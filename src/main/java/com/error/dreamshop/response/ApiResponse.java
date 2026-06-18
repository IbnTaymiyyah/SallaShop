package com.error.dreamshop.response;

import lombok.*;

@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;

}
