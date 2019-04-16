package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany
    private List<Cheese> cheese;


    public Menu(){}

    public Menu(String name) {
        this.name = name;
    }

    public void addItem(Cheese item){
        cheese.add(item);
    }

    // Remove a cheese that is attached to a menu
    public void removeItem(Cheese item) {
        cheese.remove(item);
    }


    // G E T T E R S  &  S E T T E R S  //
    public List<Cheese> getCheese() {
        return cheese;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
