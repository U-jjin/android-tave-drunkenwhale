package org.androidtown.alcohol;

public class DrinkInfo {
    private double rate;
    private String DrinkType;
    private String DrinkName;
    DrinkInfo(double rate,String DrinkType,String DrinkName){
        this.rate=rate;
        this.DrinkType=DrinkType;
        this.DrinkName=DrinkName;
    }
    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDrinkName() {
        return DrinkName;
    }

    public void setDrinkName(String drinkName) {
        DrinkName = drinkName;
    }

    public void setDrinkType(String drinkType) {
        DrinkType = drinkType;
    }

    public double getRate() {
        return rate;
    }

    public String getDrinkType() {
        return DrinkType;
    }
}
