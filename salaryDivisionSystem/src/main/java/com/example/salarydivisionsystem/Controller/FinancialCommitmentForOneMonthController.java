package com.example.salarydivisionsystem.Controller;

import com.example.salarydivisionsystem.ApiResponce.ApiResponse;
import com.example.salarydivisionsystem.Model.FinancialCommitmentForOneMonth;
import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import com.example.salarydivisionsystem.Service.FinancialCommitmentForOneMonthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/commitment")
@RequiredArgsConstructor
public class FinancialCommitmentForOneMonthController {
    private final FinancialCommitmentForOneMonthService commitmentService;

    @GetMapping("/getall")
    public ResponseEntity getAll()
    {
        return ResponseEntity.status(200).body(commitmentService.getAll());
    }
    //مثال للالتزامات : دين او ايجار او راتب خادمه
    @PostMapping("/add")
    public ResponseEntity addCommitment(@Valid @RequestBody FinancialCommitmentForOneMonth commitment)
    {
        commitmentService.add(commitment);
        return ResponseEntity.status(200).body(new ApiResponse("the commitment is added"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateCommitment(@PathVariable Integer id ,@Valid@RequestBody FinancialCommitmentForOneMonth commitment)
    {
        commitmentService.update(id,commitment);
        return ResponseEntity.status(200).body(new ApiResponse("the commitment is updated"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCommitment(@PathVariable Integer id )
    {
        commitmentService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("the commitment is deleted"));
    }
    @PutMapping("/paycommitment/{id}")
    public ResponseEntity payCommitment(@PathVariable Integer id) {
        commitmentService.payCommitment(id);
        return ResponseEntity.status(200).body(new ApiResponse("the commitment is has been paid"));
    }
    @GetMapping("/get/{month}")
    public ResponseEntity getAllCommitmentOfSpecificMonth(@PathVariable Integer month)
    {
        return ResponseEntity.status(200).body(commitmentService.getAllCommitmentOfSpecificMonth(month));
    }
}
