package pl.baranowski.dev.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.baranowski.dev.dto.*;
import pl.baranowski.dev.dto.SearchBodyDTO;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;
import pl.baranowski.dev.service.RecipeService;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WebController.class)
@ExtendWith(SpringExtension.class)
class WebControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RecipeService recipeService;

    @Test
    void showMainPage_addsSearchBodyToModel_rendersIndex() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attributeExists("searchBody"));
    }

    @Test
    void showRecipeView_whenExternalApiException_rendersErrorPageWithApiError() throws Exception {
        when(recipeService.get(1L)).thenThrow(ExternalApiException.class);

        mockMvc.perform(get("/recipe/{id}", 1L))
               .andExpect(view().name("error"))
               .andExpect(model().attributeExists("error"))
               .andExpect(model().attribute("error", Matchers.notNullValue()));
    }

    @Test
    void showRecipeView_whenResourceParsingException_returns400AndError() throws Exception {
        when(recipeService.get(1L)).thenThrow(ResourceParsingException.class);

        mockMvc.perform(get("/recipe/{id}", 1L))
               .andExpect(view().name("error"))
               .andExpect(model().attributeExists("error"))
               .andExpect(model().attribute("error", Matchers.notNullValue()));
    }

    @Test
    void showRecipeView_addsRecipeToModel_returns200AndDetailView() throws Exception {
        RecipeDTO spaghetti = new RecipeDTO(123L,
                                            "http://orogin.url/",
                                            "http://image.url/",
                                            "Spaghetti Bolognese",
                                            "Very tasty spaghetti",
                                            Arrays.asList(new IngredientDTO("Pasta", 1D, "pack"),
                                                          new IngredientDTO("Passata", 1D, "can")),
                                            Arrays.asList(new StepDTO(1, "Fail to find a pot."),
                                                          new StepDTO(2, "Go to restaurant.")));

        when(recipeService.get(1L)).thenReturn(spaghetti);

        mockMvc.perform(get("/recipe/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(view().name("detail_view"))
               .andExpect(model().attributeExists("recipe"))
               .andExpect(model().attribute("recipe", Matchers.equalTo(spaghetti)));
    }

    @Test
    void showRecipesSearchResult_whenSearchBodyInvalid_rendersIndexWithError() throws Exception {
        //given
        SearchBodyDTO searchBodyDTO = new SearchBodyDTO("", "eggs");
        //when
        //then
        mockMvc.perform(post("/find").flashAttr("searchBody", searchBodyDTO))
               .andExpect(view().name("index"))
               .andExpect(model().attributeHasFieldErrors("searchBody", "include"));
    }

    @Test
    void showRecipesSearchResult_whenExternalApiException_rendersErrorPage() throws Exception {
        //given
        SearchBodyDTO searchBodyDTO = new SearchBodyDTO("pasta, passata", "eggs");
        //when
        when(recipeService.find(searchBodyDTO.getIncludeAsList(), searchBodyDTO.getExcludeAsList())).thenThrow(
                ExternalApiException.class);
        //then
        mockMvc.perform(post("/find").flashAttr("searchBody", searchBodyDTO))
               .andExpect(view().name("error"))
               .andExpect(model().attributeExists("error"))
               .andExpect(model().attribute("error", Matchers.notNullValue()));

    }

    @Test
    void showRecipesSearchResult_whenResourceParsingException_returns400AndError() throws Exception {
        //given
        SearchBodyDTO searchBodyDTO = new SearchBodyDTO("pasta, passata", "eggs");
        //when
        when(recipeService.find(searchBodyDTO.getIncludeAsList(), searchBodyDTO.getExcludeAsList())).thenThrow(
                ResourceParsingException.class);
        //then
        mockMvc.perform(post("/find").flashAttr("searchBody", searchBodyDTO))
               .andExpect(view().name("error"))
               .andExpect(model().attributeExists("error"))
               .andExpect(model().attribute("error", Matchers.notNullValue()));
    }

    @Test
    void showRecipesSearchResult_addsRecipeCardsListToModel_returns200AndListView() throws Exception {
        //given
        RecipeCardDTO spaghettiCard = new RecipeCardDTO(123L,
                                                        "Spaghetti Bolognese",
                                                        "http://image.url/",
                                                        Arrays.asList(new IngredientDTO("Minced meat", 1D, "lbs"),
                                                                new IngredientDTO("Onion", 1D, "pc")));
        SearchBodyDTO searchBodyDTO = new SearchBodyDTO("pasta, passata", "eggs");
        //when
        when(recipeService.find(searchBodyDTO.getIncludeAsList(),
                                searchBodyDTO.getExcludeAsList())).thenReturn(Collections.singletonList(
                spaghettiCard));
        //then
        mockMvc.perform(post("/find").flashAttr("searchBody", searchBodyDTO))
               .andExpect(status().isOk())
               .andExpect(view().name("list_view"))
               .andExpect(model().attributeExists("cards"))
               .andExpect(model().attribute("cards", Matchers.equalTo(Collections.singletonList(spaghettiCard))));
    }

    //TODO pomyśleć nad rozwiązaniem problemu odswieżania listy wyników
    @Test
    void showRecipesSearchResult_addLastSearchBodyToModel() {
        fail();
    }
}