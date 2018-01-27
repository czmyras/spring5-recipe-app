package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jt on 6/1/17.
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        log.debug("Getting recipe: " + id);
        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        return "recipe/show";
    }


    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedRecipe.getId() + "/show";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));

        return "recipe/recipeform";
    }


    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {
        log.debug("Deleting id: " + id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound() {
        log.error("Handling not found exception");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        return modelAndView;
    }


}
