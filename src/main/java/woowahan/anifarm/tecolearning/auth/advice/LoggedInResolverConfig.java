package woowahan.anifarm.tecolearning.auth.advice;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import woowahan.anifarm.tecolearning.auth.WebToken;

import java.util.List;

@Configuration
@AllArgsConstructor
public class LoggedInResolverConfig implements WebMvcConfigurer {

    private final WebToken webToken;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoggedInUserArgumentResolver(webToken));
    }
}
