package com.offcn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailHandler authenticationFailHandler;
    @Autowired
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    /**用户没有登陆 走这里
     * 配置认证流程需要使用的对象
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder);
    }

    /**
     * 配置所有的安全策略
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //安全策略配置
        http.authorizeRequests()
                //需要登录的路径
                .antMatchers("/menu/getMenus").authenticated()
                //登录后拥有ROLE_ADMIN指定权限才能访问
                .antMatchers("/pages/ordersetting.html").hasRole("ADMIN")
                //其他请求都需要登录
                .anyRequest().authenticated()
                //指定登录页面，指定登录请求，释放该请求，不需要认证。直接访问，访问其他未登录的页面时会自动跳转到该页面
                .and().formLogin().loginPage("/index.html").loginProcessingUrl("/user/login")
                //用户登录名
                .usernameParameter("username")
                //用户密码
                .passwordParameter("password")
                //登录成功后的处理器
                .successHandler(authenticationSuccessHandler)
                //登录失败后的处理器
                .failureHandler(authenticationFailHandler)
                .permitAll()
                //关闭csrf防护
                .and().csrf().disable()
                //授权异常时的处理器
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler);


        //退出功能
        http.logout().logoutUrl("/logout")
                //退出成功后的跳转页面
                .logoutSuccessUrl("/index.html");
    }

    /**
     * 配置静态资源访问
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/img/**","/js/**"
        ,"/loginstyle/**","/plugins/**");
    }
}
