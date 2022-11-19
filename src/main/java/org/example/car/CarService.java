package org.example.car;

import org.example.client.Client;
import org.example.client.ClientPackage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarService {
    Client client;
    CarRepository carRepository;
    Scanner scanner = new Scanner(System.in);

    public CarService() {
        carRepository = new CarRepository();
    }

    public static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public void createCar() {
        Car car = new Car();
        System.out.println("Create car. \nEnter car name: ");
        car.setName(scanner.nextLine());
        //String name = scanner.next();
        System.out.println("Enter car type: ");
        car.setType(scanner.nextLine());
        //String type = scanner.next();
        System.out.println("Enter car model: ");
        car.setModel(scanner.nextLine());
        //String model = scanner.next();
        System.out.println("Enter engine type: ");
        car.setEngineType(scanner.nextLine());
        //String engineType = scanner.next();
        System.out.println("Enter gearbox: ");
        car.setGearbox(scanner.nextLine());
        //String gearbox = scanner.next();
        System.out.println("Enter price: ");
        car.setPrice(scanner.nextDouble());
        //double price = scanner.nextDouble();
        System.out.println("Enter package: (0 - STANDARD, 1 - INTERMEDIATE,  2 - LUXURY)");
        car.setClientPackage(ClientPackage.values()[scanner.nextInt()]);
        System.out.println("Enter car status: (0 - RENTED, 1 - AVAILABLE, 2 - UNAVAILABLE");
        car.setCarStatus(CarStatus.values()[scanner.nextInt()]);
        carRepository.insert(car);
        car.setCarStatus(CarStatus.AVAILABLE);
    }
    public List<Car> chooseGearBox() {
        System.out.println("Enter your preferences between automatic and manual: ");
        String gearbox = scanner.nextLine();
        List<Car> resultList = carRepository.getByGearbox(gearbox);
        if (resultList.isEmpty()) {
            System.out.println("No cars with chosen gearbox.");
            if (gearbox.equalsIgnoreCase("automatic")) {
                resultList = carRepository.getByGearbox("manual");
                System.out.println("Cars with manual gearbox:\n" + resultList);
            } else {
                resultList = carRepository.getByGearbox("automatic");
                System.out.println("Cars with automatic gearbox:\n" + resultList);
            }
        } else {
            System.out.println("Cars with selected gearbox:\n" + resultList);
        }
        return resultList;
    }
    public boolean chooseCar() {
        List<Car>result = new ArrayList<>();
        int count = 0;
        do{
            result = chooseGearBox();
            if (result.isEmpty()) {
                System.out.println("No available cars!");
                count++;

            }
            if (count == 2){
                return false;
            }
        } while (result.isEmpty());

        System.out.println("Enter the car id you want to rent: ");
        Car car = carRepository.getById(scanner.nextInt());

        while (!result.contains(car)) {
            System.out.println("You've chosen a wrong id, choose from the list!");
            car = carRepository.getById(scanner.nextInt());
        }
        car.setCarStatus(CarStatus.RENTED);
        car.setClient(client);
        client.setCar(car);
        carRepository.update(car);
        return true;
    }
    public Car getClientCar(String email) {
        CarRepository carRepository = new CarRepository();
        List<Car> listCar = carRepository.getAll();
        Car car;
        car = listCar.stream().filter(c -> c.getClient() != null && c.getClient().getEmail().equalsIgnoreCase(email)).toList().get(0);
        return car;
    }
    public void returnCar(Car car) {
        carRepository.update(car);
    }
    public void setClient(Client client) {
        this.client = client;
    }
}




