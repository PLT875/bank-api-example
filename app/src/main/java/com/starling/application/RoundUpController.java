package com.starling.application;

import com.starling.domain.services.SavingsGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoundUpController {

    private SavingsGoalService savingsGoalService;

    @Autowired
    public RoundUpController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @PutMapping("/savings-goal/{savingsGoalUid}/add-round-ups/{year}/{weekNo}")
    public ResponseEntity<?> addWeekRoundUpsToSavingsGoal(
            @PathVariable String savingsGoalUid,
            @PathVariable int year,
            @PathVariable int weekNo
    ) throws Exception {
        savingsGoalService.addRoundUpsToSavingsGoal(savingsGoalUid, year, weekNo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
