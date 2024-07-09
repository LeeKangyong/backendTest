package kr.co.polycube.backendtest.dto.response;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LottoResponseDto {
    private List<Integer> numbers;
}