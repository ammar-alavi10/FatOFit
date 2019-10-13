package com.hackmact.fatofit;

public class User {
    public String id, height, weight, gender, age;

    public User(){

    }

    public User(String id, String height, String weight,String age,String gender) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }
}
