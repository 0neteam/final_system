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
    basePackages = "com.img.repository.trs",
    entityManagerFactoryRef = "trsEntityManagerFactory",
    transactionManagerRef = "trsTransactionManager"
)
public class TrsConfig {

  @Bean(name = "trsDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.trs")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "trsEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("trsDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.img.model.trs")
        .persistenceUnit("trs")
        .build();
  }

  @Bean(name = "trsTransactionManager")
  public PlatformTransactionManager transactionManager(
      @Qualifier("trsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

}
