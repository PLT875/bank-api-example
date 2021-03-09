package com.starling.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedItem {

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("amount")
    private FeedItem.Amount amount;

    @Data
    @AllArgsConstructor
    public static class Amount {
        @JsonProperty("currency")
        private String currency;

        @JsonProperty("minorUnits")
        private Integer minorUnits;
    }

}
