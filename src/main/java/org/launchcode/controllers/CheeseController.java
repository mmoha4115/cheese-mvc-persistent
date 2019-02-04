package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;
    @Autowired
    private CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, Model model, @RequestParam int categoryId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }
        Category category = categoryDao.findOne(categoryId);
        newCheese.setCategory(category);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category/{id}",method = RequestMethod.GET)
    public String displayCheesesByCategory(Model model, @PathVariable int id){
        model.addAttribute("cheeses",categoryDao.findOne(id).getCheeses());
        model.addAttribute("title","Cheeses by "+categoryDao.findOne(id).getName());
        return "cheese/index";
    }

//    @RequestMapping(value = "edit/{cheeseId}" , method = RequestMethod.GET)
//    public String displayEditForm(Model model, @PathVariable int cheeseId){
//        model.addAttribute("cheese",cheeseDao.findOne(cheeseId));
//        return "cheese/edit";
//    }
//
//    @RequestMapping(value = "edit/{cheeseId}" , method = RequestMethod.POST)
//    public String processEditForm(@Valid Cheese newcheese,  Errors errors,Model model, int cheeseId , String name, String description){
//        if(errors.hasErrors()){
//            model.addAttribute(newcheese);
//            model.addAttribute("cheese",cheeseDao.findOne(cheeseId));
//            return "cheese/edit";
//        }
//
//        return index(model);
//    }
//}
}
