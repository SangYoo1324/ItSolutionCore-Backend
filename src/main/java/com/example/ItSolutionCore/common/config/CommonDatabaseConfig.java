package com.example.ItSolutionCore.common.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.ItSolutionCore.common",
        entityManagerFactoryRef = "commonEntityManager",
        transactionManagerRef =  "commonTransactionManager"
)
public class CommonDatabaseConfig {
    @Primary
    @Bean
    public PlatformTransactionManager commonTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(commonEntityManager().getObject());

        return transactionManager;
    }
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean commonEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(commonDataSource());
        em.setPackagesToScan("com.example.ItSolutionCore.common.entity",
                "com.example.ItSolutionCore.common.auth.entity"
        );
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // unit name is required to distinguish each persistence unit per database
        em.setPersistenceUnitName("common");
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        em.setJpaPropertyMap(properties);
        return em;
    }


    @Primary
    @Bean
    public DataSource commonDataSource (){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://rds-demo.cjykyawcgbun.us-west-2.rds.amazonaws.com/itsolution_core")
                .username("sam")
                .password("Qwdf3696!")
                .build();
    }
}
