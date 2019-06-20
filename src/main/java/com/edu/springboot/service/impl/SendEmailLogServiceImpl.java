package com.edu.springboot.service.impl;

import com.edu.springboot.model.SendEmailLog;
import com.edu.springboot.repository.SendEmailLogRepository;
import com.edu.springboot.service.SendEmailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailLogServiceImpl implements SendEmailLogService {
    @Autowired
    private SendEmailLogRepository sendEmailLogRepository;
    @Override
    public void save(SendEmailLog sendEmailLog) {
        sendEmailLogRepository.save(sendEmailLog);
    }
}
