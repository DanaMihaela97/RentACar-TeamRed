package org.example.client;

import jakarta.persistence.*;
import lombok.Data;
import org.example.car.Car;

@Entity
@Data

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private long licenseId;
    @Column(nullable = false)
    private String name;
    @Column
    private long phoneNumber;
    @Column
    private String email;
    @Column
    private int rentCount;

    @Column (nullable = false)
    private double totalPrice;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    //    @Column
//    private double finalPrice;
//    @OneToMany()
////   @JoinColumn(name="client_id")
//    private List<Car> cars;
    public Client(String name, long licenseId, long phoneNumber, String email ) {
        this.name = name;
        this.licenseId = licenseId;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }
    public Client() {
    }


}
//scos one to one\\\\\\\\\\\