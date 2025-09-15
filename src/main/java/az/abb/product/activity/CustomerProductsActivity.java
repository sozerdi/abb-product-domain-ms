package az.abb.product.activity;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import io.temporal.activity.ActivityInterface;
import java.util.List;

@ActivityInterface
public interface CustomerProductsActivity {

    List<ProductDto<?>> getCustomerProducts(ProductRequest productRequest);
}
