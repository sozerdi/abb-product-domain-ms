package az.abb.product.integration.service.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private OfflineProductClient offlineProductClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequest productRequest;
    private ProductListRequest productListRequest;
    private ProductResponse productResponse;
    private CardData cardData;
    private AccountData accountData;

    @BeforeEach
    void setUp() {
        cardData = new CardData();
        cardData.setNumber("1234567890123456");
        cardData.setExpiryDate("12/25");

        accountData = new AccountData();
        AccountData.AccountDetails details = new AccountData.AccountDetails();
        details.setNumber("1234567890");
        accountData.setAccount(details);

        productResponse = ProductResponse.builder()
                .id("test-id")
                .type("CARD")
                .number("1234567890123456")
                .status("ACTIVE")
                .data(cardData)
                .build();

        productRequest = ProductRequest.builder()
                .productId("test-id")
                .type(ProductType.CARD)
                .build();

        IdentifierOptions identifierOptions = IdentifierOptions.builder()
                .pin("1234")
                .cifs(Set.of("CIF123"))
                .userId(UUID.randomUUID())
                .build();

        productListRequest = ProductListRequest.builder()
                .identifierOptions(identifierOptions)
                .types(Set.of(ProductType.CARD, ProductType.ACCOUNT))
                .hidden(false)
                .primary(true)
                .expired(false)
                .build();
    }

    @Test
    void getProductInfo_WithNullRequest_ShouldReturnEmpty() {
        // When
        Mono<ProductDto> result = productService.getProductInfo(null);

        // Then
        assertNull(result.block());
    }

    @Test
    void getProductInfo_WithEmptyRequest_ShouldReturnEmpty() {
        // Given
        ProductRequest emptyRequest = ProductRequest.builder()
                .type(ProductType.CARD)
                .build();

        // When
        Mono<ProductDto> result = productService.getProductInfo(emptyRequest);

        // Then
        assertNull(result.block());
    }

    @Test
    void getProductInfo_WithProductId_ShouldCallClient() {
        // Given
        when(offlineProductClient.getProductDetail(any(), anyString()))
                .thenReturn(Mono.empty());

        // When
        Mono<ProductDto> result = productService.getProductInfo(productRequest);

        // Then
        assertNull(result.block());
    }

    @Test
    void getProductInfo_WithProductNumber_ShouldCallClient() {
        // Given
        ProductRequest requestWithNumber = ProductRequest.builder()
                .productNumber("1234567890123456")
                .type(ProductType.CARD)
                .build();

        when(offlineProductClient.getProductDetail(any(), anyString()))
                .thenReturn(Mono.empty());

        // When
        Mono<ProductDto> result = productService.getProductInfo(requestWithNumber);

        // Then
        assertNull(result.block());
    }

    @Test
    void getProductInfo_WithAccountType_ShouldUseAccountNumberFilter() {
        // Given
        ProductRequest accountRequest = ProductRequest.builder()
                .productId("account-id")
                .type(ProductType.ACCOUNT)
                .build();

        when(offlineProductClient.getProductDetail(FilterType.ACCOUNT_NUMBER, "account-id"))
                .thenReturn(Mono.empty());

        // When
        Mono<ProductDto> result = productService.getProductInfo(accountRequest);

        // Then
        assertNull(result.block());
    }

    @Test
    void getCustomerProducts_ShouldReturnFluxOfProductDtos() {
        // Given
        when(offlineProductClient.getProducts(anyString(), anyString(), any(UUID.class), 
                any(ProductFilterRequest.class)))
                .thenReturn(Mono.empty());

        // When
        Flux<ProductDto> result = productService.getCustomerProducts(productListRequest);

        // Then
        assertNull(result.blockFirst());
    }

    @Test
    void getCustomerProducts_WithCardAndAccountTypes_ShouldBuildCorrectFilter() {
        // Given
        when(offlineProductClient.getProducts(anyString(), anyString(), any(UUID.class), 
                any(ProductFilterRequest.class)))
                .thenReturn(Mono.empty());

        // When
        Flux<ProductDto> result = productService.getCustomerProducts(productListRequest);

        // Then
        assertNull(result.blockFirst());
    }
}