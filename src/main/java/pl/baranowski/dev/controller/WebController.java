package pl.baranowski.dev.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.exception.ApiException;
import pl.baranowski.dev.service.RecipeService;

import javax.validation.constraints.Min;

@Controller
public class WebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);
    private final RecipeService externalApiService;

    public WebController(RecipeService defaultExternalRecipeApiService) {
        this.externalApiService = defaultExternalRecipeApiService;
    }

    @GetMapping("/recipe/{id}")
    public String recipe(@PathVariable(name = "id", required = true) @Min(value = 0, message = "Invalid id") long id,
                         Model model) throws ApiException {
        LOGGER.debug("@GET /recipe/{id}: id={}", id);

        RecipeDTO result = this.externalApiService.get(id);
        LOGGER.debug("Result: {}", result);

        model.addAttribute("recipe", result);

        LOGGER.debug("Done, returning detail_view");
        return "detail_view";
    }
}
