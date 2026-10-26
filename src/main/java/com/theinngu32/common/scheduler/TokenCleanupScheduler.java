package com.theinngu32.common.scheduler;

import com.theinngu32.common.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler {

    private final TokenRepository tokenRepository;

    @Scheduled(cron = "0 0 2 * * ?") // Runs daily at 2 AM
    public void cleanExpiredAndRevokedTokens() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(1);
        int deletedCount = tokenRepository.deleteOldInvalidTokens(cutoffTime);
        log.info("Deleted {} expired/revoked tokens created before {}", deletedCount, cutoffTime);
    }
}
