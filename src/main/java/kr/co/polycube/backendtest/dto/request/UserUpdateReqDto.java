package kr.co.polycube.backendtest.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateReqDto {
    @NotNull(message = " Name is mandatory")
    private String name;
}
