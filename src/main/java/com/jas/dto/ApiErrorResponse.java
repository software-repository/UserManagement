package com.jas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiErrorResponse {

    private String timestamp;
    private Integer status;
    private String error;
    private String message;

}
