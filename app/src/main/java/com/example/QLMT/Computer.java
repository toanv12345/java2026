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
    private String brand;    
    private String cpu;
    private int ram;
    private double price;

    public Computer(String id, String brand, String cpu, int ram, double price) {
        this.id = id;
        this.brand = brand;
        this.cpu = cpu;
        this.ram = ram;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void displayInfo() {
        System.out.print("ID: " + id + " | Brand: " + brand + 
                         " | CPU: " + cpu + " | RAM: " + ram + "GB | Price: " + price + " VND");
    }
}
