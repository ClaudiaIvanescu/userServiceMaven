package com.itfactory.project.user_service.console_ui;


import com.itfactory.project.user_service.dto.User;

public class EditUserController {
    public static User readNewUser() {
        System.out.println("Enter details to create new user : ");
        String name = MenuUtil.readString("Name: ", false);
        String surname = MenuUtil.readString("Surname: ", false);
        String email = MenuUtil.readString("Email: ", false);
        int age = MenuUtil.readInt("Age: ");
        return new User(name, surname, email, age);
    }

    public static User readUserForUpdate(long id) {
        System.out.println("Enter details to edit existing user with id = "+ id+" : ");
        String name = MenuUtil.readString("Name: ", false);
        String surname = MenuUtil.readString("Surname: ", false);
        String email = MenuUtil.readString("Email: ", false);
        int age = MenuUtil.readInt("Age: ");

        return new User(id, name, surname, email, age);
    }
}
