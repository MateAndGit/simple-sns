package com.mateandgit.simple_sns.controller;

import com.mateandgit.simple_sns.component.RateLimiter;
import com.mateandgit.simple_sns.dto.FeedRequest;
import com.mateandgit.simple_sns.dto.FeedResponse;
import com.mateandgit.simple_sns.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final RateLimiter rateLimiter;

    @PostMapping
    public ResponseEntity<String> createFeed(@RequestBody FeedRequest request) {

        if (!rateLimiter.isAllowed(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("요청 횟수가 너무 많습니다. 잠시 후 다시 시도해주세요.");
        }

        feedService.createFeed(request);
        return ResponseEntity.ok("피드 작성 성공!");
    }

    @GetMapping
    public ResponseEntity<?> getMyFeeds(
            @RequestParam Long userId,
            @PageableDefault(size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable  pageable
            ) {

        if (!rateLimiter.isAllowed(userId)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("요청 횟수가 너무 많습니다. 잠시 후 다시 시도해주세요.");
        }

        Page<FeedResponse> feeds = feedService.getMyFeeds(userId, pageable);
        return ResponseEntity.ok(feeds);
    }
}
