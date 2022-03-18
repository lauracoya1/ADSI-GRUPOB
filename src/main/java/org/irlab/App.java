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
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.entities.Cliente;
import org.irlab.model.entities.Tarea;
import org.irlab.model.entities.Tipo;
import org.irlab.model.entities.User;
import org.irlab.model.entities.Vehiculo;
import org.irlab.model.exceptions.NoTareasException;
import org.irlab.model.exceptions.UserNotFoundException;
import org.irlab.model.services.UserService;
import org.irlab.model.services.UserServiceImpl;
import org.irlab.model.services.ClientService;
import org.irlab.model.services.ClientServiceImpl;
import org.irlab.model.services.VehicleService;
import org.irlab.model.services.VehicleServiceImpl;
import org.irlab.model.services.TipoService;
import org.irlab.model.services.TipoServiceImpl;

public class App {


    private enum Command {
        GREET_USER, 
        CHANGE_GREETING, 
        CLOSE_SESSION, 
        SHOW_SCHEDULE, 
        ADD_CLIENT,
        ADD_VEHICLE,
        ADD_TYPE,
        EXIT
    }

    private static final int CORRECT_SHUTDOWN = 50000;

    private static UserService userService = null;
    private static ClientService clientService = null;
    private static VehicleService vehiculoService = null;
    private static TipoService tipoService = null;

    private static Scanner scanner = null;

    private static void init() {
        userService = new UserServiceImpl();
        clientService = new ClientServiceImpl();
        vehiculoService = new VehicleServiceImpl();
        tipoService = new TipoServiceImpl();
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
        System.out.println("  4) Show schedule");
        System.out.println("  5) Add client");
        System.out.println("  6) Add vehicle");
        System.out.println("  7) Add type");

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
                    case '4':
                        return Command.SHOW_SCHEDULE;
                    case '5':
                        return Command.ADD_CLIENT;
                    case '6':
                        return Command.ADD_VEHICLE;
                    case '7':
                        return Command.ADD_TYPE;
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
        String currentUser = "";
        String password = "";
        System.out.println();

        for (User user : usersList) {
            System.out.println((usersList.indexOf(user)+1)+ " " + user.getName());

        }
        System.out.println();
        while (!userService.checkCredentials(currentUser, password)){
            currentUser = readInput("Dime quién eres: ", "You must supply a user name");
            password = readInput("Contraseña: ", "You must supply a valid password");
        }

        return currentUser;
    }

    private static void showSchedule(String user) throws NoTareasException {
        String fecha;
        System.out.println("Introduzca la fecha del horario a buscar");
        fecha = readInput("Fecha: ", "Debes introducir una fecha");

        List<Tarea> tareaList = userService.showHorario(user, LocalDate.parse(fecha));

        System.out.println();


        for (Tarea tarea : tareaList) {
            System.out.println();
            System.out.println("Tarea: " + tarea.getTipo().getNombre());
            System.out.println("Fecha de inicio: " + tarea.getDateTime());
            System.out.println("Fecha de fin: " + tarea.getDateTime().plusHours(tarea.getDuracion())); //ver en que formato se suma la duracion
            System.out.println("Elevador: " + tarea.getElevador().getCodigo());
            System.out.println("Vehiculo: " + tarea.getTrabajo().getVehiculo().getMatricula());
            System.out.println("Mecanicos: ");
            for(User usuario: tarea.getMecanicos())
                System.out.println("\t" + usuario.getName());;
        }
        System.out.println();
    }

    private static Cliente addClient() {
        boolean exists = true;
        String DNI;
        do {
            System.out.println("Introduzca DNI de cliente");
            DNI = readInput("DNI:", "Es necesario introducir un DNI");

            exists = clientService.exists(DNI);
        } while(exists);

        Cliente cliente = new Cliente(DNI);

        System.out.println("Introduzca nombre de cliente");
        String nombre = readInput("Nombre:", "Es necesario introducir nombre");
        cliente.setNombre(nombre);

        System.out.println("Introduzca telefono de cliente");
        String telefono = readInput("Teléfono:", "Es necesario introducir telefono");
        cliente.setTelefono(telefono);

        clientService.insertClient(cliente);

        return cliente;
    }

    private static Vehiculo addVehiculo() {
        boolean exists = true;
        String matricula;
        do {
            System.out.println("Introduzca matricula del vehiculo");
            matricula = readInput("Matricula:", "Es necesario introducir una matricula");

            exists = vehiculoService.exists(matricula);
        } while(exists);


        System.out.println("Introduzca tipo del vehiculo");
        String tipo =  readInput("Tipo: ", "Es necesario introducir un tipo");

        Vehiculo vehiculo = new Vehiculo(matricula);
        vehiculo.setTipo(tipo);

        List<Cliente> clientes = clientService.listAllClients();

        int index = 0;
        boolean inputInvalid = true;
        do {
            for (Cliente cliente : clientes) {
                System.out.printf("%d ) %s \n", index, cliente.toString());
                index++;
            }
            System.out.println(" n ) Nuevo cliente");
            String seleccion =  readInput("Selección: ", "Seleccione un valor");

            if (seleccion.equals("n")) {

                vehiculo.setCliente(addClient());
                inputInvalid = false;
            } else if (clientes.size() > 0 && Integer.parseInt(seleccion) >= 0 && Integer.parseInt(seleccion) < clientes.size()) {
                vehiculo.setCliente(clientes.get(Integer.parseInt(seleccion)));
                inputInvalid = false;
            } 
        } while (inputInvalid);

        vehiculoService.insertVehiculo(vehiculo);

        return vehiculo;
    }

    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private static Tipo addTipo() {
        boolean exists = true;
        String nombre;
        do {
            System.out.println("Introduzca nombre del tipo");
            nombre = readInput("Nombre:", "Es necesario introducir un nombre");

            exists = tipoService.exists(nombre);
        } while(exists);

        Tipo tipo = new Tipo(nombre);
        String duracion = "";
        String precio = "";

        System.out.println("Introduzca una duración");
        do {
            duracion = readInput("Duracion:", "Es necesario introducir una duración");
        } while (!isInteger(duracion));
        tipo.setDuracion(Integer.parseInt(duracion));

        System.out.println("Introduzca precio");
        do {
            precio = readInput("Precio:", "Es necesario introducir un precio");
        } while (!isInteger(precio));
        tipo.setPrecio(Integer.parseInt(precio));

        tipoService.insertTipo(tipo);

        return tipo;
    }



    public static void main(String[] args) throws SQLException, NoTareasException {
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
                case SHOW_SCHEDULE:
                    showSchedule(currentUser);
                    break;
                case ADD_CLIENT:
                    addClient();
                    break;
                case ADD_VEHICLE:
                    addVehiculo();
                    break;
                case ADD_TYPE:
                    addTipo();
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
