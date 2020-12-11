package xyz.cloudease.todo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:twillio.properties")
public class TwillioConfig {
}
