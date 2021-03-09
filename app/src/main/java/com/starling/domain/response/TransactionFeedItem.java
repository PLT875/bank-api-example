package com.starling.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionFeedItem {

    @JsonProperty("feedItems")
    private List<FeedItem> feedItems;

}
