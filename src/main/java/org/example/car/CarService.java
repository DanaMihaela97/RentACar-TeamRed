package org.example.car;

import org.example.client.Client;
import org.example.client.ClientPackage;
import org.example.rentdetail.RentDetail;
import org.example.rentdetail.RentDetailRepository;
import org.example.returned_cars.ReturnedCars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class CarService {
    Client client;
    CarRepository carRepository;
    Scanner scanner = new Scanner(System.in);
    public CarService(){
        carRepository = new CarRepository();
    }
    public static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public void createCar(){
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
        //ClientPackage clientPackage = ClientPackage.valueOf(scanner.next().toUpperCase());
/*      car.setName(name);
        car.setType(type);
        car.setModel(model);
        car.setEngineType(engineType);
        car.setGearbox(gearbox);
        car.setPrice(price);
        car.setClientPackage(clientPackage);
        car.setCarStatus(carStatus);*/
        carRepository.insert(car);

    }

    public List<Car> chooseGearBox() {
        System.out.println("Enter your preferences between automatic and manual: ");
        String gearbox = scanner.nextLine();
        List<Car> resultList = carRepository.getByGearbox(gearbox);
        if(resultList.isEmpty()) {
            System.out.println("No cars with chosen gearbox.");
            if (gearbox.equalsIgnoreCase("automatic")) {
                resultList = carRepository.getByGearbox("manual");
                System.out.println("Cars with manual gearbox:\n" + resultList);
            } else {
                resultList = carRepository.getByGearbox("automatic");
                System.out.println("Cars with automatic gearbox:\n" + resultList);
            }
        }else{
            System.out.println("Cars with selected gearbox:\n" + resultList);
        }
        return resultList;
        /*try (Session session = sessionFactory.openSession()) {
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
                System.out.println("We don't have cars with this specification or they are unavailable/broken. \nI will show you the list of available cars: " +"\n" + resultList2);
            }
        }
         */
    }

    public static void carCondition(RentDetail... rentDetail) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
//                getClientCar();
            Car result;
            Scanner scanner = new Scanner(System.in);
            RentDetail rd = new RentDetail();
            RentDetailRepository rentDetailRepository = new RentDetailRepository();


            Client cl = new Client();
            System.out.println("The id car you want to return: ");
            int reply = scanner.nextInt();
            CarRepository carManagement = new CarRepository();

            List<Car> allCars = carManagement.getAll();

            result = allCars.stream().filter(c -> c.getId() == reply).toList().get(0);
            System.out.println("What is the car's condition? BROKEN/GOOD");
            String reply2 = scanner.next();
            ReturnedCars returnedCars= new ReturnedCars();

            if (result != null && result.getClientPackage() == ClientPackage.STANDARD && reply2.equalsIgnoreCase("BROKEN")) {
                System.out.println("You have extra charge: 100");
                result.setCarStatus(CarStatus.UNAVAILABLE);
                returnedCars.setTax(100);
                returnedCars.setCar(returnedCars.getCar());
                returnedCars.setClient(returnedCars.getClient());
               // rd.setTax(100);
//                rd.setClient(rd.getClient());
//                rd.setCar(rd.getCar());

            } else if (result != null && result.getClientPackage() == ClientPackage.INTERMEDIATE && reply2.equalsIgnoreCase("BROKEN")) {
                System.out.println("You have extra charge: 300");
                result.setCarStatus(CarStatus.UNAVAILABLE);
                returnedCars.setTax(300);
                returnedCars.setCar(returnedCars.getCar());
                returnedCars.setClient(returnedCars.getClient());
//                rd.setTax(300);
//                rd.setClient(rd.getClient());
//                rd.setCar(rd.getCar());

            } else if (result != null && result.getClientPackage() == ClientPackage.LUXURY && reply2.equalsIgnoreCase("BROKEN")) {
                System.out.println("You have extra charge: 700");
                result.setCarStatus(CarStatus.UNAVAILABLE);
                returnedCars.setTax(700);
                returnedCars.setCar(returnedCars.getCar());
                returnedCars.setClient(returnedCars.getClient());
//                rd.setTax(700);
//                rd.setClient(rd.getClient());
//                rd.setCar(rd.getCar());

            } else if (result != null && reply2.equalsIgnoreCase("GOOD")) {
                System.out.println("We are glad that you returned it in good condition!! :)");
                result.setCarStatus(CarStatus.AVAILABLE);
            }
//            session.persist(rd);
            session.persist(returnedCars);
            result.setClientNull();
            session.merge(result);
            for (RentDetail rentD:rentDetail){
                session.persist(rentD);
            }
//            session.merge(car);
            session.getTransaction().commit();
        }
    }



    public boolean chooseCar() {
        List<Car> result = chooseGearBox();
        if(result.isEmpty()){
            System.out.println("No available cars!");
            return false;
        }
        System.out.println("Enter the car id you want to rent: ");
        Car car = carRepository.getById(scanner.nextInt());

        while(!result.contains(car)) {
            System.out.println("You've chosen a wrong id, choose from the list!");
            car = carRepository.getById(scanner.nextInt());
        }

        car.setCarStatus(CarStatus.RENTED);
        car.setClient(client);
        client.setCar(car);
        carRepository.update(car);

        return true;
           /* Car result;
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
            insertRentPeriod(result);*/

    }

    public  Car getClientCar(String email) {
            CarRepository carRepository = new CarRepository();
            List<Car> listCar = carRepository.getAll();
            Car car;
            car = listCar.stream().filter(c -> c.getClient()!=null && c.getClient().getEmail().equalsIgnoreCase(email)).toList().get(0);
            return car;
    }

    public void returnCar(Car car){
        carRepository.update(car);
    }
    public void setClient(Client client){
        this.client = client;
    }

}


