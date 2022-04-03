package pl.baranowski.dev.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.baranowski.dev.dto.RecipeDTO;
import pl.baranowski.dev.dto.SearchBodyDTO;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;
import pl.baranowski.dev.dto.RecipeCardDTO;
import pl.baranowski.dev.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@SessionAttributes(value= {"searchBody"})
public class WebController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebController.class);
    private final RecipeService externalApiService;

    public WebController(RecipeService defaultExternalRecipeApiService) {
        this.externalApiService = defaultExternalRecipeApiService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        LOGGER.debug("@GET / - showMainPage(model)");

        SearchBodyDTO searchBodyDTO = new SearchBodyDTO();
        model.addAttribute("searchBody", searchBodyDTO);
        LOGGER.debug("@GET / - showMainPage(model) - searchBody has been added to model: {}", searchBodyDTO);

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

    @GetMapping("/find")
    public String showRecipesSearchResultGet(@ModelAttribute @Valid SearchBodyDTO searchBodyDTO, BindingResult result,
                                             Model model) throws ExternalApiException, ResourceParsingException {
        LOGGER.info("showRecipesSearchResultGet, {}", searchBodyDTO);

        List<RecipeCardDTO> cards = externalApiService.find(searchBodyDTO.getIncludeAsList(), searchBodyDTO.getExcludeAsList());
        LOGGER.debug("Retrieved cards: {}", cards);

        model.addAttribute("cards", cards);
        return "list_view";
    }

        @PostMapping("/find")
        public String showRecipesSearchResult(@ModelAttribute @Valid SearchBodyDTO searchBodyDTO, BindingResult result,
                                              Model model) throws ExternalApiException, ResourceParsingException {
        if(result.hasErrors()) {
            return "index";
        }
        LOGGER.debug("finRecipes(SB, model) - model contains: {}", model.asMap());
        model.addAttribute("lastSearchBody", new SearchBodyDTO());

        LOGGER.debug("@POST /findRecipes(searchBody={})", searchBodyDTO);

        List<String> includeList = searchBodyDTO.getIncludeAsList();
        LOGGER.debug("includeList={}", includeList);

        List<String> excludeList = searchBodyDTO.getExcludeAsList();
        LOGGER.debug("excludeList={}", excludeList);

        List<RecipeCardDTO> cards = externalApiService.find(includeList, excludeList);
        LOGGER.debug("Retrieved cards: {}", cards);

        model.addAttribute("cards", cards);

        return "list_view";
    }

    @GetMapping("/find/reload")
    public String reloadSearchResults(@ModelAttribute @Valid SearchBodyDTO newSearchBodyDTO, Model model) {
        LOGGER.error(String.valueOf(model.containsAttribute("searchBody")));
        LOGGER.error(newSearchBodyDTO.toString());
        return "redirect:/find";
    }
}
