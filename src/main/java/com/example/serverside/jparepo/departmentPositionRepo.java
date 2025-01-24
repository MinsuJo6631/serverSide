package com.example.serverside.jparepo;

import com.example.admin.Entity.Department_Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface departmentPositionRepo extends JpaRepository<Department_Position,Long> {
}
