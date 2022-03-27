package com.ladder.perfumism.notification.domain;

import com.ladder.perfumism.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByMemberAndCreatedAtBetween(Member member, LocalDateTime start, LocalDateTime end);
}
