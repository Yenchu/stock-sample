package idv.samples.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableScheduling
@PropertySource({ "classpath:app.properties" })
@ComponentScan({ "idv.samples.service", "idv.samples.task" })
public class AppConfig {

	@Autowired
	private Environment env;

	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	@Bean
	public JavaMailSenderImpl javaMailSender() {
		String host = env.getProperty("mail.host");
		Integer port = env.getProperty("mail.port", Integer.class);
		String username = env.getProperty("mail.username");
		String password = env.getProperty("mail.password");

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost(host);
		if (port != null && port > 0) {
			mailSender.setPort(port);
		}
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.trust", "*");
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}

	@Bean
	public VelocityEngineFactoryBean velocityEngineFactoryBean() {
		VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
		Properties prop = new Properties();
		prop.setProperty("resource.loader", "class");
		prop.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine.setVelocityProperties(prop);
		return velocityEngine;
	}
}
