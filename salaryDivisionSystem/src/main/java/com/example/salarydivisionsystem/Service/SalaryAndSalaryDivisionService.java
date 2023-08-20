package com.example.salarydivisionsystem.Service;

import com.example.salarydivisionsystem.ApiResponce.ApiException;
import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import com.example.salarydivisionsystem.Repository.SalaryAndSalaryDivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaryAndSalaryDivisionService {
private final SalaryAndSalaryDivisionRepository salaryAndSalaryDivisionRepository;

    public List<TheSalaryAndSalaryDivision> getAllSummeryOfSalaryDivisionsOfAllMonths(){
    return salaryAndSalaryDivisionRepository.findAll();
    }
    public void addSalary(TheSalaryAndSalaryDivision salary){
        TheSalaryAndSalaryDivision salary1=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(salary.getMonth());
        if(salary1!=null)
        {
            throw new ApiException("the Salary is already exist for this month("+salary.getMonth()+")");
        }
        salary.setTheAmountOfMoneyForSaving(calculateSavingMoney(salary.getSalaryAmount()));
        salaryAndSalaryDivisionRepository.save(salary);
        salary.setTheAmountOfExcessMoney(salary.getSalaryAmount()-salary.getTheAmountOfMoneyForSaving());
        salaryAndSalaryDivisionRepository.save(salary);
    }

    public void updateSalary(Integer Month, TheSalaryAndSalaryDivision salary)
    {
        TheSalaryAndSalaryDivision salary1=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(salary.getMonth());
        if(salary1==null)
        {
            throw new ApiException("the Salary not found");
        }

        salary.setTheAmountOfMoneyForSaving(calculateSavingMoney(salary.getSalaryAmount()));
        salaryAndSalaryDivisionRepository.save(salary1);
        salary.setSalaryAmount(salary.getSalaryAmount()-salary.getTheAmountOfMoneyForSaving());
        salaryAndSalaryDivisionRepository.save(salary1);
        salary1.setMonth(salary.getMonth());
        salary1.setTheAmountOfExcessMoney(salary.getTheAmountOfExcessMoney());
        salary1.setTheAmountOfMoneyForFinancialCommitments(salary.getTheAmountOfMoneyForFinancialCommitments());
        salary1.setTheAmountOfMoneyForEntertainment(salary.getTheAmountOfMoneyForEntertainment());
        salaryAndSalaryDivisionRepository.save(salary1);
    }

//the saving money = 20% of Salary
    public double calculateSavingMoney(double Salary)
    {
        double saving =(0.2*Salary);
        return saving;
    }
    public void deleteSalary(Integer month)
    {
        TheSalaryAndSalaryDivision salary1=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(month);
        if(salary1==null)
        {
            throw new ApiException("the Salary not found");
        }
        salaryAndSalaryDivisionRepository.delete(salary1);
    }
    public TheSalaryAndSalaryDivision getSummeryOfSalaryDivisionsForSpecificMonth(Integer month)
    {
        TheSalaryAndSalaryDivision salary1=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(month);
        if(salary1==null)
        {
            throw new ApiException("the Salary Division not found for this month");
        }
        return salary1;
    }

}
