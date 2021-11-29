package com.mercado.productSynchronizer.service.impl;

import com.mercado.productSynchronizer.Processor.FileProcessor;
import com.mercado.productSynchronizer.service.FileMonitoringService;
import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class FileMonitoringServiceImpl implements FileMonitoringService {

    private final WatchService  watchService;
    private final FileProcessor fileProcessor;

    @Async
    @PostConstruct
    @Override
    public void launchMonitoring() {
        log.info("START_MONITORING");
        try {
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info("Event kind: {}; File affected: {}", event.kind(), event.context());
                    fileProcessor.process(event
                        .context()
                        .toString());
                }
                key.reset();
            }
        }
        catch (InterruptedException e) {
            log.warn("interrupted exception for monitoring service");
        }
    }

    @PreDestroy
    @Override
    public void stopMonitoring() {
        log.info("STOP_MONITORING");

        if (watchService != null) {
            try {
                watchService.close();
            }
            catch (IOException e) {
                log.error("exception while closing the monitoring service");
            }
        }
    }
}