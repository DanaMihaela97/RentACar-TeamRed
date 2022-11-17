package org.example.rentdetail;

import jakarta.persistence.*;
import lombok.Data;
import org.example.car.Car;
import org.example.client.Client;

@Entity
@Data
@Table(name="rent_detail")
public class RentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String startDate;
    @Column
    private String endDate;
    @Column
    private String pickUpLocation;
    @Column
    private double client_totalPrice;

    @OneToOne
    @JoinColumn(name="client_id")
    private Client client;

    @OneToOne
    @JoinColumn(name="car_id")
    private Car car;

    public RentDetail(String startDate, String endDate, String pickUpLocation, double client_totalPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickUpLocation = pickUpLocation;
        this.client_totalPrice=client_totalPrice;
    }
    public RentDetail(){}
}


