package com.edu.springboot.repository;

import com.edu.springboot.model.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendEmailLogRepository extends JpaRepository<SendEmailLog,Long> {
}
