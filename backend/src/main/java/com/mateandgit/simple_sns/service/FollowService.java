package com.mateandgit.simple_sns.service;

import com.mateandgit.simple_sns.domain.Feed;
import com.mateandgit.simple_sns.domain.Follow;
import com.mateandgit.simple_sns.domain.User;
import com.mateandgit.simple_sns.dto.FeedResponse;
import com.mateandgit.simple_sns.repository.FeedRepository;
import com.mateandgit.simple_sns.repository.FollowRepository;
import com.mateandgit.simple_sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을수 없습니다."));

        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            throw new IllegalArgumentException("이미 팔로우 중입니다.");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .followee(followee)
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User followee = userRepository.findById(followeeId)
                .orElseThrow(() -> new IllegalArgumentException("대상 사용자를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 아닙니다."));

        followRepository.delete(follow);
    }

}
