package pl.baranowski.dev.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.baranowski.dev.error.ApiError;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({ExternalApiException.class, ResourceParsingException.class})
    public final String handleApiException(ApiException ex, Model model) {
        model.addAttribute("error", new ApiError(ex));
        return "error";
    }
}
