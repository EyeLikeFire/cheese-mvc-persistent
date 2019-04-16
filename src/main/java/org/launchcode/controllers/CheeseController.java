package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    private MenuDao menuDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {

        if( categoryDao.count() < 1 ){
            //If there are no categories add this one by defualt
            categoryDao.save(new Category("Generic"));
        }

        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        //model.addAttribute("cheeseTypes", CheeseType.values());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, Model model, @RequestParam int categoryId) {

        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryDao.findAll());
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
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


            // NEED TO CATCH EXCEPTION FOR CHEESES ADDED TO A MENU
            //Cheese theCheese = cheeseDao.findOne(cheeseId);
            //System.out.println(theCheese);
            //System.out.println(theCheese.getName());

            //List<Menu> menus = theCheese.getMenues();

            //System.out.println(menus);
            //System.out.println(menus.size());

            //if(!menus.isEmpty()) {
            //    System.out.println("is empty");
            //    for (Menu theMenu : menus) {
            //        theMenu.removeItem(theCheese);
            //    }
            //} else {
            //    System.out.println("is not empty");
            //}

            //Cheese theCheese = cheeseDao.findOne(cheeseId);
            //menuDao.delete(theCheese);
            //menuDao.cheese.remove(cheeseId);
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

}
