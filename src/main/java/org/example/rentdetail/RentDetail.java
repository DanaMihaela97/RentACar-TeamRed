package org.example.rentdetail;

import jakarta.persistence.*;
import lombok.Data;
import org.example.car.Car;
import org.example.client.Client;
import java.time.LocalDate;
@Entity
@Data
@Table(name="rent_detail")
public class RentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private String pickUpLocation;
    @Column
    private double client_totalPrice;
    @Column
    private double tax;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_id")
    private Car car;


    public RentDetail(LocalDate startDate, LocalDate endDate, String pickUpLocation, double client_totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickUpLocation = pickUpLocation;
        this.client_totalPrice=client_totalPrice;

    }
    public RentDetail(){}
}


