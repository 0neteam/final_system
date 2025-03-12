package com.img.config.db;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.img.repository.mfr",
    entityManagerFactoryRef = "mfrEntityManagerFactory",
    transactionManagerRef = "mfrTransactionManager"
)
public class MfrConfig {

  @Bean(name = "mfrDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.mfr")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "mfrEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("mfrDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.img.model.mfr")
        .persistenceUnit("mfr")
        .build();
  }

  @Bean(name = "mfrTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("mfrEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}
