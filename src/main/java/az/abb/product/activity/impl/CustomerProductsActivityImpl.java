package az.abb.product.activity.impl;

import az.abb.product.activity.CustomerProductsActivity;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.service.ProductService;
import io.temporal.spring.boot.ActivityImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(workers = "customer-products-worker")
@RequiredArgsConstructor
public class CustomerProductsActivityImpl implements CustomerProductsActivity {
    private final ProductService productInfoService;


    @Override
    public List<ProductDto<?>> getCustomerProducts(ProductRequest productRequest) {
        return productInfoService.getCustomerProducts(productRequest);
    }
}
