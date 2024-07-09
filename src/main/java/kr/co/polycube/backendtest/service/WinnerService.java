package kr.co.polycube.backendtest.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.polycube.backendtest.dto.response.LottoResponseDto;
import kr.co.polycube.backendtest.model.Lotto;
import kr.co.polycube.backendtest.model.Winner;
import kr.co.polycube.backendtest.repository.LottoRepository;
import kr.co.polycube.backendtest.repository.WinnerRepository;

@Service
public class WinnerService {

    @Autowired
    private LottoService lottoService;

    @Autowired
    private LottoRepository lottoRepository;

    @Autowired
    private WinnerRepository winnerRepository;

    public void evaluateWinners() {
        List<Lotto> lottos = lottoRepository.findAll();
        List<Integer> winningNumbers = getWinningNumbers();

        for (Lotto lotto : lottos) {
            int matchCount = getMatchCount(lotto, winningNumbers);
            Integer rank = getRankByMatchCount(matchCount);
            if (rank != null) {
                Winner winner = new Winner();
                winner.setLottoId(lotto.getId());
                winner.setRank(rank);
                winnerRepository.save(winner);
            }
        }
    }

    public List<Integer> getWinningNumbers() {
        
        LottoResponseDto winningLotto = lottoService.generateLottoNumbers();
        return winningLotto.getNumbers();
    }

    public int getMatchCount(Lotto lotto, List<Integer> winningNumbers) {
        int matchCount = 0;
        List<Integer> lottoNumbers = List.of(
                lotto.getNumber1(), lotto.getNumber2(), lotto.getNumber3(),
                lotto.getNumber4(), lotto.getNumber5(), lotto.getNumber6()
        );

        for (Integer number : lottoNumbers) {
            if (winningNumbers.contains(number)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    public Integer getRankByMatchCount(int matchCount) {
        switch (matchCount) {
            case 6: return 1;
            case 5: return 2;
            case 4: return 3;
            case 3: return 4;
            case 2: return 5;
            default: return null; // 2개 미만의 일치 개수는 당첨이 없는 것으로 처리
        }
    }

    public List<Winner> getAllWinners() {
        return winnerRepository.findAll();
    }
}