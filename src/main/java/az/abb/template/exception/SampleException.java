package az.abb.template.exception;

import az.abb.template.exception.base.BaseErrorService;
import az.abb.template.exception.base.BaseException;
import lombok.Getter;

/**
 * Exception class representing custom exceptions related to sample error cases.
 * Inherits from {@link BaseException}, allowing it to utilize error details
 * from {@link BaseErrorService} and optional additional arguments for flexible error message formatting.
 */
@Getter
public class SampleException extends BaseException {

    /**
     * Constructor for {@link SampleException} with a throwable and additional arguments.
     *
     * @param baseErrorService The error service providing error details.
     * @param throwable        The root cause of the exception.
     * @param args             Additional arguments for the error.
     */
    public SampleException(BaseErrorService baseErrorService, Throwable throwable, Object... args) {
        super(baseErrorService, throwable, args);
    }

    /**
     * Constructor for {@link SampleException} with additional arguments.
     *
     * @param baseErrorService The error service providing error details.
     * @param args             Additional arguments for the error.
     */
    public SampleException(BaseErrorService baseErrorService, Object... args) {
        super(baseErrorService, args);
    }
}

