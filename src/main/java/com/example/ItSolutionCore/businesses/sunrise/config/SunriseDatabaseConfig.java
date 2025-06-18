package com.example.ItSolutionCore.businesses.sunrise.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.ItSolutionCore.businesses.sunrise",
        entityManagerFactoryRef = "sunriseEntityManager",
        transactionManagerRef =  "sunriseTransactionManager"
)
public class SunriseDatabaseConfig {

    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public PlatformTransactionManager sunriseTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sunriseEntityManager());

        return transactionManager;
    }

    @Bean
    public EntityManagerFactory sunriseEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("sunrise");
        em.setDataSource(sunriseDataSource());
        em.setPackagesToScan("com.example.ItSolutionCore.businesses.sunrise");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        em.setJpaPropertyMap(properties);
        em.afterPropertiesSet(); // 필수로 호출해야 합니다.
        return em.getObject();
    }

    @Bean
    public DataSource sunriseDataSource (){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(dbUrl+"/sunrise")
                .username(dbUser)
                .password(dbPassword)
                .build();
    }
}
