package com.mateandgit.simple_sns.service;

import com.mateandgit.simple_sns.domain.Feed;
import com.mateandgit.simple_sns.domain.Follow;
import com.mateandgit.simple_sns.domain.User;
import com.mateandgit.simple_sns.dto.FeedRequest;
import com.mateandgit.simple_sns.dto.FeedResponse;
import com.mateandgit.simple_sns.repository.FeedRepository;
import com.mateandgit.simple_sns.repository.FollowRepository;
import com.mateandgit.simple_sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void createFeed(FeedRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Feed feed = Feed.builder()
                .user(user)
                .content(request.getContent())
                .build();

        feedRepository.save(feed);
    }

    public Page<FeedResponse> getMyFeeds(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 입니다."));

        Page<Feed> feeds = feedRepository.findAllByUserOrderByCreatedAtDesc(user, pageable);

        return feeds.map(FeedResponse::new);
    }

    public Page<FeedResponse> getTimeline(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        List<Follow> followings = followRepository.findAllByFollower(user);

        List<User> followingUsers = followings.stream()
                .map(Follow::getFollowee)
                        .collect(Collectors.toList());

        followingUsers.add(user);

        Page<Feed> feeds = feedRepository.findAllByUserInOrderByCreatedAtDesc(followingUsers, pageable);

        return feeds.map(FeedResponse::new);
    }
}
