package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;


    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewDisplay(Model model, @PathVariable int menuId) {

        Menu menu = menuDao.findOne(menuId);
        model.addAttribute(menu);
        model.addAttribute("title", menu.getName());

        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {

    Menu menu = menuDao.findOne(menuId);

    AddMenuItemForm form = new AddMenuItemForm(cheeseDao.findAll(), menu);

    model.addAttribute("cheeses", cheeseDao.findAll());
    model.addAttribute("title", "Add Item to " + menu.getName());
    model.addAttribute("form", form);

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem ( Model model, @ModelAttribute @Valid AddMenuItemForm form,
                                      Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            model.addAttribute("cheeses", cheeseDao.findAll());
            return "menu/add-item";
        }

        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());
        Menu theMenu = menuDao.findOne(form.getMenuId()); //could have also done menu = getMenu
        theMenu.addItem(theCheese); //adding the cheese to the menu
        menuDao.save(theMenu);

      return "redirect:view/" + theMenu.getId();
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {

        //menuDao.count()
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());
        //model.addAttribute("menues", menuDao.findAll());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMenuForm (@ModelAttribute @Valid Menu menu,
                                      Errors errors, Model model) {

        model.addAttribute("title", menu.getName());

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(menu);

        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "")
    private String menu(Model model){
        model.addAttribute("title", "Menus");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

}

// paused work at Add a Menu //
// https://education.launchcode.org/skills-back-end-java/studios/cheese-mvc-persistent/many-to-many/