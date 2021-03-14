package com.starling.domain.services;

import com.starling.domain.util.SavingsGoalUtil;
import com.starling.infrastructure.starlingapi.AccountsResponse;
import com.starling.infrastructure.starlingapi.SavingsGoalTopUpRequest;
import com.starling.infrastructure.starlingapi.SavingsGoalTopUpResponse;
import com.starling.infrastructure.starlingapi.StarlingApiClient;
import com.starling.infrastructure.starlingapi.TransactionFeedItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
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
    public void addRoundUpsToSavingsGoal(String savingsGoalUid, int year, int weekNo) {
        AccountsResponse accountsResponse = starlingApiClient.getAccounts();
        Optional<AccountsResponse.Account> account = retrievePrimaryAccount(accountsResponse);
        if (!account.isPresent()) {
            // todo: throw AccountNotFoundException
        }

        String accountUid = account.get().getAccountUid();
        String categoryUid = account.get().getDefaultCategory();

        TransactionFeedItemResponse feedItemResponse = retrieveTransactionFeedForGivenWeek(accountUid,
                categoryUid, year, weekNo);

        Integer savings = SavingsGoalUtil.calculateTotalRoundUpSavings(feedItemResponse);
        if (savings > 0) {
            log.info("Round up savings for week {} of year {}: {}", weekNo, year, savings);
            String transferUid = UUID.randomUUID().toString();
            SavingsGoalTopUpRequest request = SavingsGoalTopUpRequest
                    .builder()
                    .amount(new SavingsGoalTopUpRequest.Amount("GBP", savings))
                    .build();

            SavingsGoalTopUpResponse response = starlingApiClient.addMoneyToSavingsGoal(
                    accountUid, savingsGoalUid, transferUid, request);

            log.info("Adding to savings goal UID: {}. Success: {}. Transfer UID: {}", savingsGoalUid,
                    response.isSuccess(), response.getTransferUid());
        } else {
            log.info("No savings for week {} of year {}", weekNo, year);
        }
    }

    private TransactionFeedItemResponse retrieveTransactionFeedForGivenWeek(
            String accountUid, String categoryUid, int year, int weekNo
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
