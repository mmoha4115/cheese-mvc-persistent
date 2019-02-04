package org.launchcode.models;

import org.launchcode.models.Cheese;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Mohamed Mohamed
 */

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3,max = 15,message = "A menu must have a name")
    private String name;

    @ManyToMany
    private List<Cheese> cheeses;

    public Menu(){

    }

    public Menu(String name){
        this.name = name;
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

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public void addCheese(Cheese cheese){
        cheeses.add(cheese);
    }

}
