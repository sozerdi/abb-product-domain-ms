package az.abb.template.exception;


import az.abb.template.exception.base.BaseErrorService;

/**
 * Enum representing a set of sample error types.
 * Each error includes a specific error code, message, and corresponding HTTP status code.
 * Implements the {@link BaseErrorService} interface.
 */
public enum SampleErrorEnum implements BaseErrorService {

    /**
     * Error representing unauthorized access.
     */
    UNAUTHORIZED_ERROR("UNAUTHORIZED-ERROR-0001", "UNAUTHORIZED_ERROR", 400),

    /**
     * Custom error description case error.
     */
    YOUR_ERROR_DESCRIPTION_CASE_HERE("CASE-NAME-ERROR-0002", "YOUR_ERROR_DESCRIPTION_CASE_HERE", 401);

    /**
     * Error message.
     */
    final String message;

    /**
     * HTTP status code corresponding to the error.
     */
    final int httpStatus;

    /**
     * Unique error code.
     */
    final String errorCode;

    /**
     * Constructor for the SampleErrorEnum.
     *
     * @param errorCode  The unique error code.
     * @param message    The error message.
     * @param httpStatus The HTTP status code associated with the error.
     */
    SampleErrorEnum(String errorCode, String message, int httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Gets the error message.
     *
     * @return The error message.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Gets the HTTP status code.
     *
     * @return The HTTP status code.
     */
    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * Gets the unique error code.
     *
     * @return The error code.
     */
    @Override
    public String getErrorCode() {
        return errorCode;
    }
}

