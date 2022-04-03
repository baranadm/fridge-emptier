package pl.baranowski.dev.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class ApiException extends Exception {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiException.class);

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
