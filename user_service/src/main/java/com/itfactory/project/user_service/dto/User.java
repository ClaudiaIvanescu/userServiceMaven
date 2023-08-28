package com.itfactory.project.user_service.dto;

import java.util.Objects;

public class User {
    private final long id;
    private final String name;
    private final String surname;
    private final String email;
    private final int age;

    public User(long id, String name, String surname, String email, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
    }

    public User(String name, String surname, String email, int age) {
        this(-1, name, surname, email, age);
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && name.equals(user.name) && surname.equals(user.surname) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
