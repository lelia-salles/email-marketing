package email.tunemail.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new ProcessHandle.Info() {
                    public Info title(String tuneMailerApi) {
                        return null;
                    }

                    @Override
                    public Optional<String> command() {
                        return Optional.empty();
                    }

                    @Override
                    public Optional<String> commandLine() {
                        return Optional.empty();
                    }

                    @Override
                    public Optional<String[]> arguments() {
                        return Optional.empty();
                    }

                    @Override
                    public Optional<Instant> startInstant() {
                        return Optional.empty();
                    }

                    @Override
                    public Optional<Duration> totalCpuDuration() {
                        return Optional.empty();
                    }

                    @Override
                    public Optional<String> user() {
                        return Optional.empty();
                    }
                }
                        .title("TuneMailer API")
                        .version()
                        .description("API for TuneMailer Email Marketing Service"));
    }
}
