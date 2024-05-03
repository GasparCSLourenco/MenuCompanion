package ca.on.conestogac.gl.gr03_groupproject;

import java.util.ArrayList;

public class Seeding {


    public Seeding(){

    }
    public ArrayList<Dish> SeedDB(){
        ArrayList<Dish> dishList = new ArrayList<>();
        dishList.add(new Dish("Hot Chocolate","Medium dark hot chocolate",5.99,R.drawable.hot_choco)) ;
        dishList.add(new Dish("Coffee","Arabic grain, medium toast",3.99,R.drawable.coffee) ) ;
        dishList.add(new Dish("Panini","Panini bread with goat cheese and seasonal vegetables",11.99,R.drawable.panini)) ;
        dishList.add(new Dish("Omelet","Omelette stuffed with sirloin steak and mushrooms",14.99,R.drawable.omelet));
        dishList.add(new Dish("Croissant","Croissant filled with eggs and bacon",10.49,R.drawable.croissant));
        dishList.add(new Dish("Donut","Homemade Chocolate Donut",4.99,R.drawable.donut));
        return dishList;
    }
}
