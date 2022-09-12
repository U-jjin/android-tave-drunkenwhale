package org.androidtown.alcohol.SearchClasses;

public class DBIDSearch {

    private String email;
    private String ID;

    //생성자
    public DBIDSearch(String email) {
        this.email = email;
    }

    public String getID() {
        String tmp_email = this.email;

        int index = tmp_email.indexOf("@");
        this.ID = tmp_email.substring(0, index);

        return ID;
    }
}
