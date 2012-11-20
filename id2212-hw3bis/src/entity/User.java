/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author alfredo
 */
public class User implements Serializable{
    
    public String name;
    public String pwd;
    
    public User(String username, String password){
        this.name=username;
        this.pwd=password;
    }
    
   
    
}
