package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @OneToMany
    @JoinColumn(name="categoery_id")
    private List<Cheese> cheeses = new ArrayList<>();

    //  C O N S T R U C T O R S  //

    public Category(){}
    public Category(String name){
        this.name = name;
    }

    //  G E T T E R S  &  S E T T E R S  //

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
