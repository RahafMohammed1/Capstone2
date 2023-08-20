package com.example.salarydivisionsystem.Controller;

import com.example.salarydivisionsystem.ApiResponce.ApiResponse;
import com.example.salarydivisionsystem.Model.TheSalaryAndSalaryDivision;
import com.example.salarydivisionsystem.Service.SalaryAndSalaryDivisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/salary")
@RequiredArgsConstructor
public class SalaryAndSalaryDivisionController {
    private final SalaryAndSalaryDivisionService salaryService;
    //get all salary division summery of all month
    @GetMapping("/getall")
    public ResponseEntity getAll()
    {
        return ResponseEntity.status(200).body(salaryService.getAllSummeryOfSalaryDivisionsOfAllMonths());
    }
    //you can just add the salary and month but even though you fill another attributes by numbers its become zero but the saving is calculation is done
    @PostMapping("/add")
    public ResponseEntity addSalary(@Valid@RequestBody TheSalaryAndSalaryDivision salary)
    {
        salaryService.addSalary(salary);
        return ResponseEntity.status(200).body(new ApiResponse("the salary is added"));
    }
    //you can just update the salary and month but even though you fill another attributes by numbers its become zero but the saving is calculation is done
    @PutMapping("/update/{month}")
    public ResponseEntity updateSalary(@PathVariable Integer month ,@Valid@RequestBody TheSalaryAndSalaryDivision salary)
    {
        salaryService.updateSalary(month,salary);
        return ResponseEntity.status(200).body(new ApiResponse("the salary is updated"));
    }

    @DeleteMapping("/delete/{month}")
    public ResponseEntity deleteSalary(@PathVariable Integer month )
    {
        salaryService.deleteSalary(month);
        return ResponseEntity.status(200).body(new ApiResponse("the salary is deleted"));
    }
   //to get All Summery Of Salary Divisions Of specific month
    @GetMapping("/get/{month}")
    public ResponseEntity get(@PathVariable Integer month)
    {
        return ResponseEntity.status(200).body(salaryService.getSummeryOfSalaryDivisionsForSpecificMonth(month));
    }

}
