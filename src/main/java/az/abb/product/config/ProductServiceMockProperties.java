package az.abb.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "product.service.mock")
public class ProductServiceMockProperties {
    
    /**
     * Whether mock mode is enabled for product service.
     */
    private boolean enabled = false;
    
    /**
     * Whether to use mock data for testing.
     */
    private boolean useMockData = false;
}
