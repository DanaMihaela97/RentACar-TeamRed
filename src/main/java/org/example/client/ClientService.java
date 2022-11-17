package org.example.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class ClientService {
    public static SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void createAccount(Client... clients) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
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

            session.persist(client);
            for (Client cl : clients) {
                session.persist(cl);
            }
            transaction.commit();
        }
    }
}
