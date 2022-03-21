package pl.baranowski.dev.client;

import org.junit.jupiter.api.Test;
import pl.baranowski.dev.exception.ExternalApiException;

import java.util.Arrays;

class SpoonacularApiFacadeTest {

    @Test
    void find() throws ExternalApiException {
        SpoonacularApiClient underTest = new SpoonacularApiClient();
        underTest.find(Arrays.asList("banana", "pepper"), Arrays.asList("onion", "strawberry"));
    }
}