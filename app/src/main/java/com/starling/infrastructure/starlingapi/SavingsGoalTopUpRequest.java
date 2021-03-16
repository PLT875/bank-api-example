package com.starling.infrastructure.starlingapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoalTopUpRequest {

    @JsonProperty("amount")
    private Amount amount;

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
