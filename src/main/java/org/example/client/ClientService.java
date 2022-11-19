package org.example.client;

import org.example.car.Car;
import org.example.car.CarService;
import org.example.rentdetail.RentDetail;
import org.example.rentdetail.RentDetailService;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class ClientService {
    public ClientService(){
        clientRepository = new ClientRepository();
        carService = new CarService();
        rentDetailService = new RentDetailService();
    }
    ClientRepository clientRepository;
    CarService carService;
    RentDetailService rentDetailService;
    Scanner scanner=new Scanner(System.in);

    public static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();
    public void createAccount(Client... clients) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Create account. \nEnter your name: ");
            String name = scanner.next();
            System.out.println("Enter your license id: ");
            long licenseId = scanner.nextLong();
            System.out.println("Enter your phone number: ");
            long phoneNo = scanner.nextLong();
            System.out.println("Enter your email: ");
            String email = scanner.next();

            Client client = new Client(name, licenseId, phoneNo, email);
            System.out.println(client + "\nThank you for your registration! :)");

            clientRepository.insert(client);
    }
    public boolean chooseCar(Client client){
        carService.setClient(client);
        boolean result = carService.chooseCar();
        if(result){
            client.setRentCount(client.getRentCount()+1);
            carService.setClient(client); // updatam clientul care utilizeaza serviciul
            clientRepository.update(client);
            return true;
        }
        return false;
    }
    public void generateReceipt(Client client){
        rentDetailService.insertRentPeriod(client);
        clientRepository.update(client);
    }
    public Client logIn(String email){
        return clientRepository.getByEmail(email);
    }
    public Client logInId(int id){
        return clientRepository.getById(id);
    }
    public void returnCar(Client client){
        RentDetail rentDetail = rentDetailService.findByClientId(client);
        rentDetailService.setRentDetail(rentDetail);
        Car car = carService.getClientCar(client.getEmail());
        rentDetailService.returnCar(client, car);
        carService.returnCar(car);
        clientRepository.update(client);
    }
    public Client findClientByEmail(String email){
        return clientRepository.getByEmail(email);

    }
}
