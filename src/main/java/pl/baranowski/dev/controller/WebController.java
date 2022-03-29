package pl.baranowski.dev.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.SearchBody;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;
import pl.baranowski.dev.model.RecipeCard;
import pl.baranowski.dev.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
public class WebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);
    private final RecipeService externalApiService;

    public WebController(RecipeService defaultExternalRecipeApiService) {
        this.externalApiService = defaultExternalRecipeApiService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        LOGGER.debug("@GET / - showMainPage(model)");

        SearchBody searchBody = new SearchBody();
        model.addAttribute("searchBody", searchBody);
        LOGGER.debug("@GET / - showMainPage(model) - searchBody has been added to model: {}", searchBody);

        LOGGER.debug("Returning index.html");
        return "index";
    }

    @GetMapping("/recipe/{id}")
    public String showRecipeView(@PathVariable(name = "id", required = true) @Min(value = 0, message = "Invalid id") long id,
                                 Model model) throws ExternalApiException, ResourceParsingException {
        LOGGER.debug("@GET /recipe/{id}: id={}", id);

        RecipeDTO result = this.externalApiService.get(id);
        LOGGER.debug("Result: {}", result);

        model.addAttribute("recipe", result);

        LOGGER.debug("Done, returning detail_view");
        return "detail_view";
    }

    @PostMapping("/find")
    public String showRecipesSearchResult(@ModelAttribute @Valid SearchBody searchBody, BindingResult result,
                                          Model model) throws ExternalApiException, ResourceParsingException {
        if(result.hasErrors()) {
            return "index";
        }
        LOGGER.debug("finRecipes(SB, model) - model contains: {}", model.asMap());
        model.addAttribute("lastSearchBody", new SearchBody());

        LOGGER.debug("@POST /findRecipes(searchBody={})", searchBody);

        List<String> includeList = searchBody.getIncludeAsList();
        LOGGER.debug("includeList={}", includeList);

        List<String> excludeList = searchBody.getExcludeAsList();
        LOGGER.debug("excludeList={}", excludeList);

        List<RecipeCard> cards = externalApiService.find(includeList, excludeList);
        LOGGER.debug("Retrieved cards: {}", cards);

        model.addAttribute("cards", cards);

        return "list_view";
    }
}
