package com.itfactory.project.user_service;


import com.itfactory.project.user_service.console_ui.MenuController;
import com.itfactory.project.user_service.service.DatabaseInitialService;
import com.itfactory.project.user_service.service.UserDao;

public class Main {
    public static void main(String[] args) {

        DatabaseInitialService.createMissingTable();
        UserDao dao = new UserDao();
        MenuController.showMenu(dao);
    }
}