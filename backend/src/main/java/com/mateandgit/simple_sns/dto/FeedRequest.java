package com.mateandgit.simple_sns.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedRequest {
    private Long userId;
    private String content;
}
