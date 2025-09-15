package az.abb.product.integration.service.impl;

import az.abb.product.config.ProductServiceMockProperties;
import az.abb.product.dto.offlineproduct.AccountData;
import az.abb.product.dto.offlineproduct.CardData;
import az.abb.product.dto.offlineproduct.IdentifierOptions;
import az.abb.product.dto.offlineproduct.OfflineProductsResponse;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.dto.offlineproduct.ProductType;
import az.abb.product.integration.client.OfflineProductReaderFeignClient;
import az.abb.product.service.MockProductDataService;
import az.abb.product.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final OfflineProductReaderFeignClient productReaderFeignClient;
    private final MockProductDataService mockProductDataService;
    private final ProductServiceMockProperties mockProperties;

    @Override
    public ProductDto<?> getProductInfo(ProductRequest requestDto) {
        if (requestDto == null) {
            return null;
        }

        // Check if mock mode is enabled
        if (mockProperties.isEnabled()) {
            log.info("Mock mode enabled, returning mock product data filtered by identifierOptions");
            return getMockProduct(requestDto);
        }

        IdentifierOptions opts = requestDto.identifierOptions();
        OfflineProductsResponse resp = productReaderFeignClient.getProducts(
                opts != null ? opts.getUserId() : null,
                opts != null ? opts.getCifs() : null,
                opts != null ? opts.getPin() : null,
                null, // cardNumbers not used here
                requestDto.types(),
                requestDto.includeHidden(),
                null, // includeExpired
                null, // default
                null  // productCode
        );

        List<ProductDto<?>> products = resp == null ? null : resp.getProducts();
        if (products == null || products.isEmpty()) {
            return null;
        }

        String productId = safeTrim(requestDto.productId());
        
        Predicate<ProductDto<?>> filter;
        if (productId != null && !productId.isEmpty()) {
            filter = p -> productId.equals(p.getId());
        } else {
            String productNumber = safeTrim(requestDto.productNumber());
            if (productNumber != null && !productNumber.isEmpty()) {
                filter = p -> productNumber.equals(extractProductNumber(p));
            } else {
                // Neither id nor number provided
                return null;
            }
        }

        return products.stream()
                .filter(Objects::nonNull)
                .filter(filter)
                .findFirst()
                .orElse(null);
    }

    private ProductDto<?> getMockProduct(ProductRequest requestDto) {
        String productId = safeTrim(requestDto.productId());
        
        // Check if requesting specific product by ID
        if (productId != null && !productId.isEmpty()) {
            // Search in cards
            var cards = mockProductDataService.createMockCardsBulk();
            var foundCard = cards.stream().filter(p -> productId.equals(p.getId())).findFirst();
            if (foundCard.isPresent()) {
                return foundCard.get();
            }
            // Then in accounts
            var accounts = mockProductDataService.createMockAccountsBulk();
            var foundAcc = accounts.stream().filter(p -> productId.equals(p.getId())).findFirst();
            if (foundAcc.isPresent()) {
                return foundAcc.get();
            }
        }
        
        // Check if requesting specific product by number (or id) in the seed list
        String productNumber = safeTrim(requestDto.productNumber());
        if (productNumber != null && !productNumber.isEmpty()) {
            // Search in cards first
            ProductDto<CardData> match = mockProductDataService.findMockCardByNumberOrId(productNumber);
            if (match != null) {
                return match;
            }
            // Try accounts by number (id is account number)
            var accounts = mockProductDataService.createMockAccountsBulk();
            var acc = accounts.stream().filter(p -> productNumber.equals(extractProductNumber(p))).findFirst();
            if (acc.isPresent()) {
                return acc.get();
            }
            // If specific product number requested but not found, return null
            return null;
        }
        
        // Check if requesting by PIN
        IdentifierOptions opts = requestDto.identifierOptions();
        if (opts != null && opts.getPin() != null && !opts.getPin().isEmpty()) {
            // Search cards by PIN
            List<ProductDto<CardData>> cards = mockProductDataService.createMockCardsBulk();
            List<ProductDto<CardData>> pinFilteredCards = 
                    mockProductDataService.filterCardsByIdentifierOptions(cards, opts);
            if (!pinFilteredCards.isEmpty()) {
                return pinFilteredCards.get(0);
            }
            
            // Search accounts by PIN
            var accounts = mockProductDataService.createMockAccountsBulk();
            var pinFilteredAccounts = 
                    mockProductDataService.filterAccountsByIdentifierOptions(accounts, opts);
            if (!pinFilteredAccounts.isEmpty()) {
                return pinFilteredAccounts.get(0);
            }
        }

        // Otherwise, honor identifierOptions by picking the first matching product
        // Prefer CARD if requested
        if (requestDto.types() == null || requestDto.types().contains(ProductType.CARD)) {
            List<ProductDto<CardData>> cards = mockProductDataService.createMockCardsBulk();
            List<ProductDto<CardData>> filtered = mockProductDataService.filterCardsByIdentifierOptions(cards, opts);
            if (!filtered.isEmpty()) {
                return filtered.get(0);
            }
        }
        
        // Fallback to ACCOUNT if requested and card not found
        if (requestDto.types() == null || requestDto.types().contains(ProductType.ACCOUNT)) {
            var accounts = mockProductDataService.createMockAccountsBulk();
            var filtered = mockProductDataService.filterAccountsByIdentifierOptions(accounts, opts);
            if (!filtered.isEmpty()) {
                return filtered.get(0);
            }
        }

        // If nothing matches, return null to mimic no results
        return null;
    }

    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    private static String extractProductNumber(ProductDto<?> product) {
        if (product == null || product.getType() == null || product.getData() == null) {
            return null;
        }

        if (ProductType.CARD.equals(product.getType()) && product.getData() instanceof CardData cardData) {
            return safeTrim(cardData.getNumber());
        }

        if (ProductType.ACCOUNT.equals(product.getType()) && product.getData() instanceof AccountData accountData) {
            AccountData.AccountDetails details = accountData.getAccount();
            return details == null ? null : safeTrim(details.getNumber());
        }

        return null;
    }

    @Override
    public List<ProductDto<?>> getCustomerProducts(ProductRequest requestDto) {
        // Check if mock mode is enabled
        if (mockProperties.isEnabled()) {
            log.info("Mock mode enabled, returning mock customer products filtered by identifierOptions");
            List<ProductDto<?>> productList = new ArrayList<>();
            
            if (requestDto.types().contains(ProductType.CARD)) {
                IdentifierOptions opts = requestDto.identifierOptions();
                List<ProductDto<CardData>> cards = mockProductDataService.createMockCardsBulk();
                productList.addAll(mockProductDataService.filterCardsByIdentifierOptions(cards, opts));
            }
            if (requestDto.types().contains(ProductType.ACCOUNT)) {
                IdentifierOptions opts = requestDto.identifierOptions();
                var accounts = mockProductDataService.createMockAccountsBulk();
                productList.addAll(mockProductDataService.filterAccountsByIdentifierOptions(accounts, opts));
            }
            return productList;
        }

        IdentifierOptions opts = requestDto.identifierOptions();
        OfflineProductsResponse resp = productReaderFeignClient.getProducts(
                opts != null ? opts.getUserId() : null,
                opts != null ? opts.getCifs() : null,
                opts != null ? opts.getPin() : null,
                null,
                Set.of(ProductType.CARD, ProductType.ACCOUNT),
                Boolean.FALSE,
                null,
                null,
                null
        );
        return resp == null ? null : resp.getProducts();
    }
}
