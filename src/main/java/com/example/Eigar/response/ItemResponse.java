package com.example.Eigar.response;

import com.example.Eigar.model.Item;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Data
@RequiredArgsConstructor
public class ItemResponse {

    private String message;
    private String status;
    private Item data;

    public static ItemResponse success(Item data){
        ItemResponse response = new ItemResponse();
        response.setStatus("success");
        response.setData(data);
        return response;
    }

    public static ItemResponse error(String message){
        ItemResponse response = new ItemResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }

    public static ItemResponse notFound(String message){
        ItemResponse response = new ItemResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }
}
