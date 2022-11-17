package org.example.car;

import org.example.client.Client;
import org.example.client.ClientPackage;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String model;
    @Column
    private String engineType;
    @Column
    private String gearbox;
    @Column
    private double price;



    @Enumerated(EnumType.STRING)
    private ClientPackage clientPackage;
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Car(String name, String type, String model, String engineType, String gearbox, double price, ClientPackage clientPackage, CarStatus carStatus) {
        this.name = name;
        this.type = type;
        this.model = model;
        this.engineType = engineType;
        this.gearbox = gearbox;
        this.price = price;
        this.clientPackage = clientPackage;
        this.carStatus = carStatus;
    }

    public Car() {

    }

    public void setClientNull() {
        client = null;
    }

}



