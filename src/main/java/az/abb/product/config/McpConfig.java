package az.abb.product.config;

import az.abb.product.mcp.CustomerProductsTool;
import az.abb.product.mcp.ProductInfoTool;
import az.abb.product.mcp.ProductRestrictionTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider productInfoTools(ProductInfoTool productInfoTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(productInfoTool)
                .build();
    }

    @Bean
    public ToolCallbackProvider customerProductsTools(CustomerProductsTool customerProductsTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(customerProductsTool)
                .build();
    }

    @Bean
    public ToolCallbackProvider productRestrictionTools(ProductRestrictionTool productRestrictionTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(productRestrictionTool)
                .build();
    }
}
