package com.michaelmark.healthplanenrollmentapi.config;

import com.michaelmark.healthplanenrollmentapi.model.MetalLevel;
import com.michaelmark.healthplanenrollmentapi.model.Plan;
import com.michaelmark.healthplanenrollmentapi.repository.PlanRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoaderConfig implements ApplicationRunner {

    private final PlanRepository planRepository;

    public DataLoaderConfig(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(DataLoaderConfig.class);

    private int indexOf(String[] headers, String name) {
        for (int i = 0; i < headers.length; i++){
            if (headers[i].trim().equalsIgnoreCase(name)){
                return i;
            }
        }
        log.warn("Column not found: '{}'", name);
        return -1;
    }

    private String getValue(String[] row, int index) {
        if(index < 0 || index >= row.length) {
            return null;
        }
        String value = row[index].trim();
        return value.isEmpty() ? null : value;
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null) return null;
        String cleanedValue = value.replace("$", "").replace(",", "").trim();
        try {
            return new BigDecimal(cleanedValue);
        } catch (NumberFormatException e){
            return null;
        }
    }

    private MetalLevel parseMetalLevel(String metalLevel) {
        if (metalLevel == null) return null;

        return switch (metalLevel.toUpperCase().trim().replace(" ", "_")) {
            case "BRONZE"         -> MetalLevel.BRONZE;
            case "SILVER"         -> MetalLevel.SILVER;
            case "GOLD"           -> MetalLevel.GOLD;
            case "PLATINUM"       -> MetalLevel.PLATINUM;
            case "CATASTROPHIC"   -> MetalLevel.CATASTROPHIC;
            case "EXPANDED_BRONZE" -> MetalLevel.EXPANDED_BRONZE;
            default -> {
                log.warn("Unknown metal level: '{}'", metalLevel);
                yield null;
            }
        };
    }

    private List<Plan> loadPlansFromCsv() throws IOException, CsvException {
        ClassPathResource resource = new ClassPathResource("data/plans_small.csv");
        List<Plan> plans = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(resource.getInputStream()))){
            List<String[]> rows = reader.readAll();

            String[] headers = rows.getFirst();
            int stateCol        = indexOf(headers, "State Code");
            int countyCol       = indexOf(headers, "County Name");
            int metalCol        = indexOf(headers, "Metal Level");
            int issuerNameCol   = indexOf(headers, "Issuer Name");
            int hiosCol         = indexOf(headers, "HIOS Issuer ID");
            int planIdCol       = indexOf(headers, "Plan ID (Standard Component)");
            int planNameCol     = indexOf(headers, "Plan Marketing Name");
            int rateCol         = indexOf(headers, "Premium Adult Individual Age 21");
            int deductibleCol   = indexOf(headers, "Medical Deductible - Individual - Standard");

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                try {
                    Plan plan = new Plan();
                    plan.setState(getValue(row, stateCol));
                    plan.setCounty(getValue(row, countyCol));
                    plan.setMetalLevel(parseMetalLevel(getValue(row, metalCol)));
                    plan.setIssuerName(getValue(row, issuerNameCol));
                    plan.setHiosIssuerId(getValue(row, hiosCol));
                    plan.setPlanId(getValue(row, planIdCol));
                    plan.setPlanName(getValue(row, planNameCol));
                    plan.setIndividualRate(parseBigDecimal(getValue(row, rateCol)));
                    plan.setDeductible(parseBigDecimal(getValue(row, deductibleCol)));
                    plans.add(plan);
                } catch (Exception e) {
                    log.warn("Skipping row {} due to parse error: {}", i + 1, e.getMessage());
                }
            }
        }
        return plans;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (planRepository.count() > 0){
            log.info("Plans are already loaded");
            return;
        }

        try {
            log.info("Ingestion from CSV is starting...");
            List<Plan> plans = loadPlansFromCsv();
            planRepository.saveAll(plans);
            log.info("Data ingestion complete. {} plans loaded.", plans.size());
        } catch (IOException e){
            log.error("Could not read plans CSV file: {}", e.getMessage());
        } catch (CsvException e) {
            log.error("Failed to parse plans CSV: {}", e.getMessage());
        }
    }
}
