package com.example.salarydivisionsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class TheSalaryAndSalaryDivision {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Integer id;
   @NotNull(message = "the month field is required ")
   @Range(min = 1,max = 12,message ="The Month number must be from 1-12" )
   private Integer month;
   @NotNull(message = "the Salary amount field is required ")
   private double salaryAmount;
   private double theAmountOfMoneyForFinancialCommitments=0;
   private double TheAmountOfMoneyForEntertainment=0;
   private double TheAmountOfMoneyForSaving=0;
   private double TheAmountOfExcessMoney=0;

}
