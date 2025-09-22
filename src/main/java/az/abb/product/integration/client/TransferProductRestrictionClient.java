package az.abb.product.integration.client;

import az.abb.product.dto.productrestriction.ProductRestrictionResponse;
import az.abb.product.enums.PartyType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferProductRestrictionClient {

    private final WebClient.Builder webClientBuilder;
    
    @Value("${endpoints.transfer-ms}")
    private String baseUrl;

    public Mono<ProductRestrictionResponse> checkProductRestriction(String productCode, PartyType partyType) {
        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();
        
        String requestUrl = baseUrl + "/product-restriction?productCode=" + productCode + "&partyType=" + partyType;
        log.debug("HTTP Request: GET {}", requestUrl);
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/product-restriction")
                        .queryParam("productCode", productCode)
                        .queryParam("partyType", partyType)
                        .build())
                .retrieve()
                .bodyToMono(ProductRestrictionResponse.class)
                .doOnNext(response -> {
                    log.info("HTTP Response: Success - Product restriction check completed for product: {}", 
                            productCode);
                    log.debug("Response Payload: {}", response);
                })
                .doOnError(error -> {
                    log.error("HTTP Request failed for product restriction check (product: {}): {}", 
                            productCode, error.getMessage());
                    log.error("Error details: ", error);
                });
    }
}
