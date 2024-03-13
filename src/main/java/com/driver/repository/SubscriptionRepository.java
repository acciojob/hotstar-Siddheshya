package com.driver.repository;

import com.driver.model.ProductionHouse;
import com.driver.model.Subscription;
import com.driver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
    @Query("SELECT sb FROM Subscription sb WHERE sb.user = :user")
    Subscription findSubscriptionByUserId(@Param("user") User user);
}
