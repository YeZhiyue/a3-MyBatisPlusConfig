package com.example.springsecuritydemo.config.security.config;

import com.example.springsecuritydemo.config.security.JwtAccessDeniedHandler;
import com.example.springsecuritydemo.config.security.JwtAuthenticationEntryPoint;
import com.example.springsecuritydemo.config.security.UserDetailsServiceImpl;
import com.example.springsecuritydemo.config.security.filter.JwtAuthenticationFilter;
import com.example.springsecuritydemo.config.security.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author 叶之越
 * Description 核心的安全设置
 * Date 2020/9/24
 * Time 7:52
 * Mail 739153436@qq.com
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户信息处理，例如：如果用户名是大小写不敏感的话，我们就需要进行特殊的处理，然后从数据库获取用户信息
     */
    @Bean
    public UserDetailsService createUserDetailsService() {
        return userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义的userDetailsService以及密码编码器
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/sign-up").permitAll()
                // 指定路径下的资源需要验证了的用户才能访问
                .antMatchers("/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                // 其他都放行了
                .anyRequest().permitAll()
                .and()
                //添加自定义Filter
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsServiceImpl))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());
        // 防止H2 web 页面的Frame 被拦截
        http.headers().frameOptions().disable();
    }
}
