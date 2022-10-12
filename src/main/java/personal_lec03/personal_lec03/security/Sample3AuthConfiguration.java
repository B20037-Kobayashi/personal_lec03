package personal_lec03.personal_lec03.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class Sample3AuthConfiguration {
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    // このクラスの下部にあるPasswordEncoderを利用して，ユーザのビルダ（ログインするユーザを作成するオブジェクト）を作成する
    UserBuilder users = User.builder();

    // パスワードはBCryptでハッシュ化
    // $ sshrun htpasswd -nbBC 10 user1 hoge
    UserDetails user1 = users
        .username("user1")
        .password("$2y$10$rjQPITJKlse3gjgeiwFpeO6UO5bK4zEmnvWQpYW33TYbpa3Lp0Fom")
        .roles("USER")
        .build();
    UserDetails admin = users
        .username("admin")
        .password("$2y$10$rjQPITJKlse3gjgeiwFpeO6UO5bK4zEmnvWQpYW33TYbpa3Lp0Fom")
        .roles("admin")
        .build();
    // 生成したユーザをImMemoryUserDetailsManagerに渡す
    return new InMemoryUserDetailsManager(user1, admin);
  }

  // Spring Securityのフォームを利用してログインを行う
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin();

    // mvcMatchers().authenticated()がmvcMatchersに指定されたアクセス先に認証処理が必要であることを示す
    http.authorizeHttpRequests()
        .mvcMatchers("/sample3/*").authenticated();

    // ログアウト時は "http://localhost:8000/" に戻る
    http.logout().logoutSuccessUrl("/");
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
