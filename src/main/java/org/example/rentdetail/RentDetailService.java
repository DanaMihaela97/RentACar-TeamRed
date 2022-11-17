package org.example.rentdetail;

import lombok.Data;
import org.example.car.Car;
import org.example.client.Client;
import org.example.client.ClientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Data
public class RentDetailService {
    static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void insertRentPeriod(Car car, RentDetail... rentDetail) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter start data: ");
            String startData = scanner.next();
            System.out.println("Enter end data: ");
            String endData = scanner.next();
            System.out.println("Enter pick up location: ");
            String pickUpLoc = scanner.next();

            LocalDate localDate = LocalDate.parse(startData, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate localDate1 = LocalDate.parse(endData, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            Period period = Period.between(localDate, localDate1);
            double totalPrice = (car.getPrice() * period.getDays());
            float discount = 0.2f;
            double result = totalPrice * discount;
            System.out.printf("%.2f",result);


            ClientRepository clientRepository = new ClientRepository();
            List<Client> clientList = clientRepository.getAll();

            for (Client cl : clientList) {
                if (!(cl.getRentCount() >= 2) ){
                    System.out.println("You rented this car for " + period.getDays() + " days!" + "\nYour total payment is: " + totalPrice);
                    break;
                } else {
                    cl.setDiscount(discount);
                    double finalPrice = totalPrice + result;
                    System.out.println("Because you have rented more than 2 times, you will receive " + discount + " discount! \nYour total payment is: " + finalPrice);
                }
            }

                RentDetail rentDetail1 = new RentDetail(startData, endData, pickUpLoc, totalPrice);
                rentDetail1.setCar(car);
                rentDetail1.setClient(car.getClient());

//            System.out.println(rentDetail1);
                session.persist(rentDetail1);
                for (RentDetail rd : rentDetail) {
                    session.persist(rd);
                }
            session.getTransaction().commit();
            }
        }
    }
