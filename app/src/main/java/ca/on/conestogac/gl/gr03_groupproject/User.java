package ca.on.conestogac.gl.gr03_groupproject;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    String Name;
    public String getName(){return this.Name;}
    String Email;
    public String getEmail(){return this.Email;}
    int Table;
    public int Table(){return this.Table;}

    List<Order> orderList;
    UserStatus userStatus;
    String Password;
    public String getPassword(){return this.Password;}

    enum UserStatus{
        Ordering,
        Waiting,
        Checkout,
        NoReg
    } //NoReg when user is not registered. User is unable to perform actions

    public User(String name,String email,String password){
        this.Name = name;
        this.userStatus = UserStatus.Ordering;
        this.Email = email;
        this.Password = password;

    }


    public User(){
        CreateGuestUser();
    }

    public void AddOrder(Order order){
        if(userStatus != UserStatus.NoReg && userStatus != UserStatus.Checkout){
            if(order != null){
                orderList.add(order);
            }
        }

    }

    public void RemoveOrder(Order order){
        if(userStatus != UserStatus.NoReg && userStatus != UserStatus.Checkout) {
            if (order != null && orderList.contains(order)) {
                orderList.remove(order);
            } else {
                Log.d("NoOrder", "Order does not exist in Order List");
            }
        }
    }

    public void CreateGuestUser(){
        this.Name = "Guest";
        this.Email = "guest@";
        this.Password = "";
        this.userStatus = UserStatus.NoReg;
    }

    public void SetTable(int table){
        this.Table = table;
    }
    public int getTable(){
        return this.Table;
    }
}
