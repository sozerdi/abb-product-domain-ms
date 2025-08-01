package az.abb.template.exception;

import az.abb.template.exception.base.BaseErrorEnum;
import az.abb.template.exception.base.BaseErrorResponseDTO;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponseDTO> handleBaseException(MethodArgumentNotValidException ex,
                                                                    WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new BaseErrorResponseDTO(BaseErrorEnum.BASE_VALIDATION_ERROR.getErrorCode(),
                BaseErrorEnum.BASE_BUSINESS_ERROR.getMessage(), webRequest.getContextPath(),
                LocalDateTime.now().toString(), BaseErrorEnum.BASE_VALIDATION_ERROR.getHttpStatus(), errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SampleException.class)
    public ResponseEntity<BaseErrorResponseDTO> handleConstraintViolationException(SampleException ex,
                                                                                   WebRequest webRequest) {
        return new ResponseEntity<>(new BaseErrorResponseDTO(ex.baseErrorService.getErrorCode(),
                ex.baseErrorService.getMessage(), webRequest.getContextPath(), LocalDateTime.now().toString(),
                ex.baseErrorService.getHttpStatus()), HttpStatusCode.valueOf(ex.baseErrorService.getHttpStatus())
        );
    }
}
