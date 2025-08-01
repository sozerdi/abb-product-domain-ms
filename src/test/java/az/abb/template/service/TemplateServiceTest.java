package az.abb.template.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TemplateServiceTest {

    @Test
    void testHelloTemplate() {
        // Create an instance of the service
        TemplateClass templateService = new TemplateClass();

        // Call the method under test
        String result = templateService.helloTemplate();

        // Verify the result
        assertEquals("Hello World from Template application", result);
    }
}
