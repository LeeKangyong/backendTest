package kr.co.polycube.backendtest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.dto.response.LottoResponseDto;

@Service
public class LottoService {
	
	public LottoResponseDto generateLottoNumbers() {
		Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < 6) {
            int number = (int) (Math.random() * 45) + 1;
            numbers.add(number);
        }

        List<Integer> numberList = new ArrayList<>(numbers);

        return new LottoResponseDto(numberList);
    }

}
