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

    @PutMapping("/savings-goal/{savingsGoalUid}/account/{accountUid}/add-round-ups")
    public ResponseEntity<?> addRoundUpsForWeekToSavingsGoal(
            @PathVariable String savingsGoalUid,
            @PathVariable String accountUid

    ) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
