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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.entities.Cliente;
import org.irlab.model.entities.Elevador;
import org.irlab.model.entities.Role;
import org.irlab.model.entities.Tarea;
import org.irlab.model.entities.Tipo;
import org.irlab.model.entities.Trabajo;
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
import org.irlab.model.services.ElevadorService;
import org.irlab.model.services.ElevadorServiceImpl;
import org.irlab.model.services.RoleService;
import org.irlab.model.services.RoleServiceImpl;
import org.irlab.model.services.TrabajoService;
import org.irlab.model.services.TrabajoServiceImpl;
import org.irlab.model.services.TareaService;
import org.irlab.model.services.TareaServiceImpl;

public class App {


    private enum Command {
        GREET_USER, 
        CHANGE_GREETING, 
        CLOSE_SESSION, 
        SHOW_SCHEDULE, 
        ADD_CLIENT,
        ADD_VEHICLE,
        ADD_TYPE,
        ADD_ELEVATOR,
        ADD_USER,
        ADD_TASK,
        EXIT
    }

    private static final int CORRECT_SHUTDOWN = 50000;

    private static UserService userService = null;
    private static ClientService clientService = null;
    private static VehicleService vehiculoService = null;
    private static TipoService tipoService = null;
    private static ElevadorService elevadorService = null;
    private static RoleService roleService = null;
    private static TrabajoService trabajoService = null;
    private static TareaService tareaService = null;

    private static Scanner scanner = null;

    private static void init() {
        userService = new UserServiceImpl();
        clientService = new ClientServiceImpl();
        vehiculoService = new VehicleServiceImpl();
        tipoService = new TipoServiceImpl();
        elevadorService = new ElevadorServiceImpl();
        roleService = new RoleServiceImpl();
        trabajoService = new TrabajoServiceImpl();
        tareaService = new TareaServiceImpl();
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
        System.out.println("  8) Add elevator");
        System.out.println("  9) Add user");
        System.out.println("  a) Add task");

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
                    case '8':
                        return Command.ADD_ELEVATOR;
                    case '9':
                        return Command.ADD_USER;
                    case 'a':
                        return Command.ADD_TASK;
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

    private static void showSchedule(String user) throws NoTareasException, UserNotFoundException {
        String fecha;
        System.out.println("Introduzca la fecha del horario a buscar");
        fecha = readInput("Fecha: ", "Debes introducir una fecha");

        List<Tarea> tareaList = userService.showHorario(user, LocalDate.parse(fecha));

        System.out.println();


        for (Tarea tarea : tareaList) {
            System.out.println();
            System.out.println("Tipo tarea: " + tarea.getTipo().getNombre());
            System.out.println("Fecha de inicio: " + tarea.getDateTime());
            System.out.println("Duracion: " + tarea.getDuracion() + "minutos");
            System.out.println("Elevador: " + tarea.getElevador().getCodigo());
            System.out.println("Vehiculo: " + tarea.getTrabajo().getVehiculo().getMatricula());
            if(userService.getByUserName(user).getRole().getRoleName().equals("admin")){
                System.out.println("Mecanicos: ");
                for(User usuario: tarea.getMecanicos())
                    System.out.println("\t" + usuario.getName());
            }

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

        boolean inputInvalid = true;
        do {
            int index = 0;
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

    private static Elevador addElevator() {
        boolean exists = true;
        String codigo;
        do {
            System.out.println("Introduzca código del elevador");
            codigo = readInput("Código:", "Es necesario introducir un código");

            exists = elevadorService.exists(codigo);
        } while(exists);

        Elevador elevador = new Elevador(codigo);

        elevadorService.insertElevador(elevador);

        return elevador;
    }

    private static User addUser() {
        boolean exists = true;
        String nombre, dni;


        do {
            System.out.println("Introduzca DNI");
            dni = readInput("DNI:", "Es necesario introducir un DNI");
            System.out.println("Introduzca Nombre");
            nombre = readInput("Nombre:", "Es necesario introducir un nombre");

            exists = userService.exists(nombre);
            if(exists){System.out.println("El usuario con ese nombre ya existe");}
        } while(exists);

        User user = new User(dni);
        user.setName(nombre);


        System.out.println("Introduzca el primer apellido");
        String apellido1 = readInput("Primer apellido:", "Es necesario introducir el primer apellido");
        user.setApellido1(apellido1);

        System.out.println("Introduzca el segundo apellido");
        String apellido2 = readInput("Segundo apellido:", "Es necesario introducir el segundo apellido");
        user.setApellido2(apellido2);

        System.out.println("Introduzca contraseña");
        String password = readInput("Contraseña:", "Es necesario introducir una contraseña");

        /*List<Role> roles = roleService.listAllRoles();

        System.out.println("Con rol de: ");
        boolean inputInvalid = true;
        do {
            int index = 0;
            for (Role r : roles) {
                System.out.printf("%d ) %s \n", index, r.toString());
                index++;
            }
            String seleccion =  readInput("Selección: ", "Seleccione un valor");
            if (roles.size() > 0 && Integer.parseInt(seleccion) >= 0 && Integer.parseInt(seleccion) < roles.size()) {
                user.setRole(roles.get(Integer.parseInt(seleccion)));
                inputInvalid = false;
            } 
        } while (inputInvalid);*/

        List<Role> roles = roleService.listAllRoles();
        user.setRole(roles.get(1));

        System.out.println("Introduzca su fecha de nacimiento");
        String fechaNacimiento = readInput("Fecha nacimiento:", "Es necesario introducir su fecha de nacimiento");
        user.setFechaNacimiento(LocalDate.parse(fechaNacimiento));

        String cuentaBancaria;
        do{
            cuentaBancaria = readInput("Cuenta bancaria:", "Es necesario introducir su cuenta bancaria");

        }while(cuentaBancaria.length() != 16);

        user.setCuentaBancaria(Long.parseLong(cuentaBancaria));

        user.setPassword(password);
        userService.insertUser(user);

        return user;
    }

    private static Tarea addTask() {

        Tarea tarea = new Tarea();
        boolean inputInvalid = true;

        // ADD TIPO
        System.out.println("Tipo de tarea: ");
        List<Tipo> tipos = tipoService.listAllTipos();
        do {
            int index = 0;
            for (Tipo t : tipos) {
                System.out.printf("%d ) %s \n", index, t.toString());
                index++;
            }
            String seleccion =  readInput("Selección: ", "Seleccione un valor");
            if (tipos.size() > 0 && Integer.parseInt(seleccion) >= 0 && Integer.parseInt(seleccion) < tipos.size()) {
                tarea.setTipo(tipos.get(Integer.parseInt(seleccion)));
                inputInvalid = false;
            } 
        } while (inputInvalid);

        // ADD ELEVADOR
        inputInvalid = true;
        System.out.println("Elevador asignado: ");
        List<Elevador> elevadores = elevadorService.listAllElevadores();
        do {
            int index = 0;
            for (Elevador e : elevadores) {
                System.out.printf("%d ) %s \n", index, e.toString());
                index++;
            }
            String seleccion =  readInput("Selección: ", "Seleccione un valor");
            if (elevadores.size() > 0 && Integer.parseInt(seleccion) >= 0 && Integer.parseInt(seleccion) < elevadores.size()) {
                tarea.setElevador(elevadores.get(Integer.parseInt(seleccion)));
                inputInvalid = false;
            } 
        } while (inputInvalid);

        // ADD TRABAJO
        inputInvalid = true;
        System.out.println("Trabajo asignado: ");
        List<Trabajo> trabajos = trabajoService.listAllTrabajos();
        do {
            int index = 0;
            for (Trabajo t : trabajos ) {
                System.out.printf("%d ) %s \n", index, t.toString());
                index++;
            }
            String seleccion =  readInput("Selección: ", "Seleccione un valor");
            if (trabajos.size() > 0 && Integer.parseInt(seleccion) >= 0 && Integer.parseInt(seleccion) < trabajos.size()) {
                tarea.setTrabajo(trabajos.get(Integer.parseInt(seleccion)));
                inputInvalid = false;
            } 
        } while (inputInvalid);


        // ADD FECHA
        String fecha, hora;
        System.out.println("Introduzca la fecha de inicio para la tarea");
        fecha = readInput("Fecha: ", "Debes introducir una fecha");
        System.out.println("Introduzca la hora de inicio para la tarea");
        hora = readInput("Fecha: ", "Debes introducir una hora");
        tarea.setDateTime(LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(hora)));



        // ADD MECANICO
        inputInvalid = true;
        boolean addOther = false;
        System.out.println("Seleccione un mecanico : ");
        List<User> mecanicos = userService.listAllUsers();
        do {
            addOther = false;
            do {
                int index = 0;
                for (User u : mecanicos ) {
                    System.out.printf("%d ) %s \n", index, u.toString());
                    index++;
                }
                String seleccion =  readInput("Selección: ", "Seleccione un valor");
                while(userService.isBusy(mecanicos.get(Integer.parseInt(seleccion)), (LocalDateTime.of(LocalDate.parse(fecha), LocalTime.parse(hora))))){
                    System.out.println("El mecánico seleccionado está ocupado a esa hora, seleccione otro");
                    seleccion =  readInput("Selección: ", "Seleccione un valor");
                }

                if (mecanicos.size() > 0 && Integer.parseInt(seleccion) >= 0 && Integer.parseInt(seleccion) < mecanicos.size()) {
                    tarea.addMecanico(mecanicos.get(Integer.parseInt(seleccion)));
                    inputInvalid = false;
                } 
            } while (inputInvalid);
            String addNext =  readInput("(y)Añadir otro | (otro) No añadir otro: ", "Seleccione un valor");
            switch(addNext){
                case "y":
                    addOther = true ;
                    break;
                default:
                    addOther = false;
                    break;
            }
        } while (addOther);

        tareaService.insertTarea(tarea);

        return tarea;
    }
    public static void main(String[] args) throws SQLException, NoTareasException, UserNotFoundException {
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
                case ADD_ELEVATOR:
                    addElevator();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case ADD_TASK:
                    addTask();
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
