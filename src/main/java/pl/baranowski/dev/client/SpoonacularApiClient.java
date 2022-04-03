package pl.baranowski.dev.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.baranowski.dev.api.external.spoonacular.Recipe;
import pl.baranowski.dev.api.external.spoonacular.search.result.Result;
import pl.baranowski.dev.api.external.spoonacular.search.result.SearchResults;
import pl.baranowski.dev.exception.ExternalApiException;
import pl.baranowski.dev.exception.ResourceParsingException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SpoonacularApiClient {
    public final static String API_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";
    private final static String API_HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private final static String API_KEY = "24ba75ae75msh0b52031a504df72p1e69eejsndc3281a731a9";
    private final static String ENDPOINT_SEARCH_URL = "recipes/complexSearch?";
    private final static boolean INSTRUCTIONS_REQUIRED = true;
    private final static boolean LIMIT_LICENSE = false;
    private final static boolean SORT_RANDOM = true;
    private final static boolean IGNORE_PANTRY = true;
    private final static boolean FILL_INGREDIENTS = true;
    private final static int NUMBER_OF_RECIPES = 9;
    private final Logger LOGGER = LoggerFactory.getLogger(SpoonacularApiClient.class);
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    @Autowired
    public SpoonacularApiClient(ObjectMapper objectMapper) {
        this(API_URL, objectMapper);
    }

    public SpoonacularApiClient(String apiUrl, ObjectMapper objectMapper) {
        this.apiUrl = apiUrl;
        this.objectMapper = objectMapper;
    }

    public Recipe get(long id) throws ExternalApiException, ResourceParsingException {
        LOGGER.info("get(id={})", id);

        String response = sendRequest(prepareUrl(id));
        LOGGER.info("Received response: {}", response);

        Recipe result = parseRecipeResponse(response);
        LOGGER.info("Returning mapped result");
        return result;
    }

    public List<Result> find(List<String> include,
                             List<String> exclude) throws ExternalApiException, ResourceParsingException {
        LOGGER.info("find(include={}, exclude={})",
                    Arrays.toString(include.toArray()),
                    Arrays.toString(exclude.toArray()));

        String response = sendRequest(prepareUrl(include, exclude));
        LOGGER.info("Received response: {}", response);

        SearchResults searchResults = parseSearchResultResponse(response);

        LOGGER.info("Returning mapped result.");
        return searchResults.getResults();
    }

    private Recipe parseRecipeResponse(String response) throws ResourceParsingException {
        Recipe result;
        try {
            result = objectMapper.readValue(response, Recipe.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error(response);
            throw new ResourceParsingException("Error parsing response from Spoonacular API.");
        }
        return result;
    }

    private SearchResults parseSearchResultResponse(String response) throws ResourceParsingException {
        SearchResults result;
        try {
            result = objectMapper.readValue(response, SearchResults.class);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error(response);
            throw new ResourceParsingException("Error parsing response from Spoonacular API.");
        }
        return result;
    }

    private String sendRequest(String url) throws ExternalApiException {
        LOGGER.debug("sendRequest(url={})", url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
                                               .get()
                                               .addHeader("x-rapidapi-host", API_HOST)
                                               .addHeader("x-rapidapi-key", API_KEY)
                                               .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null || !response.isSuccessful())
                throw new ExternalApiException("Failed receiving data from Spoonacular API.");
            return responseBody.string();
        } catch (IOException e) {
            throw new ExternalApiException("Could not receive data from Spoonacular API.");
        }
    }

    private String prepareUrl(long id) {
        return apiUrl + "/recipes/" + id + "/information?includeNutrition=false";
    }

    private String prepareUrl(List<String> include, List<String> exclude) {
        String options = buildOptions();
        String query = buildQuery(include, exclude);
        return apiUrl + options + query;
    }

    private String buildOptions() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENDPOINT_SEARCH_URL);
        sb.append("&number=").append(NUMBER_OF_RECIPES);
        if (INSTRUCTIONS_REQUIRED) sb.append("&instructionsRequired=true");
        if (LIMIT_LICENSE) sb.append("&limitLicense=true");
        if (SORT_RANDOM) sb.append("&sort=random");
        if (IGNORE_PANTRY) sb.append("&ignorePantry=true");
        if (FILL_INGREDIENTS) sb.append("&fillIngredients=true");
        return sb.toString();
    }

    private String buildQuery(List<String> include, List<String> exclude) {
        return "&includeIngredients=" + createParamString(include) + "&excludeIngredients=" + createParamString(exclude);
    }

    private String createParamString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(str -> sb.append(str).append(","));
        int lastCharIndex = sb.length() - 1;
        if (sb.charAt(lastCharIndex) == ',') sb.deleteCharAt(lastCharIndex);
        return sb.toString();
    }
}
