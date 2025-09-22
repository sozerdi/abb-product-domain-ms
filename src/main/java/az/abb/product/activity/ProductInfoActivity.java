package az.abb.product.activity;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface ProductInfoActivity {

    ProductDto getProductInfo(ProductRequest productRequest);
}
