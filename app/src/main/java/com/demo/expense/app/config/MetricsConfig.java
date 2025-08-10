package com.demo.expense.app.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        // Add app-wide tags for better dashboards and correlation
        return registry -> registry.config().commonTags(
                "app", "expense-platform",
                "module", "app"
        );
    }
}