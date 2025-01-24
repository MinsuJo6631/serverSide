package com.example.serverside.jparepo;

import com.example.admin.Entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface positionRepo extends JpaRepository<Position,Long> {

    Position findByPositionName(String name);


}
