package com.starling.infrastructure.starlingapi;

import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface StarlingApiClient {

    @Headers("Accept: application/json")
    @RequestLine("GET /api/v2/feed/account/{accountUid}/category/{categoryUid}/transactions-between/{parameters}")
    TransactionFeedItem getFeedItemsTransactionsBetween(@Param("accountUid") String accountUid,
                                                        @Param("categoryId") String categoryUid,
                                                        @QueryMap Map<String, String> parameters);
}
