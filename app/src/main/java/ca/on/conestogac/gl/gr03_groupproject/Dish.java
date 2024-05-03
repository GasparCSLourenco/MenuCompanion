package ca.on.conestogac.gl.gr03_groupproject;

import java.io.Serializable;

public class Dish implements Serializable {
    String Name;
    String Description;
    double Price;
    int ImagePath;

    boolean available;


    public Dish(String name,String desc, double price, int img){
        this.Name = name;
        this.Description = desc;
        this.Price = price;
        this.ImagePath = img;
        this.available = true;
    }

    public String getName(){return this.Name;}
    public String getDescription(){return this.Description;}
    public Double getPrice(){return this.Price;}
    public void setPrice(double price){this.Price = price;}
    public int getImgPath(){return this.ImagePath;}

    public boolean getAvailable(){return this.available;}

    public void setAvailable(boolean isAvailable){this.available = isAvailable;}

    public String DisplayPrice(double price){
        return String.format("%.2f", price);
    }
}


