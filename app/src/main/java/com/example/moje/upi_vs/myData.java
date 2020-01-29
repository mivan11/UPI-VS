package com.example.moje.upi_vs;

public class myData {
    private String Battery, Temperature, Emission, Moisture;

    public myData(String Battery, String Temperature, String Emission, String Moisture) {
        this.Battery = Battery;
        this.Temperature = Temperature;
        this.Emission = Emission;
        this.Moisture = Moisture;
    }
    public String getBattery() {
        return Battery;
    }
    public void setBattery(String Battery) {
        this.Battery = Battery;
    }

    public String getTemperature() {
        return (Temperature);
    }
    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    public String getEmission() {
        return Emission;
    }
    public void setEmission(String Emission) {
        this.Emission = Emission;
    }

    public String getMoisture() {
        return Moisture;
    }
    public void setMoisturex(String Moisture) {
        this.Moisture = Moisture;
    }
}