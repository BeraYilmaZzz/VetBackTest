package com.bera.yilmaz.VetManagementSystem.Core.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://vet-app-live-test-l8hgfoo9b-beras-projects-30f10fa9.vercel.app/",
                        "https://vet-app-live-test.vercel.app/",
                        "https://vet-app-live-test-beras-projects-30f10fa9.vercel.app/",
                        "https://vet-app-live-test-git-main-beras-projects-30f10fa9.vercel.app/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //.allowedOrigins("*")
                .allowCredentials(true);
    }
}
