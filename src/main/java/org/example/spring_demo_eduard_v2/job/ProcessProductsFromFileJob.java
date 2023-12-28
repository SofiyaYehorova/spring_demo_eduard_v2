package org.example.spring_demo_eduard_v2.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring_demo_eduard_v2.dto.ProductDto;
import org.example.spring_demo_eduard_v2.services.DataStorageService;
import org.example.spring_demo_eduard_v2.services.ProductService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessProductsFromFileJob {

    private final DataStorageService dataStorageService;
    private final ProductService productService;

    //    @Scheduled(fixedDelay = 1000)
    @Scheduled(cron = "40 11 * * * *")
    public void process() {
//        log.info("Job is running {}", Thread.currentThread().getName());
        log.info("Processing data...");

        Optional<String> dataOpt = dataStorageService.pollData();

        while (dataOpt.isPresent()) {
            String data = dataOpt.get();
            log.info("Found data: " + data);

            Arrays
                    .stream(data.split("\n"))
                    .map(line -> line.replace("\n", ""))
                    .map(line -> line.replace("\r", ""))
                    .map(line -> line.split(" "))
                    .map(values -> ProductDto
                            .builder()
                            .name(values[0])
                            .price(Double.parseDouble(values[1]))
                            .totalCount(Integer.parseInt(values[2]))
                            .build())
                    .forEach(productService::createProduct);


            dataOpt = dataStorageService.pollData();
        }
    }
//    fixedRate: fixedRate - method execute time = time to the next process
}
