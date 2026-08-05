package com.example.QLMT;

import java.io.Serializable;

public class Computer implements IComputer, Serializable {
    private static final long serialVersionUID = 1L;
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
                " | CPU: " + cpu + " | RAM: " + ram +
                "GB | Price: " + String.format("%,.0f", price) + " VND");
    }
}