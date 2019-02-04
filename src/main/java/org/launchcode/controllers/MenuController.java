package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Mohamed Mohamed
 */

@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private MenuDao menuDao;

    @RequestMapping(value = "")
    public String menuIndex(Model model){
        model.addAttribute("title","Menus");
        model.addAttribute("menus",menuDao.findAll());
        return "menu/index";
    }

    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String displayAddForm(Model model){
        model.addAttribute("title","Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add" , method = RequestMethod.POST)
    public String processDisplayAddForm(Model model, @ModelAttribute @Valid Menu menu, Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("title","Add Menu");
            model.addAttribute(menu);
            return "menu/add";
        }
        menuDao.save(menu);
        return "redirect:view/"+menu.getId();
    }

    @RequestMapping(value = "view/{id}",method = RequestMethod.GET)
    public String displayCheesesByCategory(Model model, @PathVariable int id){
        model.addAttribute("cheeses",menuDao.findOne(id).getCheeses());
        model.addAttribute("title","Cheeses by "+menuDao.findOne(id).getName());
        return "menu/view";
    }

//    @RequestMapping(value = "addcheese")
}
