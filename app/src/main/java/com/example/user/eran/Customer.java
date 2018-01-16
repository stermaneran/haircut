package com.example.user.eran;

/**
 * Created by shay on 19/12/17.
 * represents a customer object
 */

public class Customer {

    String FirstName, LastName, UserName, Email, Password, Street, City, id, Photo, Type, Gender;

    /**
     * default ctor
     */
    public Customer() {
        //for fire base
    }

    /**
     * ctor
     */
    public Customer(String fname, String lname, String uname, String email,
                    String password, String street, String city, String id, String path, String gender) {

        this.FirstName = fname;
        this.LastName = lname;
        this.UserName = uname;
        this.Email = email;
        this.Password = password;
        this.Street = street;
        this.City = city;
        this.id = id;
        this.Photo = path;
        this.Gender = gender;
        this.Type = "Non-Admin";

    }
}