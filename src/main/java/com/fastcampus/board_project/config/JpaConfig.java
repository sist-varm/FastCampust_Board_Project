package com.fastcampus.board_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
//    누가 만들었는지에 대한 데이터를 넣기 위해 - auditing할때 넣어줌
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("uno");
        // TODO : 스프링시큐리티로 인증 기능을 붙이게 될 때, 로그인 정보를 바탕으로 들어가도록 수정하자

    }
}
