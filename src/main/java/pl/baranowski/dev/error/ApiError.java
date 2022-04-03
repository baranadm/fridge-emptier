package pl.baranowski.dev.error;

import lombok.Getter;
import pl.baranowski.dev.exception.ApiException;

@Getter
public class ApiError {
    private final String message;

    public ApiError(ApiException apiException) {
        this.message = apiException.getMessage();
    }
}
