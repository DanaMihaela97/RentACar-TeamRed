package org.example.car;
import org.example.client.Client;
import org.example.client.ClientPackage;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name="clientPackage", columnDefinition="ENUM('STANDARD','INTERMEDIATE','LUXURY')")
    @Enumerated(EnumType.STRING)
    private ClientPackage clientPackage;
    @Column(name="carStatus", columnDefinition="ENUM('RENTED','AVAILABLE','UNAVAILABLE')")
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;
    public Car() {
    }
}



