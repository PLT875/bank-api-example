package com.starling.infrastructure.starlingapi;

import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface StarlingApiClient {

    @RequestLine("GET api/v2/accounts")
    @Headers("Accept: application/json")
    AccountsResponse getAccounts();

    @RequestLine("GET /api/v2/feed/account/{accountUid}/category/{categoryUid}/transactions-between{timestampsMinMax}")
    @Headers("Accept: application/json")
    TransactionFeedItemResponse getFeedItemsBetweenTimestamps(
            @Param("accountUid") String accountUid,
            @Param("categoryUid") String categoryUid,
            @QueryMap Map<String, String> timestampsMinMax);

    @RequestLine("PUT /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}")
    @Headers("Accept: application/json")
    void addMoneyToSavingsGoal(
            @Param("accountUid") String accountUid,
            @Param("savingsGoalUid") String savingsGoalUid,
            @Param("transferUid") String transferUid,
            SavingsGoalTopUpRequest savingsGoalTopUpRequest);

}
