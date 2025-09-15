package az.abb.product.integration.client;

import az.abb.product.dto.offlineproduct.OfflineProductsResponse;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductType;
import java.util.Set;
import java.util.UUID;
import org.codehaus.commons.nullanalysis.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "offline-product-reader", url = "${endpoints.offline-product-reader}")
public interface OfflineProductReaderFeignClient {

    @GetMapping
    OfflineProductsResponse getProducts(
            @RequestParam(value = "userID", required = false) UUID userId,
            @RequestParam(value = "cifs", required = false) Set<String> cifs,
            @RequestParam(value = "pin", required = false) String pin,
            @RequestParam(value = "productNumber", required = false) String productNumber,
            @RequestParam(value = "types", required = false) Set<ProductType> types,
            @RequestParam(value = "includeHidden", required = false) Boolean includeHidden,
            @RequestParam(value = "includeExpired", required = false) Boolean includeExpired,
            @RequestParam(value = "primary", required = false) Boolean primary,
            @RequestParam(value = "productCode", required = false) String productCode);

    @GetMapping("/{id}")
    ProductDto<?> getProductById(
            @PathVariable @NotNull String id,
            @RequestParam(value = "userID", required = false) UUID userId,
            @RequestParam(value = "cifs", required = false) Set<String> cifs,
            @RequestParam(value = "pin", required = false) String pin);
}
