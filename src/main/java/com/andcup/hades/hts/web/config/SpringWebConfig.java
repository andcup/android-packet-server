package com.andcup.hades.hts.web.config;

import com.andcup.hades.hts.web.ApplicationContextRegister;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by Amos
 * Date : 2017/3/30 15:32.
 * Description:
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.andcup.hades.hts.web.controller")
public class SpringWebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public ApplicationContextAware applicationContext(){
        return new ApplicationContextRegister();
    }
}
