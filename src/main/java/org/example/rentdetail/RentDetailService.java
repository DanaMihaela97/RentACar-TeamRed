package org.example.rentdetail;

import lombok.Data;
import org.example.car.Car;
import org.example.car.CarRepository;
import org.example.car.CarService;
import org.example.car.CarStatus;
import org.example.client.Client;
import org.example.client.ClientPackage;
import org.example.client.ClientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RentDetailService {
    CarRepository carRepository;
    RentDetail rentDetail;
    RentDetailRepository rentDetailRepository;
    Scanner scanner=new Scanner(System.in);
    public RentDetailService(){
        rentDetailRepository = new RentDetailRepository();
        carRepository = new CarRepository();
    }

    public  void insertRentPeriod(Client client) {
        RentDetail rentDetail = new RentDetail();
        Car car = client.getCar();
        System.out.println("Enter start date: ");
        rentDetail.setStartDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("Enter end date: ");
        rentDetail.setEndDate(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("Enter pick up location: ");
        rentDetail.setPickUpLocation(scanner.nextLine());

        Period period = Period.between(rentDetail.getStartDate(), rentDetail.getEndDate());
        double totalPrice = (car.getPrice() * period.getDays());
        double discount = totalPrice * 0.2f;

        if (client.getRentCount() >= 2){
            totalPrice = totalPrice - discount;
            System.out.printf("Because you have rented more than 2 times, you will receive 20 percent discount! \nYour total payment is: " +  "%.2f\n", totalPrice);
        }
        else {
            System.out.println("\nYou rented this car for " + period.getDays() + " days!" + "\nYour total payment is: " + totalPrice);
        }
        rentDetail.setCar(car);
        rentDetail.setClient(client);
        client.setTotalPrice(totalPrice);
        rentDetail.setClient_totalPrice(totalPrice);
        rentDetailRepository.update(rentDetail);
    }
    public void returnCar(Client client, Car car){
        System.out.println("What is the car's condition: good/broken");
        String condition = scanner.next();
        if (condition.equalsIgnoreCase("good")){
            car.setCarStatus(CarStatus.AVAILABLE);
            car.setClient(null);
            client.setCar(null);
            System.out.println("We are glad that you returned it in good condition!! :)");
        } else {
            car.setCarStatus(CarStatus.UNAVAILABLE);
            car.setClient(null);
            client.setCar(null);
            if (car.getClientPackage() == ClientPackage.STANDARD){
                rentDetail.setTax(100);
                System.out.println();
            }
            if (car.getClientPackage() == ClientPackage.INTERMEDIATE){
                rentDetail.setTax(200);
                System.out.println();
            }
            if (car.getClientPackage() == ClientPackage.LUXURY){
                rentDetail.setTax(300);
                System.out.println();
            }
        }

        rentDetailRepository.update(rentDetail);
    }

    public RentDetail findByClientId(Client client){
        List<RentDetail> rentDetailList = rentDetailRepository.getAll();
        for (RentDetail rentDetail: rentDetailList) {
            if(rentDetail.getClient().getId() == client.getId())
            {
                return rentDetail;
            }
        }
        return null;
    }

    public void setRentDetail(RentDetail rentDetail){
        this.rentDetail = rentDetail;
    }
}
