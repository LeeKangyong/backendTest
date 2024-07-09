package kr.co.polycube.backendtest.dto.request;



import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterReqDto {
	@NotNull(message = " ID is mandatory")
    private Long id;

    @NotNull(message = " Name is mandatory")
    private String name;
}