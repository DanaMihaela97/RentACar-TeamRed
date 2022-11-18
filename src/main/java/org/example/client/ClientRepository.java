package org.example.client;

import jakarta.persistence.TypedQuery;
import org.example.CrudManagement;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import java.util.List;

import static org.example.car.CarService.sessionFactory;

public class ClientRepository implements CrudManagement<Client> {
    @Override
    public void insert(Client item) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
        }

    }

    @Override
    public void update(Client item) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(item);
            session.getTransaction().commit();
        }

    }

    @Override
    public List<Client> getAll() {
        try (Session session = sessionFactory.openSession()) {
            JpaCriteriaQuery<Client> jpaCriteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Client.class);
            jpaCriteriaQuery.from(Client.class);
            TypedQuery<Client> typedQuery = session.createQuery(jpaCriteriaQuery);
            return typedQuery.getResultList();
        }
    }

    @Override
    public Client getById(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.get(Client.class, id);
        }
    }

    @Override
    public void delete(int item) {
        System.out.println("Deleting client...");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Client client = getById(item);
            if (client == null){
                System.out.println("Client with id " + item + " doesn't exist.");
            } else {
                session.remove(client);
                System.out.println("The client was removed!");
            }
            session.getTransaction().commit();
        }
    }
    public Client getByEmail(String email) {
            List<Client> clientList = getAll();
            for (Client client : clientList) {
                if (client.getEmail().equals(email)) {
                    return client;
                }
            }
            return new Client();
        }
    }

