package com.izhbg.typz.sso.audit.component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.izhbg.typz.sso.audit.dto.AuditLog;


@Component
public class AuditLogQueue {
    private BlockingQueue<AuditLog> blockingQueue = new LinkedBlockingQueue<AuditLog>();

    public void add(AuditLog auditLog) {
        blockingQueue.add(auditLog);
    }

    public AuditLog poll() throws InterruptedException {
        return blockingQueue.poll(1, TimeUnit.SECONDS);
    }
}
