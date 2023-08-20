package com.example.salarydivisionsystem.Repository;

import com.example.salarydivisionsystem.Model.Entertainment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EntertainmentRepository extends JpaRepository<Entertainment,Integer> {
    Entertainment findEntertainmentById(Integer id);
}
