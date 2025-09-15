package az.abb.product.dto.offlineproduct;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfflineProductsResponse {
    private List<ProductDto<?>> products;
    private String message;
}

