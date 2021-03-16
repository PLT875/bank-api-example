package com.starling.infrastructure.starlingapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountsResponse {

    List<Account> accounts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Account {

        @JsonProperty("accountUid")
        private String accountUid;

        @JsonProperty("accountType")
        private String accountType;

        @JsonProperty("defaultCategory")
        private String defaultCategory;

        @JsonProperty("currency")
        private String currency;

    }

}
