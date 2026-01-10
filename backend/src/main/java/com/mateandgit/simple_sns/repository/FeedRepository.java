package com.mateandgit.simple_sns.repository;

import com.mateandgit.simple_sns.domain.Feed;
import com.mateandgit.simple_sns.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Page<Feed> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<Feed> findAllByUserInOrderByCreatedAtDesc(List<User> users, Pageable pageable);
    Page<Feed> findByContentContainingOrderByCreatedAtDesc(String keyword, Pageable pageable);
}
