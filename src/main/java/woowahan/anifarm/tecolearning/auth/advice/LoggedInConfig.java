package woowahan.anifarm.tecolearning.auth.advice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggedInConfig implements WebMvcConfigurer {
    private final LoggedInInterceptor loggedInInterceptor;

    public LoggedInConfig(LoggedInInterceptor loggedInInterceptor) {
        this.loggedInInterceptor = loggedInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggedInInterceptor)
                .excludePathPatterns("/")
                .excludePathPatterns("/css/*")
                .excludePathPatterns("/js/*")
                .excludePathPatterns("/img/**", "/precache-manifest.*.js", "/sw.js", "/service-worker.js", "/robots.txt", "/manifest*.json")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/login")
                .excludePathPatterns("/docs/**")
                .excludePathPatterns("/api/studies/summary")
                .excludePathPatterns("/api/oauth")
                .excludePathPatterns("/api/users/signup")
                .excludePathPatterns("/api/oauth/login");
    }
}
