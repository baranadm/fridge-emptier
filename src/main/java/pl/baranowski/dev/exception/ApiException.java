package pl.baranowski.dev.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ApiException extends Exception {
    private final UUID uuid;

    public ApiException(String message) {
        super(message);
        this.uuid = UUID.randomUUID();
    }
    public ApiException(String message, UUID uuid) {
        super(message);
        this.uuid = uuid;
    }
}
