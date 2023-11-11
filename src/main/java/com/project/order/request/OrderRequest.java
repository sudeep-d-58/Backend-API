package com.project.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @JsonProperty("origin")
    @NotNull
    @Min(value = 2, message = "Please provide only one latitude and one longitude")
    @Max(value = 2, message = "Please provide only one latitude and one longitude")
    @Valid
    private String[] origin;

    @JsonProperty("destination")
    @NotNull
    @Min(value = 2, message = "Please provide only one latitude and one longitude")
    @Max(value = 2, message = "Please provide only one latitude and one longitude")
    private String[] destination;
}
