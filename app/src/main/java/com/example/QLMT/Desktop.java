package com.example.QLMT;

public class Desktop extends Computer {

    private static final long serialVersionUID = 3L;
    private int powerSupply;
    private String caseType;

    public Desktop(String id, String brand, String cpu, int ram, double price, int powerSupply, String caseType) {
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
        System.out.print("DESKTOP | ");
        super.displayInfo();
        System.out.println(" | Power: " + powerSupply + "W | Case: " + caseType);
    }
}
