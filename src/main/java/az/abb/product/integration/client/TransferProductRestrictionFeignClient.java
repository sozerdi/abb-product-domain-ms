package az.abb.product.integration.client;

import az.abb.product.dto.productrestriction.ProductRestrictionResponse;
import az.abb.product.enums.PartyType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-restriction-client", url = "${endpoints.transfer-ms}")
public interface TransferProductRestrictionFeignClient {

    @GetMapping(value = "/product-restriction")
    ResponseEntity<ProductRestrictionResponse> checkProductRestriction(
            @RequestParam String productCode,
            @RequestParam PartyType partyType);
}
