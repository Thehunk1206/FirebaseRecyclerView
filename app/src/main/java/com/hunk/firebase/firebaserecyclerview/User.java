package com.hunk.firebase.firebaserecyclerview;

public class User {
    public String name,age,email;

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
