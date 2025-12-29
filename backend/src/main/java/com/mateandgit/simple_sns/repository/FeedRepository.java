package com.mateandgit.simple_sns.repository;

import com.mateandgit.simple_sns.domain.Feed;
import com.mateandgit.simple_sns.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Page<Feed> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
