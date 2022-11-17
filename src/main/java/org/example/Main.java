package org.example;

import org.example.car.Car;
import org.example.car.CarRepository;
import org.example.car.CarStatus;
import org.example.client.Client;
import org.example.client.ClientPackage;
import org.example.client.ClientRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

import static org.example.car.CarService.carCondition;
import static org.example.car.CarService.updateCarStatus;
import static org.example.client.ClientService.createAccount;

public class Main {
    public static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void main(String[] args) {
//        Car car1 = new Car("Volvo", "HatchBack", "V40", "Petrol", "Manual", 100, ClientPackage.INTERMEDIATE, CarStatus.AVAILABLE);
//        Car car2 = new Car("Lamborghini", "Super Car", "Diablo", "Petrol", "Automatic", 500, ClientPackage.LUXURY, CarStatus.AVAILABLE);
//        Car car3 = new Car("Mercedes", "Limousine", "E Class", "Petrol", "Automatic", 350, ClientPackage.LUXURY, CarStatus.RENTED);
//        Car car4 = new Car("Dacia", "Sedan", "1310", "Gasoline", "Manual", 10, ClientPackage.STANDARD, CarStatus.UNAVAILABLE);
//        Car car5 = new Car("Audi", "Sedan", "A4", "Petrol", "Automatic", 300, ClientPackage.INTERMEDIATE, CarStatus.RENTED);
//        Car car6 = new Car("Ferrari", "Super Car", "488", "Petrol", "Automatic", 550, ClientPackage.LUXURY, CarStatus.AVAILABLE);
//        Car car7 = new Car("Jaguar", "Limousine", "XJ", "Petrol", "Automatic", 500, ClientPackage.LUXURY, CarStatus.AVAILABLE);
//        List<Car> cars = List.of(car1, car2, car3, car4, car5, car6, car7);
//        CarRepository carRepository = new CarRepository();
//        for (Car car : cars) {
//            carRepository.update(car);
//            createAccount();
//        Scanner scanner = new Scanner(System.in);
//       System.out.println("What is your client ID?: ");
//        int clientId = scanner.nextInt();
//        ClientRepository clientManagement = new ClientRepository();
//        Client client = new Client();
//        client = clientManagement.getById(clientId);
//        updateCarStatus(client);
        carCondition();

        }

}



