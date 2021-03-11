package com.starling.application;

import com.starling.domain.services.TransactionFeedService;
import com.starling.infrastructure.starlingapi.TransactionFeedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

import static com.starling.domain.util.TransactionFeedUtil.calculateEndOfWeek;
import static com.starling.domain.util.TransactionFeedUtil.calculateStartOfWeek;

@RestController
public class RoundUpController {

    private TransactionFeedService transactionFeedService;

    @Autowired
    public RoundUpController(TransactionFeedService transactionFeedService) {
        this.transactionFeedService = transactionFeedService;
    }

    @RequestMapping("/round-up")
    public String index() {
        String accountUid = "85ab5c9c-a380-4fd4-a7dc-847bbdb7bcd7";
        String categoryUid = "29854949-15df-475e-8785-b490dbaec2b2";
        ZonedDateTime startOfWeek = calculateStartOfWeek(2021, 10);
        String endOfWeek = calculateEndOfWeek(startOfWeek);

        TransactionFeedItem item = transactionFeedService.retrieveTransactionFeedBetweenTimestamps(
                accountUid, categoryUid, startOfWeek.toString(), endOfWeek);

        System.out.println(item);
        return "Greetings from Spring Boot!";
    }

}
