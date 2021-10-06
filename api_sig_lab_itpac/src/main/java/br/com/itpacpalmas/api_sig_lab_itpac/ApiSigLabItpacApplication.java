package br.com.itpacpalmas.api_sig_lab_itpac;
import br.com.itpacpalmas.api_sig_lab_itpac.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
@EnableConfigurationProperties({
	FileStorageConfig.class
})
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class ApiSigLabItpacApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSigLabItpacApplication.class, args);
	}

}
