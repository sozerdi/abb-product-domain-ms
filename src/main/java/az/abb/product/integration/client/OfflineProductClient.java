package az.abb.product.integration.client;

import az.abb.product.dto.offlineproduct.ProductFilterRequest;
import az.abb.product.dto.offlineproduct.ProductResponse;
import az.abb.product.enums.FilterType;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class OfflineProductClient {

    private final WebClient.Builder webClientBuilder;
    
    @Value("${endpoints.product-service-v2}")
    private String baseUrl;

    public Mono<ProductResponse> getProductDetail(FilterType filterType, String filterValue) {
        String requestUrl = baseUrl + "/products?filter-type=" + filterType + "&filter-value=" + filterValue;
        log.debug("HTTP Request: GET {}", requestUrl);
        
        return webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products")
                        .queryParam("filter-type", filterType)
                        .queryParam("filter-value", filterValue)
                        .build())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .doOnNext(response -> {
                    log.info("HTTP Response: Success - Product detail received for filter: {}={}", 
                            filterType, filterValue);
                    log.debug("Response Payload: {}", response);
                })
                .doOnError(error -> {
                    log.error("HTTP Request failed for product detail (filter: {}={}): {}", 
                            filterType, filterValue, error.getMessage());
                    log.error("Error details: ", error);
                });
    }

    public Mono<List<ProductResponse>> getProducts(String pin, String cif, UUID userId, 
            ProductFilterRequest productFilterRequest) {
        String requestUrl = buildGetProductsUrl(pin, cif, userId);
        log.debug("HTTP Request: POST {}", requestUrl);
        log.debug("Request Payload: {}", productFilterRequest);
        
        return webClientBuilder.baseUrl(baseUrl).build()
                .post()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/products");
                    if (pin != null) {
                        builder.queryParam("pin", pin);
                    }
                    if (cif != null) {
                        builder.queryParam("cif", cif);
                    }
                    if (userId != null) {
                        builder.queryParam("userId", userId);
                    }
                    return builder.build();
                })
                .bodyValue(productFilterRequest)
                .retrieve()
                .bodyToFlux(ProductResponse.class)
                .collectList()
                .doOnNext(response -> {
                    log.info("HTTP Response: Success - {} products received", response.size());
                    log.debug("Response Payload: {}", response);
                })
                .doOnError(error -> {
                    log.error("HTTP Request failed for get products: {}", error.getMessage());
                    log.error("Error details: ", error);
                });
    }

    private String buildGetProductsUrl(String pin, String cif, UUID userId) {
        StringBuilder url = new StringBuilder(baseUrl).append("/products");
        boolean firstParam = true;
        
        if (pin != null) {
            url.append(firstParam ? "?" : "&").append("pin=").append(pin);
            firstParam = false;
        }
        if (cif != null) {
            url.append(firstParam ? "?" : "&").append("cif=").append(cif);
            firstParam = false;
        }
        if (userId != null) {
            url.append(firstParam ? "?" : "&").append("userId=").append(userId);
        }
        
        return url.toString();
    }
}