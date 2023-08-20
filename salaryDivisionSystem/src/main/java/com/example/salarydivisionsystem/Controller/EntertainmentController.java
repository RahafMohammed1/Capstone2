package com.example.salarydivisionsystem.Controller;

import com.example.salarydivisionsystem.ApiResponce.ApiResponse;
import com.example.salarydivisionsystem.Model.Entertainment;
import com.example.salarydivisionsystem.Model.FinancialCommitmentForOneMonth;
import com.example.salarydivisionsystem.Service.EntertainmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/entertainment")
@RequiredArgsConstructor
public class EntertainmentController {
    final private EntertainmentService entertainmentService;
    @GetMapping("/getall")
    public ResponseEntity getAll()
    {
        return ResponseEntity.status(200).body(entertainmentService.getAll());
    }
    //مثال الطعام،القهوه
    @PostMapping("/add")
    public ResponseEntity addEntertainment(@Valid @RequestBody Entertainment entertainment)
    {
        entertainmentService.add(entertainment);
        return ResponseEntity.status(200).body(new ApiResponse("the Entertainment is added"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateEntertainment(@PathVariable Integer id ,@Valid @RequestBody Entertainment entertainment)
    {
        entertainmentService.update(id,entertainment);
        return ResponseEntity.status(200).body(new ApiResponse("the Entertainment is updated"));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEntertainment(@PathVariable Integer id )
    {
        entertainmentService.delete(id);
        return ResponseEntity.status(200).body(new ApiResponse("the Entertainment is deleted"));
    }
    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity WithdrawMoneyFromOneOfTheEntertainmentDepartments(@PathVariable Integer id,@PathVariable Integer amount ) {
        entertainmentService.WithdrawMoneyFromOneOfTheEntertainmentDepartments(id,amount);
        return ResponseEntity.status(200).body(new ApiResponse("the withdraw is done"));
    }
}
