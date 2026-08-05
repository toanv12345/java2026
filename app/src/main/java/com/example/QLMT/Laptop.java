package com.example.QLMT;

public class Laptop extends Computer {

    private static final long serialVersionUID = 2L;
    private double weight;
    private int batteryCapacity;
    private double screenSize;

    public Laptop(String id, String brand, String cpu, int ram, double price, double weight, int batteryCapacity,
            double screenSize) {
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
        System.out.print("LAPTOP  | ");
        super.displayInfo();
        System.out.println(" | Weight: " + weight + "kg | Battery: " + batteryCapacity + "mAh | Screen: " + screenSize + " inch");
    }
}
