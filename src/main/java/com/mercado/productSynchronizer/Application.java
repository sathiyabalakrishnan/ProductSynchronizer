package com.mercado.productSynchronizer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
@EnableAsync
public class Application {
    @Value("${input.path}")
    private String inputPath;
    @Value("${output.path}")
    private String outputPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WatchService watchService() {
        log.info("MONITORING_FOLDER: {}" + inputPath);
        WatchService watchService = null;
        try {
            watchService = FileSystems
                .getDefault()
                .newWatchService();
            Path path = Paths.get(inputPath);
            if (!Files.isDirectory(path)) {
                throw new RuntimeException("Incorrect monitoring folder: " + path);
            }
            path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_CREATE
            );
        }
        catch (IOException e) {
            log.error("Error " + e);
        }
        return watchService;
    }

}
