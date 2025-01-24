package com.example.serverside.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@Table(name="department")//회사의 모든 부서 정보를 저장


public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(nullable = false, name = "department_name")
    private String departmentName;

//    @OneToMany(mappedBy = "department")
//    private List<DEPARTMENT_POSITION> departmentPositions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "department")
//    private List<USER_ACTIVE> userActives = new ArrayList<>();

    @Builder
    public Department(String name) {
        this.departmentName = name;
    }
}
