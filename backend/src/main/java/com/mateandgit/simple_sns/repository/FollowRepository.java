package com.mateandgit.simple_sns.repository;

import com.mateandgit.simple_sns.domain.Follow;
import com.mateandgit.simple_sns.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFollowerAndFollowee(User user, User followee);

    Optional<Follow> findByFollowerAndFollowee(User follower, User followee);

    List<Follow> findAllByFollower(User follower);

    List<Follow> findAllByFollowee(User followee);

    long countByFollower(User follower);

    long countByFollowee(User followee);
}
