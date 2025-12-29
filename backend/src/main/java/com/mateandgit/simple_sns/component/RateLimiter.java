package com.mateandgit.simple_sns.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class RateLimiter {

    private final Map<Long, Integer> requestCounts = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS = 5;

    public boolean isAllowed(Long userId) {
        int count = requestCounts.getOrDefault(userId,0);

        if (count >= MAX_REQUESTS) return false;

        requestCounts.put(userId, count + 1);
        return true;
    }

    @Scheduled(cron = "0 * * * * *")
    public void resetCounts() {
        requestCounts.clear();
        log.info("Rate Limit Counts Cleared!");
    }
}
