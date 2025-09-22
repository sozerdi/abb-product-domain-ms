package az.abb.product.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import az.abb.product.mcp.CustomerProductsTool;
import az.abb.product.mcp.ProductInfoTool;
import az.abb.product.mcp.ProductRestrictionTool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.tool.ToolCallbackProvider;

@ExtendWith(MockitoExtension.class)
class McpConfigTest {

    @Mock
    private ProductInfoTool productInfoTool;

    @Mock
    private CustomerProductsTool customerProductsTool;

    @Mock
    private ProductRestrictionTool productRestrictionTool;

    @InjectMocks
    private McpConfig mcpConfig;

    @Test
    void productInfoTools_ShouldReturnToolCallbackProvider() {
        // When
        ToolCallbackProvider provider = mcpConfig.productInfoTools(productInfoTool);

        // Then
        assertNotNull(provider);
    }

    @Test
    void customerProductsTools_ShouldReturnToolCallbackProvider() {
        // When
        ToolCallbackProvider provider = mcpConfig.customerProductsTools(customerProductsTool);

        // Then
        assertNotNull(provider);
    }

    @Test
    void productRestrictionTools_ShouldReturnToolCallbackProvider() {
        // When
        ToolCallbackProvider provider = mcpConfig.productRestrictionTools(productRestrictionTool);

        // Then
        assertNotNull(provider);
    }
}

