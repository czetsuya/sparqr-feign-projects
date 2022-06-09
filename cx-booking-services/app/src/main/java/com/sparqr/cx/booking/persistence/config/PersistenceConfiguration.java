package com.sparqr.cx.booking.persistence.config;

import com.sparqr.cx.booking.persistence.repositories.RepositoriesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    basePackageClasses = {
        RepositoriesConfiguration.class
    }
)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
public class PersistenceConfiguration {

}
