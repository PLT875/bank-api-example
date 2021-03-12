package com.starling.domain.services;

import com.starling.infrastructure.starlingapi.StarlingApiClient;
import com.starling.infrastructure.starlingapi.TransactionFeedItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SavingsGoalService {

    private StarlingApiClient starlingApiClient;

    @Autowired
    public SavingsGoalService(StarlingApiClient starlingApiClient) {
        this.starlingApiClient = starlingApiClient;
    }

    public TransactionFeedItemResponse retrieveTransactionFeedBetweenTimestamps(
            String accountUid, String categoryUid, String minTransactionTs, String maxTransactionTs) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("minTransactionTimestamp", minTransactionTs);
        parameters.put("maxTransactionTimestamp", maxTransactionTs);

        return starlingApiClient.getFeedItemsBetweenTimestamps(accountUid, categoryUid, parameters);
    }

}
