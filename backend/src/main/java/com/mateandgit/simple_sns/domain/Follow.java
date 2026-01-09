package com.mateandgit.simple_sns.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "follows",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_follower_followee",
                        columnNames = {"follower_id", "followee_id"}
                )
        },
        indexes = {
                @Index(name = "idx_follower", columnList = "follower_id"),
                @Index(name = "idx_followee", columnList = "followee_id")
        }
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder

    public Follow(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
