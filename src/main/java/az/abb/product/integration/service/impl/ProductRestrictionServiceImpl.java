package az.abb.product.integration.service.impl;

import az.abb.product.dto.productrestriction.ProductRestrictionRequest;
import az.abb.product.integration.client.TransferProductRestrictionFeignClient;
import az.abb.product.service.ProductRestrictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRestrictionServiceImpl implements ProductRestrictionService {
    private final TransferProductRestrictionFeignClient restrictionClient;

    @Override
    public Boolean isProductRestricted(ProductRestrictionRequest request) {
        if (request == null) {
            log.warn("checkProductRestriction called with null request");
            return Boolean.FALSE;
        }

        ProductRestrictionRequest payload = ProductRestrictionRequest.builder()
                .productCode(request.productCode())
                .partyType(request.partyType())
                .build();

        var response = restrictionClient.checkProductRestriction(payload.productCode(), payload.partyType());
        if (response == null) {
            log.warn("Restriction client returned null response");
            return Boolean.FALSE;
        }

        var body = response.getBody();
        if (body == null || body.getRestricted() == null) {
            log.warn("Restriction response body or flag is null");
            return Boolean.FALSE;
        }

        return body.getRestricted();
    }
}
