package az.abb.product.integration.service.impl;

import static az.abb.product.enums.FilterType.CARD_NUMBER;

import az.abb.product.dto.offlineproduct.AccountData;
import az.abb.product.dto.offlineproduct.CardData;
import az.abb.product.dto.offlineproduct.IdentifierOptions;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductFilterRequest;
import az.abb.product.dto.offlineproduct.ProductListRequest;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.dto.offlineproduct.ProductResponse;
import az.abb.product.dto.offlineproduct.ProductType;
import az.abb.product.enums.FilterType;
import az.abb.product.integration.client.OfflineProductClient;
import az.abb.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final OfflineProductClient offlineProductClient;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<ProductDto> getProductInfo(ProductRequest requestDto) {
        if (requestDto == null) {
            return Mono.empty();
        }

        String productId = safeTrim(requestDto.productId());
        String productNumber = safeTrim(requestDto.productNumber());
        ProductType productType = requestDto.type();
        
        if (productId != null && !productId.isEmpty()) {
            // Get by product ID using the specified product type
            FilterType filterType = getFilterTypeForProductType(productType);
            return offlineProductClient.getProductDetail(filterType, productId)
                    .map(this::convertProductResponseToDto)
                    .doOnNext(response -> log.debug("Received product info: {}", 
                            response != null ? response.getId() : "null"))
                    .doOnError(error -> log.error("Error fetching product info", error));
        } else if (productNumber != null && !productNumber.isEmpty()) {
            // Get by product number using the specified product type
            FilterType filterType = getFilterTypeForProductType(productType);
            return offlineProductClient.getProductDetail(filterType, productNumber)
                    .map(this::convertProductResponseToDto)
                    .doOnNext(response -> log.debug("Received product info: {}", 
                            response != null ? response.getId() : "null"))
                    .doOnError(error -> log.error("Error fetching product info", error));
        } else {
            return Mono.empty();
        }
    }


    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    @Override
    public Flux<ProductDto> getCustomerProducts(ProductListRequest requestDto) {

        // Use new V2 API for multiple products
        IdentifierOptions opts = requestDto.identifierOptions();
        
        ProductFilterRequest filterRequest = buildProductFilterRequestFromProductListRequest(requestDto);
        
        return offlineProductClient.getProducts(
                opts != null ? opts.getPin() : null,
                opts != null && opts.getCifs() != null && !opts.getCifs().isEmpty() 
                        ? opts.getCifs().iterator().next() : null,
                opts != null ? opts.getUserId() : null,
                filterRequest
        )
        .flatMapMany(Flux::fromIterable)
        .map(this::convertProductResponseToDto)
        .doOnNext(response -> log.debug("Received customer product: {}", response.getId()))
        .doOnError(error -> log.error("Error fetching customer products", error));
    }


    private ProductDto convertProductResponseToDto(ProductResponse response) {
        if (response == null) {
            return null;
        }
        
        ProductDto dto = new ProductDto();
        dto.setId(response.getId());
        dto.setType(ProductType.valueOf(response.getType()));
        
        // Convert data based on type using ObjectMapper to handle LinkedHashMap
        if (response.getData() != null) {
            try {
                if ("CARD".equals(response.getType())) {
                    CardData cardData = objectMapper.convertValue(response.getData(), CardData.class);
                    dto.setData(cardData);
                } else if ("ACCOUNT".equals(response.getType())) {
                    AccountData accountData = objectMapper.convertValue(response.getData(), AccountData.class);
                    dto.setData(accountData);
                }
            } catch (Exception e) {
                log.error("Error converting response data to DTO for type: {}", response.getType(), e);
                dto.setData(null);
            }
        }
        
        return dto;
    }


    private ProductFilterRequest buildProductFilterRequestFromProductListRequest(ProductListRequest request) {
        ProductFilterRequest.ProductFilterRequestBuilder builder = ProductFilterRequest.builder();
        
        // Set product types
        if (request.types() != null && !request.types().isEmpty()) {
            builder.types(request.types().stream()
                    .map(ProductType::name)
                    .collect(java.util.stream.Collectors.toList()));
        }
        
        // Build filters based on product types
        ProductFilterRequest.Filters.FiltersBuilder filtersBuilder = ProductFilterRequest.Filters.builder();
        
        // Check if we have CARD types
        boolean hasCardTypes = request.types() != null 
                && (request.types().contains(ProductType.CARD) || request.types().contains(ProductType.STORED_CARD));
        
        // Check if we have ACCOUNT types
        boolean hasAccountTypes = request.types() != null && request.types().contains(ProductType.ACCOUNT);
        
        if (hasCardTypes) {
            // Card filters
            ProductFilterRequest.Filters.Card.CardBuilder cardBuilder = ProductFilterRequest.Filters.Card.builder();
            cardBuilder.visible(request.hidden());
            cardBuilder.primary(request.primary());
            cardBuilder.expired(request.expired()); // Only for cards
            filtersBuilder.card(cardBuilder.build());
        }
        
        if (hasAccountTypes) {
            // Account filters
            ProductFilterRequest.Filters.Account.AccountBuilder accountBuilder = 
                    ProductFilterRequest.Filters.Account.builder();
            accountBuilder.visible(request.hidden());
            accountBuilder.primary(request.primary());
            // expired field is not set for accounts
            filtersBuilder.account(accountBuilder.build());
        }
        
        builder.filters(filtersBuilder.build());
        return builder.build();
    }

    private FilterType getFilterTypeForProductType(ProductType productType) {
        if (productType == null) {
            // Default to CARD_NUMBER if no type specified
            return CARD_NUMBER;
        }
        
        switch (productType) {
            case CARD:
                return CARD_NUMBER;
            case ACCOUNT:
                return FilterType.ACCOUNT_NUMBER;
            default:
                return null;
        }
    }
}
