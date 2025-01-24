package com.example.serverside.jparepo;

import com.example.admin.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface departmentRepo extends JpaRepository<Department, Long> {

    Department findByDepartmentName(String name);

    void deleteDepartmentByDepartmentName(String departmentName);

}
