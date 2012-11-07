/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id2212.hw2.item;

/**
 *
 * @author Marcel
 */
public class Item {
    
    public int id;
    public String name;
    public float price;
    
    public static int counter = 0;
    
    public Item(String n, float p) {
        this.name = n;
        this.price= p;
        this.id = counter;
        counter++;
    }
}
