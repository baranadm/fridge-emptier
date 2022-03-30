package pl.baranowski.dev.client;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.exception.ExternalApiException;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(classes = {MockWebServer.class})
@TestWithResources
class SpoonacularApiClientTest {

    MockWebServer mockWebServer;
    SpoonacularApiClient underTest;

    @GivenTextResource("/spoonacular_json_examples/recipe.json")
    String recipeJSON;
    @GivenTextResource("/spoonacular_json_examples/recipe.json")
    String searchResultJSON;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        underTest = new SpoonacularApiClient(mockWebServer.url("/").toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void get() throws ExternalApiException {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(recipeJSON).addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockedResponse);
        //when
        String response = underTest.get(1L);
        //then
        assertEquals(recipeJSON, response);
    }
    @Test
    void find() throws ExternalApiException {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(searchResultJSON).addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockedResponse);
        //when
        String result = underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry"));
        //then
        assertEquals(searchResultJSON, result);
    }
}