package az.abb.product.dto.offlineproduct;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String type;
    private String number;
    private String status;
    
    @JsonProperty("data")
    private Object data; // CardData or AccountData
}
