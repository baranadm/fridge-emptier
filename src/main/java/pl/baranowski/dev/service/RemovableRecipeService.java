package pl.baranowski.dev.service;

public interface RemovableRecipeService extends RecipeService {
    boolean remove(long id);
}
