package kr.co.polycube.backendtest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserGetRespDto {
    private Long id;
    private String name;
}
