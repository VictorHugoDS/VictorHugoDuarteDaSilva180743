package br.com.hamix.config.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class FlywayBootstrapConfig {

    @Bean
    @ConditionalOnMissingBean(Flyway.class)
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .schemas("public")
                .defaultSchema("public")
                .load();
    }

    @Bean
    public Object flywayMigrator(Flyway flyway) {
        flyway.migrate();
        return new Object();
    }

    @Bean
    public static BeanFactoryPostProcessor flywayDependencyPostProcessor() {
        return beanFactory -> {
            if (!beanFactory.containsBeanDefinition("entityManagerFactory")) {
                return;
            }
            BeanDefinition entityManagerFactory = beanFactory.getBeanDefinition("entityManagerFactory");
            String[] dependsOn = entityManagerFactory.getDependsOn();
            List<String> updated = new ArrayList<>();
            if (dependsOn != null) {
                updated.addAll(Arrays.asList(dependsOn));
            }
            if (!updated.contains("flywayMigrator")) {
                updated.add("flywayMigrator");
            }
            entityManagerFactory.setDependsOn(updated.toArray(new String[0]));
        };
    }
}
