package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Mohamed Mohamed
 */

@Controller
@RequestMapping(value = "category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String displayAllCategories(Model model){
        model.addAttribute("title","Categories");
        model.addAttribute("categories",categoryDao.findAll());
        return "category/index";
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String displayAddForm(Model model){
        model.addAttribute("title","Add Category");
        model.addAttribute(new Category());
        return "category/add";
    }

    @RequestMapping(value = "add" , method = RequestMethod.POST)
    public String processDisplayAddForm(Model model, @ModelAttribute @Valid Category newCategory, Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("title","Add Category");
            model.addAttribute("category",newCategory);
            return "category/add";
        }

        return "redirect:";
    }

}