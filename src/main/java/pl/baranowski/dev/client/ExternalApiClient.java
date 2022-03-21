package pl.baranowski.dev.client;

import pl.baranowski.dev.exception.ExternalApiException;

import java.util.List;

public interface ExternalApiClient {
    String get(long id) throws ExternalApiException;
    String find(List<String> include, List<String> exclude) throws ExternalApiException;
}
