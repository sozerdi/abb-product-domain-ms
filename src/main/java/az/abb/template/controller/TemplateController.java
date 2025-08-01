package az.abb.template.controller;

import az.abb.template.dto.ResponseDto;
import az.abb.template.service.SampleErrorService;
import az.abb.template.service.TemplateClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/template")
@RequiredArgsConstructor
@Tag(name = "Template API", description = "My Template Controller")
public class TemplateController {
    private final TemplateClass service;
    private final SampleErrorService errorService;

    @Operation(summary = "Template GET Mapping")
    @GetMapping
    public ResponseEntity<ResponseDto> getSomeString() {
        return ResponseEntity.ok(new ResponseDto(service.helloTemplate()));
    }

    @Operation(summary = "Template POST Mapping")
    @PostMapping("/{data}")
    public ResponseEntity<ResponseDto> getSomeString(@PathVariable String data) {
        return ResponseEntity.ok(new ResponseDto(data));
    }

    @GetMapping("/throw")
    public void throwException() {
        errorService.throwException();
    }
}
