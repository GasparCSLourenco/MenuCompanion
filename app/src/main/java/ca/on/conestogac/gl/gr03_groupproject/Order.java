package ca.on.conestogac.gl.gr03_groupproject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

    ArrayList<Dish> Dishes;

    String Date;

    User User;

    String Specifications;

    int Table;

    Status status;

    enum Status{
        Ordered("Ordered"),
        Preparing("Preparing"),
        Finished ("Finished"),
        Canceled ("Canceled");
        private String value;
        Status(String value){
            this.value = value;
        }
    }

    public String getStatus(){
        return status.value;
    }
    public User getUser() { return this.User;}

    public ArrayList<Dish> getDishes(){
        return this.Dishes;
    }

    public Order(ArrayList<Dish> dishes, User user){
        this.Dishes = dishes;
        LocalDate now = LocalDate.now();
        this.Date = String.valueOf(now);
        this.User = user;
        this.Table = user.getTable();
        status = Status.Ordered;
    }

    public Order(ArrayList<Dish> dishes, String date, User user, int table){
        this.Dishes = dishes;
        this.Date = date;
        this.User = user;
        this.Table = table;
        this.status = Status.Finished;
    }

    public String getDate(){
        return this.Date;
    }
    public int getTable(){
        return this.Table;
    }

    public String getSpecifications(){
        return this.Specifications;
    }

    public void setSpecifications(String specification){
        this.Specifications = specification;
    }

    public int getOrderSize(){
        return this.getDishes().size();
    }


}
