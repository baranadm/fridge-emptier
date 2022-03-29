package pl.baranowski.dev.error;

import lombok.Getter;
import pl.baranowski.dev.exception.ApiException;

import java.util.UUID;

@Getter
public class ApiError {
    private final UUID uuid;
    private final String message;

    public ApiError(ApiException apiException) {
        this.uuid = apiException.getUuid();
        this.message = apiException.getMessage();
    }
}
