package com.img.config.db;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.img.repository.stg",
    entityManagerFactoryRef = "stgEntityManagerFactory",
    transactionManagerRef = "stgTransactionManager"
)
public class StgConfig {

  @Primary
  @Bean(name = "stgDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.stg")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "stgEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("stgDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.img.model.stg")
        .persistenceUnit("stg")
        .build();
  }

  @Primary
  @Bean(name = "stgTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("stgEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}
