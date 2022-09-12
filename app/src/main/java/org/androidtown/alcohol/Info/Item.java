package org.androidtown.alcohol;

public class Item {
    int img;
    String Insta;
    String AlcoholName;


    Item(int img,String Insta,String AlocholName){
        this.img=img;
        this.Insta=Insta;
        this.AlcoholName=AlocholName;
    }

    public int getimg() {
        return img;
    }

    public String getInsta() {
        return Insta;
    }

    public String getAlocholName() {
        return AlcoholName;
    }

}
