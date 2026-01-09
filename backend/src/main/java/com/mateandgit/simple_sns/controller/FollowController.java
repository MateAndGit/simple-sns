package com.mateandgit.simple_sns.controller;

import com.mateandgit.simple_sns.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{followeeId}")
    public ResponseEntity<String> follow(
            @PathVariable Long followeeId,
            @RequestParam Long followerId
    ) {
        followService.follow(followerId, followeeId);
        return ResponseEntity.ok("팔로우 성공!");
    }

    @DeleteMapping("/{followeeId}")
    public ResponseEntity<String> unfollow(
            @PathVariable Long followeeId,
            @RequestParam Long followerId
    ) {
        followService.unfollow(followerId, followeeId);
        return ResponseEntity.ok("언팔로우 성공!");
    }
}
