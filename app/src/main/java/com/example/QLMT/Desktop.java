/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.QLMT;

/**
 *
 * @author oocs
 */
public class Desktop extends Computer {
    private int powerSupply;
    private String caseType;
    
    public Desktop (String id, String brand, String cpu, int ram, double price, int powerSupply, String caseType){
        super(id, brand, cpu, ram, price);
        this.powerSupply = powerSupply;
        this.caseType = caseType;
    }

    public int getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(int powerSupply) {
        this.powerSupply = powerSupply;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Power: " + powerSupply + "W | Case: " + caseType);
    }
}
