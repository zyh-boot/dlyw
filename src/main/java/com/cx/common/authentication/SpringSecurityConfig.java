package com.cx.common.authentication;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cx.common.handler.*;
import com.cx.common.handler.MyAuthenticationAccessDeniedHandler;
import com.cx.common.handler.MySessionExpiredStrategy;
import com.cx.common.properties.CommonProperties;
import com.cx.common.handler.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    RedisPersistentTokenRepository redisPersistentTokenRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;

    @Autowired
    private MyAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private MyAuthenticationAccessDeniedHandler accessDeniedHandler;

    @Override
    public void configure(WebSecurity web) {
        //Ignore, public
        web.ignoring().antMatchers(commonProperties.getSecurity().getAnonUrl().split(StringPool.COMMA));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不做csrf校验
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(commonProperties.getSecurity().getAnonUrl().split(StringPool.COMMA)).permitAll()
                .antMatchers(HttpMethod.GET, "/login*").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin() // 表单登录
                .loginPage(commonProperties.getSecurity().getLoginUrl())
                //.loginProcessingUrl("/signin")
                .failureUrl(commonProperties.getSecurity().getLoginUrl()+"?error")
                //.successForwardUrl(commonProperties.getShiro().getSuccessUrl())
                .usernameParameter("username")
                .passwordParameter("password")
                .and()// 记住我
                .rememberMe()
                .tokenRepository(redisPersistentTokenRepository) // 配置 token 持久化仓库
                //.tokenValiditySeconds(3600) // remember 过期时间，单为秒
                .userDetailsService(myUserDetailService()) // 处理自动登录逻辑
                .and()
                .logout() // 退出
                .logoutUrl(commonProperties.getSecurity().getLogoutUrl())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .and()
                .sessionManagement() // 添加 Session管理器
              //  .invalidSessionUrl(commonProperties.getSecurity().getLoginUrl()) // Session失效后跳转到这个链接
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredSessionStrategy(sessionExpiredStrategy)
                .and()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
                 http.logout().permitAll();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Bean
    UserDetailsService myUserDetailService() {
        return new SecurityUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 注册Servlet Listener,用于发布Session的创建和销毁事件
     */
    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        ServletListenerRegistrationBean<HttpSessionEventPublisher> registration = new ServletListenerRegistrationBean<>();
        registration.setListener(new HttpSessionEventPublisher());
        return registration;
    }

    /**
     * BCrypt  加密
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
