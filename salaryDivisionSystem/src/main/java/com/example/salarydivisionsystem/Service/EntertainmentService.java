package com.example.salarydivisionsystem.Service;

import com.example.salarydivisionsystem.ApiResponce.ApiException;
import com.example.salarydivisionsystem.Model.Entertainment;
import com.example.salarydivisionsystem.Model.FinancialCommitmentForOneMonth;
import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import com.example.salarydivisionsystem.Repository.EntertainmentRepository;
import com.example.salarydivisionsystem.Repository.SalaryAndSalaryDivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntertainmentService {
    final private EntertainmentRepository entertainmentRepository;
    final private SalaryAndSalaryDivisionRepository salaryAndSalaryDivisionRepository;

    public List<Entertainment> getAll() {
        return entertainmentRepository.findAll();
    }
    public void add (Entertainment entertainment)
    {
        TheSalaryAndSalaryDivision salaryAndSalaryDivision=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(entertainment.getMonthNumber());
        if(salaryAndSalaryDivision==null){
            throw new ApiException("Error,add The salary for this month first");
        }
        double salary=salaryAndSalaryDivision.getTheAmountOfExcessMoney();
        Integer amountOfMoneyOfEntertainment=entertainment.getAmountOfMoney();
        if(amountOfMoneyOfEntertainment>salary) {
            throw new ApiException("Error,your salary not enough for adding this entertainment type");
        }
        salaryAndSalaryDivision.setTheAmountOfMoneyForEntertainment(salaryAndSalaryDivision.getTheAmountOfMoneyForEntertainment()+amountOfMoneyOfEntertainment);
      entertainmentRepository.save(entertainment);
    }
    public void update(Integer id, Entertainment entertainment) {
        Entertainment entertainment1 = entertainmentRepository.findEntertainmentById(id);
        if (entertainment1 == null) {
            throw new ApiException("Error,The entertainment not found");
        }
        //delete the Total Value of old entertainment from TheAmountOfMoneyForEntertainment value
        TheSalaryAndSalaryDivision salaryAndSalaryDivision = salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(entertainment1.getMonthNumber());
        salaryAndSalaryDivision.setTheAmountOfMoneyForEntertainment(salaryAndSalaryDivision.getTheAmountOfMoneyForEntertainment() - entertainment1.getAmountOfMoney());
        //add the Total Value of new entertainment to TheAmountOfMoneyForEntertainment value
        salaryAndSalaryDivision.setTheAmountOfMoneyForEntertainment(salaryAndSalaryDivision.getTheAmountOfMoneyForEntertainment() + entertainment.getAmountOfMoney());
        //update entertainment
        entertainment1.setMonthNumber(entertainment.getMonthNumber());
        entertainment1.setAmountOfMoney(entertainment.getAmountOfMoney());
        entertainment1.setTitle(entertainment.getTitle());
        entertainmentRepository.save(entertainment1);
    }
    public void delete (Integer id)
    {
        Entertainment entertainment1 = entertainmentRepository.findEntertainmentById(id);
        if (entertainment1 == null) {
            throw new ApiException("Error,The entertainment not found");
        }
        //delete the Total Value of entertainment from TheAmountOfMoneyForEntertainment value
        TheSalaryAndSalaryDivision salaryAndSalaryDivision = salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(entertainment1.getMonthNumber());
        salaryAndSalaryDivision.setTheAmountOfMoneyForEntertainment(salaryAndSalaryDivision.getTheAmountOfMoneyForEntertainment() - entertainment1.getAmountOfMoney());

        entertainmentRepository.delete(entertainment1);

    }

    public void WithdrawMoneyFromOneOfTheEntertainmentDepartments(Integer id,Integer amount)
    {
        Entertainment entertainment1 = entertainmentRepository.findEntertainmentById(id);
        if (entertainment1 == null) {
            throw new ApiException("Error,The entertainment not found");
        }
        if(entertainment1.getAmountOfMoney()<amount)
        {
            throw new ApiException("Error,you have not enough money to pay "+entertainment1.getTitle()+");");
        }
        entertainment1.setAmountOfMoney(entertainment1.getAmountOfMoney()-amount);
        TheSalaryAndSalaryDivision salaryAndSalaryDivision=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(entertainment1.getMonthNumber());
        double c=salaryAndSalaryDivision.getTheAmountOfExcessMoney()-amount;
        double a=salaryAndSalaryDivision.getTheAmountOfMoneyForEntertainment()-amount;
        salaryAndSalaryDivision.setTheAmountOfMoneyForEntertainment(a);
        salaryAndSalaryDivision.setTheAmountOfExcessMoney(c);
        salaryAndSalaryDivisionRepository.save(salaryAndSalaryDivision);
        entertainmentRepository.save(entertainment1);
    }

}
