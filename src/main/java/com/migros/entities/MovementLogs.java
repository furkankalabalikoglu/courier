package com.migros.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementLogs {
    private Long id;
    @JsonProperty("courier_id")
    private Long courierId;
    @JsonProperty("log_date")
    private String logDate;
    @JsonProperty("location")
    private Location location;
    private Short isEntrance;
    private Long enteredStoreId;

}
