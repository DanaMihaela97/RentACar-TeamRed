package org.example;

import org.example.car.CarRepository;
import org.example.car.CarService;
import org.example.client.Client;
import org.example.client.ClientRepository;
import org.example.client.ClientService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ClientService clientService = new ClientService();
        Scanner scanner = new Scanner(System.in);
        CarService carService = new CarService();
        carService.createCar();
        //CRUD cars\\
        CarRepository carRepository = new CarRepository();
        System.out.println("Enter car id: ");
        int id = scanner.nextInt();
        System.out.println(carRepository.getCarId(id));
        carRepository.delete(id);
        //CRUD clients\\
        ClientRepository clientRepository = new ClientRepository();
        System.out.println("Enter client id: ");
        int clientId = scanner.nextInt();
        System.out.println(clientRepository.getById(clientId));
        clientRepository.delete(clientId);

        System.out.println("Choose an option: (0 - log in, 1 - register)");
        int choice = scanner.nextInt();
        Client client = null;
        if (choice == 0) {
            String email = "";
            while (true) {
                System.out.println("Enter your email: ");
                email = scanner.next();
                if (email.equals("exit")) {
                    break;
                }
                client = clientService.findClientByEmail(email);
                if (client != null) {
                    break;
                } else {
                    System.out.println("The user with this email is not registered. \nPlease, try again, or enter exit. ");
                }
            }
            if (client != null) {
                boolean hasCar = clientService.chooseCar(client);
                if (hasCar) {
                    clientService.generateReceipt(client);
                    clientService.returnCar(client);
                }
            }
        }
        if (choice == 1) {
            clientService.createAccount();
        }
   }
}





