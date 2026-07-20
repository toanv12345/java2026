/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.QLMT;

/**
 *
 * @author oocs
 */
public class Laptop extends Computer {
    private double weight;      
    private int batteryCapacity; 
    private double screenSize;   

    public Laptop(String id, String brand, String cpu, int ram, double price, double weight, int batteryCapacity, double screenSize) {
        super(id, brand, cpu, ram, price);
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
        this.screenSize = screenSize;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Weight: " + weight + "kg | Battery: " + batteryCapacity + "mAh | Screen: " + screenSize + " inch");
    }
}
