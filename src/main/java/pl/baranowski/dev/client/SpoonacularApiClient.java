package pl.baranowski.dev.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.baranowski.dev.exception.ExternalApiException;

import java.util.Arrays;
import java.util.List;

public class SpoonacularApiClient implements ExternalApiClient {
    private final Logger LOGGER = LoggerFactory.getLogger(SpoonacularApiClient.class);
    private final static String API_HOST = "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com";
    private final static String API_KEY = "24ba75ae75msh0b52031a504df72p1e69eejsndc3281a731a9";
    private final static String API_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";
    private final static String ENDPOINT_SEARCH_URL = "recipes/complexSearch?";
    private final static boolean INSTRUCTIONS_REQUIRED = true;
    private final static boolean LIMIT_LICENSE = true;
    private final static boolean SORT_RANDOM = true;
    private final static boolean IGNORE_PANTRY = true;
    private final static boolean FILL_INGREDIENTS = true;
    private final static int NUMBER_OF_RECIPES = 10;

    @Override
    public String get(long id) throws ExternalApiException {
        LOGGER.debug("get(id={})", id);

        String result = sendRequest(prepareUrl(id));
        LOGGER.debug("Returning: {}", result);
        return result;
    }

    @Override
    public String find(List<String> include, List<String> exclude) throws ExternalApiException {
        LOGGER.debug("find(include={}, exclude={})",
                     Arrays.toString(include.toArray()),
                     Arrays.toString(exclude.toArray()));

        String result = sendRequest(prepareUrl(include, exclude));
        LOGGER.debug("Returning: {}", result);
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
            return response.body().string();
        } catch (Exception e) {
            throw new ExternalApiException("Could not receive data from spoonacular API.");
        }
    }
    private String prepareUrl(long id) {
        return API_URL + "/recipes/" + id + "/information?includeNutrition=false";
    }
    private String prepareUrl(List<String> include, List<String> exclude) {
        String options = buildOptions();
        String query = buildQuery(include, exclude);
        return API_URL + options + query;
    }
    private String buildOptions() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENDPOINT_SEARCH_URL);
        sb.append("&number=").append(NUMBER_OF_RECIPES);
        if(INSTRUCTIONS_REQUIRED) sb.append("&instructionsRequired=true");
        if(LIMIT_LICENSE) sb.append("&limitLicense=true");
        if(SORT_RANDOM) sb.append("&sort=random");
        if(IGNORE_PANTRY) sb.append("&ignorePantry=true");
<<<<<<< HEAD
        if(FILL_INGREDIENTS) sb.append("&fillIngredients=true");
        return sb.toString();
    }
    private String buildQuery(List<String> include, List<String> exclude) {
        return "&includeIngredients=" + createParamString(include) + "&excludeIngredients=" + createParamString(exclude);
    }
    private String createParamString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(str -> sb.append(str).append(","));
        int lastCharIndex = sb.length() - 1;
        if(sb.charAt(lastCharIndex) == ',') sb.deleteCharAt(lastCharIndex);
=======
        return sb.toString();
    }
    private String buildQuery(List<String> include, List<String> exclude) {
        return "&include=" + createQuery(include) + "&exclude=" + createQuery(exclude);
    }
    private String createQuery(List<String> include) {
        StringBuilder sb = new StringBuilder();
        include.forEach(str -> sb.append(str).append("%2C%20"));
>>>>>>> 55aefde3b2b015dd3d464518d1a9ec12a40816fc
        return sb.toString();
    }
}
