package az.abb.product.service;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductRequest;
import java.util.List;

public interface ProductService {
    ProductDto<?> getProductInfo(ProductRequest requestDto);

    List<ProductDto<?>> getCustomerProducts(ProductRequest requestDto);
}
