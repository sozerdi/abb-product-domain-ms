package az.abb.product.service;

import az.abb.product.dto.offlineproduct.ProductDto;
import az.abb.product.dto.offlineproduct.ProductListRequest;
import az.abb.product.dto.offlineproduct.ProductRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDto> getProductInfo(ProductRequest requestDto);
    
    Flux<ProductDto> getCustomerProducts(ProductListRequest requestDto);
}
