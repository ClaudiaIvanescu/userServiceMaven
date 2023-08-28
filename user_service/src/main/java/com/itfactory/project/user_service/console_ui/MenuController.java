package com.itfactory.project.user_service.console_ui;



import com.itfactory.project.user_service.dto.User;
import com.itfactory.project.user_service.service.UserDao;

import java.util.List;
import java.util.Optional;

public class MenuController {
    public static void showMenu (UserDao userDao){
        while(true){
           char c = MenuUtil.readChoice("A - Add new user\n" +
                                     "B - View user by ID\n" +
                                     "C - Show all users\n" +
                                     "D - Delete a user\n" +
                                     "E - Update an existing user\n" +
                                     "Q - Quit", 'A', 'B', 'C', 'D','E', 'Q');

           switch (c){
               case 'A':
                   User newUser = EditUserController.readNewUser();
                   userDao.insert(newUser);
                   break;
               case 'B':
                   int id = MenuUtil.readInt("Insert id of user to be shown: ");
                   Optional<User> foundUser = userDao.getById(id);
                   if(foundUser.isPresent()){
                       System.out.println(foundUser);
                   }else{
                       System.out.println("There is no user with id: "+id);
                   }
                   break;
               case 'C':
                   List<User>  users = userDao.getAllUsers();
                   if(!users.isEmpty()){
                       users.forEach(System.out::println);
                   }else{
                       System.out.println("There is no use to be shown!");
                   }
                   break;
               case 'D':
                   int idDelete = MenuUtil.readInt("Insert user to be deleted: ");
                   Optional<User> userToBeDeleted = userDao.getById(idDelete);
                   if(userToBeDeleted.isPresent()){
                       userDao.delete(idDelete);
                   }else{
                       System.out.println("There is no user with id: "+idDelete);
                   }
                   break;
               case 'E':
                   int idUpdate = MenuUtil.readInt("Insert id of user for update: ");
                   Optional<User> userExist = userDao.getById(idUpdate);
                   if(userExist.isPresent()){
                       User updatedUser = EditUserController.readUserForUpdate(idUpdate);
                       userDao.getById(idUpdate).ifPresent(user -> userDao.update(updatedUser));
                   }else{
                       System.out.println("There is no user with id: "+idUpdate);
                   }
                   break;
               default:
                   System.out.println("Bye");
                   return;
           }
        }
    }
}
