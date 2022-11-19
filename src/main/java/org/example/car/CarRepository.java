package org.example.car;

import jakarta.persistence.TypedQuery;
import org.example.CrudManagement;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.car.CarService.sessionFactory;


public class CarRepository implements CrudManagement<Car> {
    @Override
    public void insert(Car item) {
        System.out.println("Inserting car...");
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(item);
            transaction.commit();
        }
    }

    @Override
    public void update(Car item) {
        System.out.println("Updating car...");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(item);
            session.getTransaction().commit();

        }
    }

    @Override
    public List<Car> getAll() {

        try (Session session = sessionFactory.openSession()) {
            JpaCriteriaQuery<Car> jpaCriteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Car.class);
            jpaCriteriaQuery.from(Car.class);
            TypedQuery<Car> typedQuery = session.createQuery(jpaCriteriaQuery);
            return typedQuery.getResultList();
        }
    }

    @Override
    public Car getById(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.get(Car.class, id);
        }
    }
    public List<Car> getByGearbox(String gearbox){

           return getAll().stream().filter(car -> car.getGearbox().equalsIgnoreCase(gearbox) &&
                   car.getCarStatus() == CarStatus.AVAILABLE).collect(Collectors.toList());
        }


    @Override
    public void delete(int item) {
        System.out.println("Deleting car...");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Car car = getById(item);
            if (car == null){
                System.out.println("Car with id " + item + " doesn't exist.");
            } else {
                session.remove(car);
                System.out.println("The car was removed.");
            }
            session.getTransaction().commit();
        }
    }
    public Car getCarId(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Car> carList = getAll();
            for (Car car : carList) {
                if (car.getId() == id) {
                    return car;
                }
            }
            session.getTransaction().commit();
        }
        return null;
    }
}




