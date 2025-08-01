package az.abb.template.service;

import org.springframework.stereotype.Service;

@Service
public class TemplateClass {
    public String helloTemplate() {
        return "Hello World from Template application";
    }
}
