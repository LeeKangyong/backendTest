package kr.co.polycube.backendtest.service;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.polycube.backendtest.model.Lotto;
import kr.co.polycube.backendtest.model.Winner;
import kr.co.polycube.backendtest.repository.LottoRepository;
import kr.co.polycube.backendtest.repository.WinnerRepository;

@Service
public class LottoBatchService implements Tasklet {

    @Autowired
    private LottoRepository lottoRepository;

    @Autowired
    private WinnerRepository winnerRepository;

    @Autowired
    private WinnerService winnerService;

    @Transactional
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        compareLottoNumbers();
        return RepeatStatus.FINISHED;
    }

    public void compareLottoNumbers() {
        List<Lotto> allLottos = lottoRepository.findAll();
        List<Integer> winningNumbers = winnerService.getWinningNumbers();

        for (Lotto lotto : allLottos) {
            int matchCount = winnerService.getMatchCount(lotto, winningNumbers);
            Integer rank = winnerService.getRankByMatchCount(matchCount);
            if (rank != null) {
                Winner winner = new Winner(lotto.getId(), rank);
                winnerRepository.save(winner);
            }
        }
    }
}