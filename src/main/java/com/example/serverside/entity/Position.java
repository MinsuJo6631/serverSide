package com.example.serverside.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@Table(name="position")//회사의 모든 직급 정보를 저장 ex) 사원,대리,과장,차장,부장 전부다 부서에 관계없이 전부 저장
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, name = "role_name")
    private String positionName;

    @Column(nullable = false,name="rank")
    private int rank;

    @Builder
    public Position(String position_name, int rank){
        this.positionName = position_name;
        this.rank = rank;
    }
}
