package com.example.ItSolutionCore.businesses.itSolution.config;

import org.springframework.beans.factory.annotation.Value;
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
        basePackages = "com.example.ItSolutionCore.businesses.itSolution",
        entityManagerFactoryRef = "itSolutionEntityManager",
        transactionManagerRef =  "itSolutionTransactionManager"
)
public class ItSolutionDataConfig {

    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPassword;


    @Bean
    public PlatformTransactionManager itSolutionTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(itSolutionEntityManager().getObject());

        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean itSolutionEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(itSolutionDataSource());
        em.setPackagesToScan("com.example.ItSolutionCore.businesses.itSolution.entity"
//                "com.example.ItSolutionCore.common.itSolution.entity"
        );
        em.setPersistenceUnitName("itSolution");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        em.setJpaPropertyMap(properties);
        return em;
    }



    @Bean
    public DataSource itSolutionDataSource (){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(dbUrl+"/itSolution")
                .username(dbUser)
                .password(dbPassword)
                .build();
    }

}
