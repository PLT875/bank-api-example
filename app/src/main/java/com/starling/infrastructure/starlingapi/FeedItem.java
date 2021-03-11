package com.starling.infrastructure.starlingapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedItem {

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("amount")
    private FeedItem.Amount amount;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amount {
        @JsonProperty("currency")
        private String currency;

        @JsonProperty("minorUnits")
        private Integer minorUnits;
    }

}
