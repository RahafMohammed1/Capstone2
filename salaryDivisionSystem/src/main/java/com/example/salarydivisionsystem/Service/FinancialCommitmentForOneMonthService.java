package com.example.salarydivisionsystem.Service;

import com.example.salarydivisionsystem.ApiResponce.ApiException;
import com.example.salarydivisionsystem.Model.FinancialCommitmentForOneMonth;
import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import com.example.salarydivisionsystem.Repository.FinancialCommitmentForOneMonthRepository;
import com.example.salarydivisionsystem.Repository.SalaryAndSalaryDivisionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinancialCommitmentForOneMonthService {
    final private FinancialCommitmentForOneMonthRepository financialCommitmentForOneMonthRepository;
    final private SalaryAndSalaryDivisionRepository salaryAndSalaryDivisionRepository;

    public List<FinancialCommitmentForOneMonth> getAll()
    {
        return financialCommitmentForOneMonthRepository.findAll();
    }

    public void add (FinancialCommitmentForOneMonth Commitment)
    {
       TheSalaryAndSalaryDivision salaryAndSalaryDivision=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(Commitment.getMonthNumber());
       if(salaryAndSalaryDivision==null){
           throw new ApiException("Error,add The salary for this month first");
       }
       double salary=salaryAndSalaryDivision.getTheAmountOfExcessMoney();
       Integer amountOfMoneyOfCommitment=Commitment.getTotalValue();
       if(amountOfMoneyOfCommitment>salary) {
           throw new ApiException("Error,your salary not enough for adding this commitment");
       }
       salaryAndSalaryDivision.setTheAmountOfMoneyForFinancialCommitments(salaryAndSalaryDivision.getTheAmountOfMoneyForFinancialCommitments()+amountOfMoneyOfCommitment);
        if (Commitment.getStatus().equalsIgnoreCase("complete")) {
            salaryAndSalaryDivision.setTheAmountOfExcessMoney(salaryAndSalaryDivision.getTheAmountOfExcessMoney() - amountOfMoneyOfCommitment);
        }
       financialCommitmentForOneMonthRepository.save(Commitment);
    }

    public void update(Integer id, FinancialCommitmentForOneMonth financialCommitment) {
        FinancialCommitmentForOneMonth commitment=financialCommitmentForOneMonthRepository.findFinancialCommitmentForOneMonthById(id);
        if (commitment == null) {
            throw new ApiException("Error,The Commitment not found");
        }
        //delete the Total Value of old Commitment from TheAmountOfMoneyForFinancialCommitments value
        TheSalaryAndSalaryDivision salaryAndSalaryDivision = salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(commitment.getMonthNumber());
        salaryAndSalaryDivision.setTheAmountOfMoneyForFinancialCommitments(salaryAndSalaryDivision.getTheAmountOfMoneyForFinancialCommitments() - commitment.getTotalValue());
        //add the Total Value of new Commitment to TheAmountOfMoneyForFinancialCommitments value
        salaryAndSalaryDivision.setTheAmountOfMoneyForFinancialCommitments(salaryAndSalaryDivision.getTheAmountOfMoneyForFinancialCommitments()+financialCommitment.getTotalValue());
        if (commitment.getStatus().equalsIgnoreCase("complete")) {
            //return the Value taken by old Commitment to the TheAmountOfExcessMoney
            salaryAndSalaryDivision.setTheAmountOfExcessMoney(salaryAndSalaryDivision.getTheAmountOfExcessMoney() + commitment.getTotalValue());
        }
        //update Commitments
        commitment.setTitleOfCommitment(financialCommitment.getTitleOfCommitment());
        commitment.setMonthNumber(financialCommitment.getMonthNumber());
        commitment.setStatus(financialCommitment.getStatus());
        commitment.setTotalValue(financialCommitment.getTotalValue());
        financialCommitmentForOneMonthRepository.save(commitment);
    }

    public void delete (Integer id)
    {
        FinancialCommitmentForOneMonth commitment=financialCommitmentForOneMonthRepository.findFinancialCommitmentForOneMonthById(id);
        if(commitment==null)
        {
            throw new ApiException("Error,The Commitment not found");
        }
        //delete the Total Value of Commitment from TheAmountOfMoneyForFinancialCommitments value
        TheSalaryAndSalaryDivision salaryAndSalaryDivision = salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(commitment.getMonthNumber());
        salaryAndSalaryDivision.setTheAmountOfMoneyForFinancialCommitments(salaryAndSalaryDivision.getTheAmountOfMoneyForFinancialCommitments() - commitment.getTotalValue());
        if (commitment.getStatus().equalsIgnoreCase("complete")) {
            //return the  Value taken by Commitment to the TheAmountOfExcessMoney
            salaryAndSalaryDivision.setTheAmountOfExcessMoney(salaryAndSalaryDivision.getTheAmountOfExcessMoney() + commitment.getTotalValue());
        }
        financialCommitmentForOneMonthRepository.delete(commitment);

    }

    public void payCommitment(Integer id)
    {
        FinancialCommitmentForOneMonth commitment=financialCommitmentForOneMonthRepository.findFinancialCommitmentForOneMonthById(id);
        if(commitment==null)
        {
            throw new EntityNotFoundException(new ApiException("Error,The Commitment not found"));
        }
        String status=commitment.getStatus();
        if (status.equalsIgnoreCase("complete"))
        {
            throw new ApiException("Error,The Commitment has been paid");
        }
       TheSalaryAndSalaryDivision salaryAndSalaryDivision=salaryAndSalaryDivisionRepository.findTheSalaryAndSalaryDivisionByMonth(commitment.getMonthNumber());
        double salary=salaryAndSalaryDivision.getTheAmountOfExcessMoney();
        double salaryAfterPay=salary-commitment.getTotalValue();
        salaryAndSalaryDivision.setTheAmountOfExcessMoney(salaryAfterPay);
        salaryAndSalaryDivisionRepository.save(salaryAndSalaryDivision);
        commitment.setStatus("complete");
        financialCommitmentForOneMonthRepository.save(commitment);
    }

    public List<FinancialCommitmentForOneMonth> getAllCommitmentOfSpecificMonth(Integer month)
    {
        return financialCommitmentForOneMonthRepository.findAllByMonthNumber(month);
    }

}
