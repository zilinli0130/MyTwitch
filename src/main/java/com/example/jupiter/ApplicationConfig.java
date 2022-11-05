//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of ApplicationConfig class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// System includes
import javax.sql.DataSource;
import java.util.Properties;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Configuration
@EnableWebMvc
public class ApplicationConfig {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory(DataSource data_source) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(data_source);
        sessionFactory.setPackagesToScan("com.example.jupiter.entity.db");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        String RDS_ENDPOINT = "twitch-mysql-database.cxbikibd1ize.us-east-2.rds.amazonaws.com";
        String USERNAME = "admin";
        String PASSWORD = "Lzl19970130";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + RDS_ENDPOINT + ":3306/twitch?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        return hibernateProperties;
    }
}
