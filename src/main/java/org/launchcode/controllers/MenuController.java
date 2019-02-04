package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
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
        model.addAttribute("menu",menuDao.findOne(id));
        model.addAttribute("title","Cheeses by "+menuDao.findOne(id).getName());
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}",method = RequestMethod.GET)
    public String displayAddCheeseToMenuForm(Model model,@PathVariable int id){
        Menu menu = menuDao.findOne(id);

        AddMenuItemForm form = new AddMenuItemForm(
                cheeseDao.findAll(),
                menu
        );
        model.addAttribute("title","Add item to menu "+menu.getName());
        model.addAttribute("form",form);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String processAddCheeseToMenuForm(Model model,@ModelAttribute @Valid AddMenuItemForm form,Errors errors){
        if (errors.hasErrors()){
            model.addAttribute("form",form);
            return "menu/add-item";
        }

        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());
        Menu theMenu = menuDao.findOne(form.getMenuId());
        theMenu.addCheese(theCheese);
        menuDao.save(theMenu);

        return "redirect:/menu/view/"+theMenu.getId();
    }
}
