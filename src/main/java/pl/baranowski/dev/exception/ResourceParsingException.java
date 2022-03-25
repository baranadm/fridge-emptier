package pl.baranowski.dev.exception;

import java.util.UUID;

public class ResourceParsingException extends ApiException {
    public ResourceParsingException(String message) {
        super(message);
    }
    public ResourceParsingException(String message, UUID uuid) {
        super(message, uuid);
    }
}
