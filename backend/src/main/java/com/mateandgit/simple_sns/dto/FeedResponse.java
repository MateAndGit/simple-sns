package com.mateandgit.simple_sns.dto;

import com.mateandgit.simple_sns.domain.Feed;
import lombok.Getter;

@Getter
public class FeedResponse {
    private Long id;
    private String nickname;
    private String content;

    public FeedResponse(Feed feed) {
        this.id = feed.getId();
        this.nickname = feed.getUser().getNickname();
        this.content = feed.getContent();
    }
}