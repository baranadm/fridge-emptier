package pl.baranowski.dev.exception;

import java.util.UUID;

public class ExternalApiException extends ApiException {
    public ExternalApiException(String message) {
        super(message);
    }
//    public ExternalApiException(String message, UUID uuid) {
//        super(message, uuid);
//    }
}
