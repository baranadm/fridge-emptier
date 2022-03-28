package pl.baranowski.dev.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;
import pl.baranowski.dev.service.RecipeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WebController.class)
@ExtendWith(SpringExtension.class)
class WebControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RecipeService recipeService;

    @Test
    void showMainPage_addsSearchBodyToModel_returnsStatus200AndIndex() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attributeExists("searchBody"));
    }

    @Test
    void showRecipeView_whenExternalApiException_returns400AndError() throws ExternalApiException, ResourceParsingException {
        //given
        MvcResult result = mockMvc.perform(get("recipe/1L")).andExpect(status().isBadRequest()).andExpect(model().attributeHasFieldErrors());
        //when
        when(recipeService.get(1L)).thenThrow(ExternalApiException.class);
        //then
        assertThrows(ExternalApiException.class, () -> recipeService.get(1L));
    }

    @Test
    void showRecipeView_whenResourceParsingException_returns400AndError() throws ExternalApiException, ResourceParsingException {
        //when
        when(recipeService.get(1L)).thenThrow(ResourceParsingException.class);
        //then
        assertThrows(ResourceParsingException.class, () -> recipeService.get(1L));
    }

    @Test
    void showRecipeView_addsRecipeToModel_returns200AndDetailView() {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attributeExists("searchBody"));
    }

    @Test
    void showRecipesSearchResult_whenEmptyOrInvalidSearchBody_returns400AndError() {
        fail();
    }

    @Test
    void showRecipesSearchResult_whenExternalApiException_returns400AndError() {
        fail();
    }

    @Test
    void showRecipesSearchResult_whenResourceParsingException_returns400AndError() {
        fail();
    }

    @Test
    void showRecipesSearchResult_addsRecipeCardsListToModel_returns200AndListView() {
        fail();
    }

    //TODO pomyśleć nad rozwiązaniem problemu odswieżania listy wyników
    @Test
    void showRecipesSearchResult_addLastSearchBodyToModel() {
        fail();
    }
}