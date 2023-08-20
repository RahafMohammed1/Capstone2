package com.example.salarydivisionsystem.Repository;

import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryAndSalaryDivisionRepository extends JpaRepository<TheSalaryAndSalaryDivision,Integer> {
    TheSalaryAndSalaryDivision findTheSalaryAndSalaryDivisionByMonth(Integer month);


}
