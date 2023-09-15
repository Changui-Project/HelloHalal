package cu.dev.halal.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(
            @Autowired JwtTokenProvider jwtTokenProvider
    ){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // WebSecurity는 HttpSecurity 앞단에 적용되며 스프링 시큐리티의 영향권 밖에 있다.
    // 인증과 인가를 모두 피하기 위한 용도로 사용
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//        web.ignoring()
//                .antMatchers("/test/**")
//                .antMatchers(("/**"));
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // UI를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화
        http.httpBasic().disable()
                // REST API에서는 CSRF 보안이 필요 없다.
                .csrf().disable()
                .cors().and()
                // session 기능 제거
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // antPattern을 통해 권한을 설정
                // 아래에 작성되는 주소들은 모두 허용
                .authorizeRequests()
                // 허용할 주소 ex: 로그인, 회원가입
                .antMatchers("/auth/signin", "/auth/signup").permitAll()
                // .antMatchers(HttpMethod.GET, "/salespost", "/promotionalpost").permitAll()
//                // 권한 체크가 완료된 ADMIN과 USER에게 제공되는 API
                //.antMatchers("/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/**").authenticated()
                // 개발용 설정
                //.anyRequest().hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest().hasRole("ADMIN")
                .and()
                // 권한을 통과하지 못할 경우 예외 전달
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                // 인증에서 예외가 발생한 경우 예외 전달
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);




    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        // 모든 origin 허용
        config.setAllowedOrigins(Arrays.asList("/**"));
        // 모든 HTTP 메소드 허용
        config.setAllowedMethods(Arrays.asList("*"));
        // 모든 요청 헤더 허용
        config.setAllowedHeaders(Arrays.asList("*"));
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
