package az.abb.product.mcp;

import reactor.core.publisher.Mono;

public interface ProductRestrictionTool {

    Mono<Boolean> checkProductRestriction(
            String productCode,
            String partyType
    );
}
