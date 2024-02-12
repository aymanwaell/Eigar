package com.example.Eigar.response;


import com.example.Eigar.model.EigarUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String status;
    private String message;
    private EigarUser data;


    public static UserResponse success(EigarUser data) {
        UserResponse response = new UserResponse();
        response.setStatus("success");
        response.setData(data);
        return response;
    }

    public static UserResponse notFound(String message) {
        UserResponse response = new UserResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }

    public static UserResponse error(String message) {
        UserResponse response = new UserResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }
}
