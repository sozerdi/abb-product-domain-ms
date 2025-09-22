package az.abb.product.integration.service.impl;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import az.abb.product.integration.client.TransferProductRestrictionClient;
import az.abb.product.service.ProductRestrictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRestrictionServiceImpl implements ProductRestrictionService {
    private final TransferProductRestrictionClient restrictionClient;

    @Override
    public Mono<Boolean> isProductRestricted(ProductRestrictionRequest request) {
        if (request == null) {
            log.warn("checkProductRestriction called with null request");
            return Mono.just(Boolean.FALSE);
        }

        ProductRestrictionRequest payload = ProductRestrictionRequest.builder()
                .productCode(request.productCode())
                .partyType(request.partyType())
                .build();

        return restrictionClient.checkProductRestriction(payload.productCode(), payload.partyType())
                .map(response -> {
                    if (response == null || response.getRestricted() == null) {
                        log.warn("Restriction response body or flag is null");
                        return Boolean.FALSE;
                    }
                    return response.getRestricted();
                })
                .onErrorReturn(Boolean.FALSE)
                .doOnError(error -> log.error("Error checking product restriction", error))
                .doOnNext(result -> log.debug("Product restriction check result: {}", result));
    }
}
