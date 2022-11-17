package org.example.car;

import org.example.client.Client;
import org.example.client.ClientPackage;
import org.example.client.ClientRepository;
import org.example.rentdetail.RentDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.example.rentdetail.RentDetailService.insertRentPeriod;

public class CarService {
    public static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void chooseGearBox() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Car> resultList = new ArrayList<>();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your preferences between automatic and manual: ");
            String answer = scanner.next();
            CarRepository carManagement = new CarRepository();
            List<Car> resultList2 = new ArrayList<>();
            List<Car> carsList = carManagement.getAll();

            resultList2 = carsList.stream().filter(car -> car.getCarStatus() == CarStatus.AVAILABLE).collect(Collectors.toList());
            resultList = carsList.stream().filter(car -> car.getGearbox().equalsIgnoreCase(answer) && car.getCarStatus() == CarStatus.AVAILABLE).collect(Collectors.toList());
            if (!resultList.isEmpty()) {
                System.out.println(resultList);
            } else {
                System.out.println("We don't have cars with this specification or they are unavailable/broken.");

                System.out.println(resultList2);
            }
        }
    }

    public static void carCondition(RentDetail... rentDetail) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
//                getClientCar();
            Car result;
            Scanner scanner = new Scanner(System.in);
            RentDetail rd = new RentDetail();
            Client cl = new Client();
            System.out.println("The id car you want to return: ");
            int reply = scanner.nextInt();
            CarRepository carManagement = new CarRepository();

            List<Car> allCars = carManagement.getAll();

            result = allCars.stream().filter(c -> c.getId() == reply).toList().get(0);
            System.out.println("What is the car's condition? BROKEN/GOOD");
            String reply2 = scanner.next();

            if (result != null && result.getClientPackage() == ClientPackage.STANDARD && reply2.equalsIgnoreCase("BROKEN")) {
                System.out.println("You have extra charge: 100");
                result.setCarStatus(CarStatus.UNAVAILABLE);
                rd.setTax(rd.getTax() + 100);

            } else if (result != null && result.getClientPackage() == ClientPackage.INTERMEDIATE && reply2.equalsIgnoreCase("BROKEN")) {
                System.out.println("You have extra charge: 300");
                result.setCarStatus(CarStatus.UNAVAILABLE);
                rd.setTax(rd.getTax() + 300);

            } else if (result != null && result.getClientPackage() == ClientPackage.LUXURY && reply2.equalsIgnoreCase("BROKEN")) {
                System.out.println("You have extra charge: 700");
                result.setCarStatus(CarStatus.UNAVAILABLE);
                rd.setTax(rd.getTax() + 700);

            } else if (result != null && reply2.equalsIgnoreCase("GOOD")) {
                System.out.println("We are glad that you returned it in good condition!! :)");
                result.setCarStatus(CarStatus.AVAILABLE);
            }
            session.merge(rd);
            result.setClientNull();
            session.merge(result);
//            for (RentDetail rentD:rentDetail){
//                session.persist(rentD);
//            }
//            session.merge(car);
            session.getTransaction().commit();
        }
    }


    public static void updateCarStatus(Client client) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            chooseGearBox();
            Car result;
            CarRepository carManagement = new CarRepository();
            List<Car> carsCars = carManagement.getAll();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the car id you want to rent: ");
            int reply = scanner.nextInt();

            result = carsCars.stream().filter(car -> car.getId() == reply).toList().get(0);
            if (result != null && result.getCarStatus() == CarStatus.AVAILABLE) {
                System.out.println(result);
            } else {
                System.out.println("The car with this id is unavailable or broken.");
                return;
            }
            result.setCarStatus(CarStatus.RENTED);
            client.setRentCount(client.getRentCount() + 1);
            session.merge(client);
            session.getTransaction().commit();

            result.setClient(client);
            carManagement.update(result);
            insertRentPeriod(result);
        }
    }

    public static void getClientCar() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your email: ");
            String clientEmail = scanner.next();

            CarRepository carRepository = new CarRepository();
            List<Car> listCar = carRepository.getAll();
            Car car;
            car = listCar.stream().filter(c -> c.getClient().getEmail().equalsIgnoreCase(clientEmail)).toList().get(0);
            System.out.println(car);
            transaction.commit();
        }
    }
}


