package az.abb.product.activity.impl;

import az.abb.product.activity.ProductInfoActivity;
import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import az.abb.product.service.ProductService;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(workers = "product-info-worker")
@RequiredArgsConstructor
public class ProductInfoActivityImpl implements ProductInfoActivity {
    private final ProductService productInfoService;


    @Override
    public ProductDto<?> getProductInfo(ProductRequest productRequest) {
        return productInfoService.getProductInfo(productRequest);
    }
}
