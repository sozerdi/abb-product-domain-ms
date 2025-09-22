package az.abb.product.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(MockitoExtension.class)
class WebClientConfigTest {

    @InjectMocks
    private WebClientConfig webClientConfig;

    @Test
    void webClientBuilder_ShouldReturnWebClientBuilder() {
        // When
        WebClient.Builder builder = webClientConfig.webClientBuilder();

        // Then
        assertNotNull(builder);
    }

    @Test
    void webClientBuilder_ShouldCreateWebClient() {
        // Given
        WebClient.Builder builder = webClientConfig.webClientBuilder();

        // When
        WebClient webClient = builder.build();

        // Then
        assertNotNull(webClient);
    }
}

