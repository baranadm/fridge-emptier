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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MockWebServer.class})
@TestWithResources
class SpoonacularApiClientTest {

    MockWebServer mockWebServer;
    SpoonacularApiClient underTest;

    @GivenTextResource("/spoonacular_json_examples/recipe.json")
    String recipeJSON;
    @GivenTextResource("/spoonacular_json_examples/recipe.json")
    String searchResultJSON;
    @GivenTextResource("/spoonacular_json_examples/recipe_not_found.json")
    String errorJSON;

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
    void get_whenResponseReceived_returnsRawResponse() throws ExternalApiException {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(recipeJSON).addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        String response = underTest.get(1L);
        //then
        assertEquals(recipeJSON, response);
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
        MockResponse mockedResponse = new MockResponse().setBody(errorJSON).setResponseCode(404).addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        //then
        assertThrows(ExternalApiException.class, () -> underTest.get(420000000L));
    }

    @Test
    void find_whenResponseReceived_returnsRawResponse() throws ExternalApiException {
        //given
        MockResponse mockedResponse = new MockResponse().setBody(searchResultJSON).addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        String result = underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry"));
        //then
        assertEquals(searchResultJSON, result);
    }

    @Test
    void find_whenNoResponse_throwsExternalApiException() throws ExternalApiException, IOException {
        //when
        mockWebServer.shutdown();
        //then
        assertThrows(ExternalApiException.class, () -> underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry")));
    }

    @Test
    void find_whenErrorResponse_throwsExternalApiException() {
        //given
        MockResponse mockedResponse = new MockResponse().setResponseCode(404).addHeader("Content-Type", "application/json");
        //when
        mockWebServer.enqueue(mockedResponse);
        //then
        assertThrows(ExternalApiException.class, () -> underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry")));
    }
}