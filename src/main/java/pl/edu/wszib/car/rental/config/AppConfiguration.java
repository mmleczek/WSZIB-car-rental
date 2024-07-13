package pl.edu.wszib.car.rental.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.edu.wszib.car.rental.filters.AdminFilter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "pl.edu.wszib.car.rental.dao.impl.spring.data")
@ComponentScan("pl.edu.wszib.car.rental")
public class AppConfiguration {
    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/car/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
