package pl.baranowski.dev.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Getter
public class ApiException extends Exception {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiException.class);
    private UUID uuid;

    public ApiException(String message) {
        super(message);
        this.uuid = UUID.randomUUID();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.uuid = UUID.randomUUID();
    }

    //    public ApiException(String message) {
//        this(message, UUID.randomUUID());
//    }
//
//    public ApiException(String message, UUID uuid) {
//        super(message);
//        this.uuid = uuid;
//        //TODO nie wyświetla message <-
//        LOGGER.error(uuid.toString(), message);
//    }
}
