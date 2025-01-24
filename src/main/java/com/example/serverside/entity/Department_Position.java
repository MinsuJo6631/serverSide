package com.example.serverside.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@Table(name="department_position")//각 부서에서 사용가능한 직급을 매핑 개발팀 은 모든 직급 가능, 인사팀은 부장직급 없음 등을 반영
public class Department_Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name="position_id")
    private Position position;


    @Column( nullable = false, name = "is_active")
    private boolean is_active;

    @Builder
    public Department_Position(Department department, Position position, boolean is_active){
        this.department = department;
        this.position = position;
        this.is_active = is_active;
    }

}
