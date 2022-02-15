/**
 * Copyright 2022 Information Retrieval Lab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.irlab;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.daos.UserDao;
import org.irlab.model.entities.User;
import org.irlab.model.exceptions.UserNotFoundException;
import org.irlab.model.services.UserService;
import org.irlab.model.services.UserServiceImpl;

public class App {


    private enum Command {
        GREET_USER, CHANGE_GREETING, CLOSE_SESSION, EXIT
    }

    private static final int CORRECT_SHUTDOWN = 50000;

    private static UserService userService = null;

    private static Scanner scanner = null;

    private static void init() {
        userService = new UserServiceImpl();
    }

    private static void shutdown() throws SQLException {
        AppEntityManagerFactory.close();

        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException e) {
            if (e.getErrorCode() != CORRECT_SHUTDOWN) {
                throw e;
            }
        }
    }

    private static Command getCommand() {
        System.out.println("Choose an option:");
        System.out.println("  1) Greet user");
        System.out.println("  2) Change user greeting");
        System.out.println("  3) Close user session");
        System.out.println();
        System.out.println("  q) Exit");
        System.out.println();
        while (true) {
            System.out.print("Option: ");
            String input = scanner.nextLine();
            if (input.length() == 0) {
                System.out.println("An option needs to be introduced");
            } else if (input.length() > 1) {
                System.err.println(input + " is not a valid option");
            } else {
                switch (input.charAt(0)) {
                    case '1':
                        return Command.GREET_USER;
                    case '2':
                        return Command.CHANGE_GREETING;
                    case '3':
                        return Command.CLOSE_SESSION;
                    case 'q':
                        return Command.EXIT;
                    default:
                        System.out.println(input + " is not a valid option");
                }
            }
        }
    }

    private static String readInput(String message, String errorMessage) {
        String result = null;
        while (result == null) {
            System.out.print(message);
            String input = scanner.nextLine();
            if (input.length() == 0) {
                System.out.println(errorMessage);
            } else {
                result = input;
            }
        }
        return result;
    }


    private static String greetUser(String currentUser) {
        if(currentUser == null){
            currentUser = readInput("User name: ", "You must supply a user name");
        }
        String greetingMessage = userService.greet(currentUser);
        System.out.println(greetingMessage);
        if(greetingMessage.equals("User not found, Sorry!")){
            return currentUser = null;
        }
        return currentUser;
    }
    //va a haber que dejar la opcion de change greeting y tal y va a haber que poner un "dime quien eres"
    private static String changeGreeting(String currentUser) { //va a haber que tocar para que recuerde que usuario lo esta utilizando
        if (currentUser == null){
            currentUser = readInput("User name: ", "You must supply a user name");
        }
        String newGreeting = readInput("Greeting message: ",
                "You must supply a new greeting message");
        try {
            userService.setUserGreeting(currentUser, newGreeting);
            System.out.println("User greeting changed");
        } catch (UserNotFoundException e) {
            System.out.println(String.format(
                    "Greeting could not be changed, due to the following error:\n%s",
                    e.getMessage()));
            currentUser = null;
        }
        return currentUser;
    }

    private static String whoAreYou(){
        List<User> usersList = userService.listAllUsers();
        System.out.println();

        for (User user : usersList) {
            System.out.println((usersList.indexOf(user)+1)+ " " + user.getName());

        }
        System.out.println();
        String currentUser = readInput("Dime quién eres: ", "You must supply a user name");
        return currentUser;

    }

    public static void main(String[] args) throws SQLException {
        init();
        boolean exit = false;
        scanner = new Scanner(System.in);
        String currentUser = whoAreYou();
        while (!exit) {
            System.out.println();
            Command command = getCommand();
            switch (command) {
                case GREET_USER:
                    currentUser = greetUser(currentUser);
                    break;
                case CHANGE_GREETING:
                    currentUser = changeGreeting(currentUser);
                    break;
                case CLOSE_SESSION:
                    currentUser = whoAreYou();
                    break;
                case EXIT:
                    exit = true;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command);
            }

        }

        shutdown();
    }
}
