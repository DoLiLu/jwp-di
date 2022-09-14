package core.di.factory;

import core.di.ConfigurationBeanScanner;
import core.jdbc.JdbcTemplate;
import next.controller.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ConfigurationBeanScannerTest {

    private BeanFactory beanFactory;
    private ConfigurationBeanScanner scanner;

    @BeforeEach
    void setup() {
        beanFactory = new BeanFactory();
        scanner = new ConfigurationBeanScanner(beanFactory);
    }

    @Test
    @DisplayName("@Configuration 어노테이션이 존재하는 클래스의 @Bean 어노테이션 Method 빈으로 등록 - DataSource")
    void registerDataSource() {
        scanner.register();
        beanFactory.initialize();

        assertThat(beanFactory.getBean(DataSource.class)).isNotNull();
    }

    @Test
    @DisplayName("@Configuration 어노테이션이 존재하는 클래스의 @Bean 어노테이션 Method 빈으로 등록 - JdbcTemplate")
    void registerJdbcTemplate() {
        scanner.register();
        beanFactory.initialize();

        assertThat(beanFactory.getBean(JdbcTemplate.class)).isNotNull();
    }

    @Test
    @DisplayName("@ComponentScan 정상 적용 테스트")
    void componentScan() {
        scanner.register();
        beanFactory.initialize();

        assertAll(
                () -> assertThat(beanFactory.getBean(ApiUserController.class)).isNotNull(),
                () -> assertThat(beanFactory.getBean(ApiQnaController.class)).isNotNull(),
                () -> assertThat(beanFactory.getBean(HomeController.class)).isNotNull(),
                () -> assertThat(beanFactory.getBean(QnaController.class)).isNotNull(),
                () -> assertThat(beanFactory.getBean(UserController.class)).isNotNull()
        );
    }
}