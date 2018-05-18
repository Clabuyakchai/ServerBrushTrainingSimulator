package com.clabuyakchai.repository;

import com.clabuyakchai.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    List<Statistics> findStatisticsByUserId(Integer id);
}
