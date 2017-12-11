package org.zmj.springbootdemo.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.zmj.springbootdemo.demo.security.CustomUserServiceImpl;
import org.zmj.springbootdemo.demo.security.MyFilterSecurityInterceptor;
import org.zmj.springbootdemo.demo.utils.MD5Util;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    @Qualifier("customerDataSource")
    private DataSource dataSource;

    @Value("${matcher}")
    String matcher;

    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserServiceImpl();
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()); //user Details Service验证(无MD5加密)
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/loginError","/register","/authImage","/checkImageCode","/authImageBase64","/css/**","/img/**","/js/**",matcher).permitAll()
                .anyRequest().authenticated() //任何请求,登录后才可以访问
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login")
                .defaultSuccessUrl("/home",true)
                .permitAll() //登录页面用户任意访问
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .expiredUrl("/login")
                .and()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                //登录后记住用户，下次自动登录
                //数据库中必须存在名为persistent_logins的表

                // 这里是核心
                .rememberMe()
                .tokenValiditySeconds(1209600)
                //指定记住登录信息所使用的数据源
                .tokenRepository(persistentTokenRepository());//code4

        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }


    @Bean
    PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/img/**","/js/**","/vue/**","/static/**");
        //可以仿照上面一句忽略静态资源
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{//MD5加密验证密码
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }}); //user Details Service验证
    }

    /**
     * allow cross origin requests
     * 跨域相关，请百度
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
