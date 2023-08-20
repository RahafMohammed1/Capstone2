package com.example.salarydivisionsystem.Repository;

import com.example.salarydivisionsystem.Model.FinancialCommitmentForOneMonth;
import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FinancialCommitmentForOneMonthRepository extends JpaRepository<FinancialCommitmentForOneMonth,Integer> {
    List<FinancialCommitmentForOneMonth> findAllByMonthNumber (Integer month);
    FinancialCommitmentForOneMonth findFinancialCommitmentForOneMonthById (Integer id);
}
