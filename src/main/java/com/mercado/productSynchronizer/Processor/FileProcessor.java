package com.mercado.productSynchronizer.Processor;

import com.mercado.productSynchronizer.dto.Product;
import com.mercado.productSynchronizer.resttemplate.RestTemplateClient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileProcessor {
    @Autowired
    private RestTemplateClient restTemplateClient;
    @Value("${input.path}")
    private String             inputPath;
    @Value("${output.path}")
    private String             outputPath;

    public void process(String fileName) {
        log.info("fileProcessor");
        List<List<String>> records = new ArrayList<>();
        String input = inputPath + fileName;
        Path sourcePath = Paths.get(input);
        Path destinationPath = Paths.get(outputPath + fileName);
        log.info("File exists--------->" + Files.exists(sourcePath));
        if (Files.exists(sourcePath)) {
            try (Stream<String> stream = Files.lines(sourcePath)) {
                stream.skip(1).forEach(data -> {
                    String[] output = data.split(Pattern.quote(","));
                    Product productDTO = new Product();
                    productDTO.setId(Long.valueOf(output[0]));
                    productDTO.setTitle(output[1]);
                    productDTO.setDescription(output[2]);
                    productDTO.setPrice(output[3]);
                    productDTO.setQuantity(output[4]);
                    restTemplateClient.createProduct(productDTO);
                });
                log.info("file move started--------->");
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                log.info("<<<<----------file move ended");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            log.info(records.toString());
        } else {
            log.info("file nt exist");
        }
    }
}
