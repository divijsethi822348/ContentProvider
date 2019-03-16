package com.example.contentprovider.ModelClasses;

public class ContactsModel {
    String name;
    String number;
    String photo_uri;

    public String getPhoto_uri() {
        return photo_uri;
    }

    public void setPhoto_uri(String photo_uri) {
        this.photo_uri = photo_uri;
    }
//    public ContactsModel(String name, String number) {
//        this.name = name;
//        this.number = number;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
