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
        LOGGER.info("@GET / - showMainPage(model)");

        SearchBodyDTO searchBodyDTO = new SearchBodyDTO();
        model.addAttribute("searchBody", searchBodyDTO);
        LOGGER.info("@GET / - showMainPage(model) - searchBody has been added to model: {}", searchBodyDTO);

        LOGGER.info("Returning index.html");
        return "index";
    }

    @GetMapping("/recipe/{id}")
    public String showRecipeView(@PathVariable(name = "id", required = true) @Min(value = 0, message = "Invalid id") long id,
                                 Model model) throws ExternalApiException, ResourceParsingException {
        LOGGER.info("@GET /recipe/{id}: id={}", id);

        RecipeDTO result = this.externalApiService.get(id);
        LOGGER.info("Result: {}", result);

        model.addAttribute("recipe", result);

        LOGGER.info("Done, returning detail_view");
        return "detail_view";
    }

    @GetMapping("/find")
    public String showRecipesSearchResultGet(@ModelAttribute(name = "searchBody") @Valid SearchBodyDTO searchBodyDTO, BindingResult result,
                                             Model model) throws ExternalApiException, ResourceParsingException {
        LOGGER.info("showRecipesSearchResultGet, {}", searchBodyDTO);

        List<RecipeCardDTO> cards = externalApiService.find(searchBodyDTO.getIncludeAsList(), searchBodyDTO.getExcludeAsList());
        LOGGER.info("Retrieved cards: {}", cards);

        model.addAttribute("cards", cards);
        return "list_view";
    }

        @PostMapping("/find")
        public String showRecipesSearchResult(@ModelAttribute(name = "searchBody") @Valid SearchBodyDTO searchBodyDTO, BindingResult result,
                                              Model model) throws ExternalApiException, ResourceParsingException {
        if(result.hasErrors()) {
            return "index";
        }
        LOGGER.info("finRecipes(SB, model) - model contains: {}", model.asMap());
        model.addAttribute("lastSearchBody", new SearchBodyDTO());

        LOGGER.info("@POST /findRecipes(searchBody={})", searchBodyDTO);

        List<String> includeList = searchBodyDTO.getIncludeAsList();
        LOGGER.info("includeList={}", includeList);

        List<String> excludeList = searchBodyDTO.getExcludeAsList();
        LOGGER.info("excludeList={}", excludeList);

        List<RecipeCardDTO> cards = externalApiService.find(includeList, excludeList);
        LOGGER.info("Retrieved cards: {}", cards);

        model.addAttribute("cards", cards);

        return "list_view";
    }

    @GetMapping("/find/reload")
    public String reloadSearchResults(@ModelAttribute(name = "searchBody")  @Valid SearchBodyDTO newSearchBodyDTO, Model model) {
        LOGGER.info(String.valueOf(model.containsAttribute("searchBody")));
        LOGGER.info(newSearchBodyDTO.toString());
        return "redirect:/find";
    }
}
