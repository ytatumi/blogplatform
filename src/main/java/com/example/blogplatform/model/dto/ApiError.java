package com.example.blogplatform.model.dto;


import java.time.LocalDateTime;
import java.util.List;



public record ApiError(int status,
                       String error,
                       List<String> messages,
                       LocalDateTime timestamp){
}
