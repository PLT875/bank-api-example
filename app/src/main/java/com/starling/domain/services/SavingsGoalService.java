package com.starling.domain.services;

import com.starling.domain.util.SavingsGoalUtil;
import com.starling.infrastructure.starlingapi.AccountsResponse;
import com.starling.infrastructure.starlingapi.StarlingApiClient;
import com.starling.infrastructure.starlingapi.TransactionFeedItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SavingsGoalService {

    private StarlingApiClient starlingApiClient;

    @Autowired
    public SavingsGoalService(StarlingApiClient starlingApiClient) {
        this.starlingApiClient = starlingApiClient;
    }

    /**
     * todo: track previously submitted round-ups
     *
     * @param savingsGoalUid
     * @param year
     * @param weekNo
     */
    public void addRoundUpToSavingsGoal(String savingsGoalUid, int year, int weekNo) {
        AccountsResponse accountsResponse = starlingApiClient.getAccounts();
        Optional<AccountsResponse.Account> account = retrievePrimaryAccount(accountsResponse);
        if (!account.isPresent()) {
            // todo: throw AccountNotFoundException
        }

        String accountUid = account.get().getAccountUid();
        String categoryUid = account.get().getDefaultCategory();

        TransactionFeedItemResponse feedItemResponse = retrieveTransactionFeedForGivenWeek(accountUid,
                categoryUid, year, weekNo);
    }

    private TransactionFeedItemResponse retrieveTransactionFeedForGivenWeek(String accountUid, String categoryUid,
                                                                            int year, int weekNo
    ) {
        ZonedDateTime startOfWeek = SavingsGoalUtil.calculateStartOfWeek(year, weekNo);
        String endOfWeek = SavingsGoalUtil.calculateEndOfWeek(startOfWeek);

        Map<String, String> timestampParameters = new HashMap<>();
        timestampParameters.put("minTransactionTimestamp", startOfWeek.toString());
        timestampParameters.put("maxTransactionTimestamp", endOfWeek);

        return starlingApiClient.getFeedItemsBetweenTimestamps(accountUid, categoryUid, timestampParameters);
    }

    private Optional<AccountsResponse.Account> retrievePrimaryAccount(AccountsResponse accountsResponse) {
        return SavingsGoalUtil.retrievePrimaryAccount(accountsResponse);
    }

}
