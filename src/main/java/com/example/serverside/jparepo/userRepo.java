package com.example.serverside.jparepo;

import com.example.admin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<User,Long> {

    User findByName(String name);

    boolean existsByHID(String HID);

    User findByHID(String HID);

    void deleteByHID(String HID);

}
