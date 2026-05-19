package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.Notification;
import com.localservice.bookingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByUser(User user);


    List<Notification> findByUserAndIsRead(User user, Boolean isRead);


    Long countByUserAndIsRead(User user, Boolean isRead);
}