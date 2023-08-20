package com.example.salarydivisionsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class FinancialCommitmentForOneMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @NotNull(message = "the month Number field is required ")
    @Range(min = 1,max = 12,message ="The Month number must be from 1-12" )
    private Integer monthNumber;
    @NotNull(message = "the title Of Commitment field is required ")
    private String titleOfCommitment;
    @NotNull(message = "the total Value Of Commitment field is required ")
    private Integer totalValue;
    @Value("not complete")
    private String status;
}
