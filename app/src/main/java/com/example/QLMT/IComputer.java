package com.example.QLMT;

public interface IComputer {

    String getId();

    void setId(String id);

    String getBrand();

    void setBrand(String brand);

    String getCpu();

    void setCpu(String cpu);

    int getRam();

    void setRam(int ram);

    double getPrice();

    void setPrice(double price);

    void displayInfo();
}