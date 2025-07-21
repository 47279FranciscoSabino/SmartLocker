package project.smartlocker.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import project.smartlocker.http.pipeline.AuthTokenFilter

@Configuration
class FilterConfig {
    @Bean
    fun authFilter(authTokenFilter: AuthTokenFilter): FilterRegistrationBean<*> {
        val registrationBean = FilterRegistrationBean(authTokenFilter)
        registrationBean.order = 1
        return registrationBean
    }
}