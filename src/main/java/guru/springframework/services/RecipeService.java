package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    public Recipe findById(long id);

    public RecipeCommand findCommandById(long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

}
