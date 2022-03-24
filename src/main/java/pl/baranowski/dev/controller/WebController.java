package pl.baranowski.dev.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.SearchBody;
import pl.baranowski.dev.exception.ApiException;
import pl.baranowski.dev.model.RecipeCard;
import pl.baranowski.dev.service.RecipeService;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);
    private final RecipeService externalApiService;

    public WebController(RecipeService defaultExternalRecipeApiService) {
        this.externalApiService = defaultExternalRecipeApiService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        model.addAttribute("searchBody", new SearchBody());
        return "index";
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

    @GetMapping("/find-test")
    public String test(Model model) throws ApiException {
        return findRecipes(new SearchBody("honey, pasta, basil", "tomato"), model);
    }

    @PostMapping("/find")
    public String findRecipes(@ModelAttribute SearchBody searchBody,
                              Model model) throws ApiException {
        LOGGER.debug("@GET /findRecipes(searchBody={})", searchBody);

        List<String> includeList = getList(searchBody.getInclude());
        LOGGER.debug("includeList={}", includeList);

        List<String> excludeList = getList(searchBody.getExclude());
        LOGGER.debug("excludeList={}", excludeList);

        List<RecipeCard> cards = externalApiService.find(includeList, excludeList);
        LOGGER.debug("Retrieved cards: {}", cards);

        model.addAttribute("cards", cards);

        return "list_view";
    }

    @NotNull
    private List<String> getList(String include) {
        return Arrays.stream(include.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
