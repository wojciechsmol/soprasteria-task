package com.smol.soprasteriatask.config;

import com.smol.soprasteriatask.error.LoggingAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //current username and password
    private static String currentUsername;
    private static String currentPassword;

    //current user id
    @Value("${user.id")
    private static String currentUserId;

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    //----------------------------------------------------------------------
    // Comment this block of code if you want to use authentication without
    // external API
    // START:
    //------------------------------------------------------------------------

   /* @Autowired
    private CustomAuthenticationProvider authProvider;


    //configure alternative authenticationProvider
    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider);
    }
*/
    //-----------------------------------------------------------------------
    // END
    //------------------------------------------------------------------------*/


    //--------------------------------------------------------------------------
    // Uncomment method below if you want to use authentication through hard-coded user
    // username: user , pass: pwd
    //-----------------------------------------------------------------------------------

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}pwd").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/", "/home").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .csrf().disable();
    }


    public static String getCurrentPassword() {
        return currentPassword;
    }

    public static void setCurrentPassword(String currentPassword) {
        SecurityConfig.currentPassword = currentPassword;
    }

    public static String getCurrentUsername() {
        return currentUsername;
    }

    public static void setCurrentUsername(String currentUsername) {
        SecurityConfig.currentUsername = currentUsername;
    }


    public static String getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(String currentUserId) {
        SecurityConfig.currentUserId = currentUserId;
    }
}


