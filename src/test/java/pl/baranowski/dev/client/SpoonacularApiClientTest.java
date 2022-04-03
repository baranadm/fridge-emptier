package pl.baranowski.dev.client;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.api.external.spoonacular.Recipe;
import pl.baranowski.dev.api.external.spoonacular.search.result.Result;
import pl.baranowski.dev.api.external.spoonacular.search.result.SearchResults;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MockWebServer.class, ObjectMapper.class})
@TestWithResources
class SpoonacularApiClientTest {
    @Autowired
    ObjectMapper objectMapper;
    MockWebServer mockWebServer;
    SpoonacularApiClient underTest;

    @GivenTextResource("/spoonacular_json_examples/recipe.json")
    String recipeJSON;
    @GivenTextResource("/spoonacular_json_examples/search_result.json")
    String searchResultJSON;
    @GivenTextResource("/spoonacular_json_examples/recipe_not_found.json")
    String errorJSON;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        underTest = new SpoonacularApiClient(mockWebServer.url("/").toString(), objectMapper);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void get_whenResponseReceived_returnsRawResponse() throws ExternalApiException, ResourceParsingException {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(recipeJSON)
                                                        .addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        Recipe response = underTest.get(1L);
        //then
        assertEquals(642582, response.getId());
        assertEquals("Farfalle With Broccoli, Carrots and Tomatoes", response.getTitle());
        assertEquals("https://www.foodista.com/recipe/YV88GS5Z/farfalle-with-broccoli-carrots-and-tomatoes",
                     response.getSourceUrl());
        assertEquals("https://spoonacular.com/recipeImages/642582-556x370.jpg", response.getImage());
        assertNotNull(response.getSummary());
        assertEquals(8, response.getExtendedIngredients().size());
        assertTrue(response.getAnalyzedInstructions().size() > 0);
        assertNotNull(response.getAnalyzedInstructions().get(0));
        assertEquals(13, response.getAnalyzedInstructions().get(0).getSteps().size());

    }

    @Test
    void get_whenNoResponse_throwsExternalApiException() throws ExternalApiException, IOException {
        //when
        mockWebServer.shutdown();
        //then
        assertThrows(ExternalApiException.class, () -> underTest.get(1L));
    }

    @Test
    void get_whenErrorResponse_throwsExternalApiException() {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(errorJSON)
                                                        .setResponseCode(404)
                                                        .addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        //then
        assertThrows(ExternalApiException.class, () -> underTest.get(420000000L));
    }

    @Test
    void find_whenResponseReceived_returnsListOfSearchResultEntries() throws ExternalApiException, ResourceParsingException {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(searchResultJSON)
                                                        .addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        List<Result> result = underTest.find(Arrays.asList("banana", "avocado"),
                                             Arrays.asList("eggs"));
        //then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(sre -> (
                sre.getId() != null &&
                        sre.getTitle() != null &&
                        sre.getImage() != null &&
                        sre.getMissedIngredients() != null)));
    }

    @Test
    void find_whenNoResponse_throwsExternalApiException() throws ExternalApiException, IOException {
        //when
        mockWebServer.shutdown();
        //then
        assertThrows(ExternalApiException.class,
                     () -> underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry")));
    }

    @Test
    void find_whenErrorResponse_throwsExternalApiException() {
        //given
        MockResponse mockedResponse = new MockResponse().setResponseCode(404)
                                                        .addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        //then
        assertThrows(ExternalApiException.class,
                     () -> underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry")));
    }

    @Test
    void find_whenInvalidJSON_throwsResourceParsingException() throws ExternalApiException, ResourceParsingException {
        //given
        String invalidJSON = "łłłł";
        List<String> include = Collections.singletonList("banana");
        List<String> exclude = Collections.singletonList("montana");
        //when
        MockResponse mockedResponse = new MockResponse().setBody(invalidJSON)
                                                        .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockedResponse);
        //then
        assertThrows(ResourceParsingException.class, () -> underTest.find(include, exclude));
    }

}