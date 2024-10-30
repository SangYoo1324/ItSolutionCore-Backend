package com.example.ItSolutionCore.businesses.attManager.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.ItSolutionCore.businesses.attManager",
        entityManagerFactoryRef = "attManagerEntityManager",
        transactionManagerRef = "attManagerTransactionManager"
)
public class AttManagerDataConfig {

    @Bean
    public PlatformTransactionManager attManagerTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(attManagerEntityManager().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean attManagerEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(attManagerDataSource());
        em.setPackagesToScan("com.example.ItSolutionCore.businesses.attManager.entity");
        em.setPersistenceUnitName("attManager");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public DataSource attManagerDataSource(){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://rds-demo.cjykyawcgbun.us-west-2.rds.amazonaws.com/attManager")
                .username("sam")
                .password("Qwdf3696!")
                .build();
    }

}
