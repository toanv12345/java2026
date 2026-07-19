/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.QLMT;

/**
 *
 * @author oocs
 */
public class Computer {
    private String id;          
    private String name;        
    private String brand;    
    private String specs;
    private double price;
    private boolean inStock;

    public Computer(String id, String name, String brand, String specs, double price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.specs = specs;
        this.price = price;
        this.inStock= inStock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Computer{" + "id=" + id + ", name=" + name + ", brand=" + brand + ", specs=" + specs + ", price=" + price + ", inStock=" + inStock + '}';
    }
    
    

}
