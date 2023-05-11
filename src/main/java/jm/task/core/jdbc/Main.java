package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Bob", "Dilan", (byte)56);
        service.saveUser("Tom", "Riddle", (byte)38);
        service.saveUser("Don", "Baton", (byte)26);
        service.saveUser("Senior", "Pomidor", (byte)86);
        service.getAllUsers().forEach(System.out::println);
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}