package com.example.Eigar.response;

import com.example.Eigar.model.RentalTransaction;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RentalTransactionResponse {

    private String message;
    private String status;
    private RentalTransaction data;

    public static RentalTransactionResponse success(RentalTransaction data){
        RentalTransactionResponse response = new RentalTransactionResponse();
        response.setStatus("success");
        response.setData(data);
        return response;
    }
    public static RentalTransactionResponse success(String message){
        RentalTransactionResponse response = new RentalTransactionResponse();
        response.setStatus("success");
        response.setMessage(message);
        return response;
    }
    public static RentalTransactionResponse error(String message){
        RentalTransactionResponse response = new RentalTransactionResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }
    public static RentalTransactionResponse notFound(String message){
        RentalTransactionResponse response = new RentalTransactionResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }
}
