package pl.baranowski.dev.client;

import org.junit.jupiter.api.Test;
import pl.baranowski.dev.exception.ExternalApiException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

class SpoonacularApiClientTest {

    //TODO napisać testy
    SpoonacularApiClient underTest = new SpoonacularApiClient();
    @Test
    void get() throws ExternalApiException {
        underTest.get(645479);
        fail("not implemented");
    }
    @Test
    void find() throws ExternalApiException {
        underTest.find(Arrays.asList("banana", "avocado"), Arrays.asList("onion", "strawberry"));
        fail("not implemented");
    }
}