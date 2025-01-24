package com.example.serverside.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, name = "name")
    private String name;

    @Column( nullable = false, name = "HID")
    private String HID;

    @Column( nullable = false, name = "pw")
    private String pw;

    @Column( nullable = false, name = "phone")
    private String phone;

    @Column( nullable = false, name = "email")
    private String email;

    @Builder
    public User(String name, String HID, String pw, String phone, String email){
        this.name = name;
        this.HID = HID;
        this.pw = pw;
        this.phone = phone;
        this.email = email;
    }


}
