package com.starling.infrastructure.starlingapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsGoalTopUpResponse {

    @JsonProperty("transferUid")
    private String transferUid;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errors")
    private List<Message> errors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {

        @JsonProperty("message")
        private String message;
    }

}


