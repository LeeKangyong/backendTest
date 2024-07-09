package kr.co.polycube.backendtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.polycube.backendtest.dto.response.LottoResponseDto;
import kr.co.polycube.backendtest.service.LottoService;

@RestController
@RequestMapping("/lottos")
public class LottoController {
	
	@Autowired
    private LottoService lottoService;

    @PostMapping
    public ResponseEntity<LottoResponseDto> generateLottoNumbers() {
        LottoResponseDto responseDto = lottoService.generateLottoNumbers();
        return ResponseEntity.ok(responseDto);
    }
}
