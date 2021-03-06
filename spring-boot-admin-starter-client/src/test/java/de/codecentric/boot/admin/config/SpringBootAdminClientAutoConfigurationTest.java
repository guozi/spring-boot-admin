package de.codecentric.boot.admin.config;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.springframework.boot.actuate.autoconfigure.ManagementServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import de.codecentric.boot.admin.services.ApplicationRegistrator;

public class SpringBootAdminClientAutoConfigurationTest {

	private AnnotationConfigWebApplicationContext context;

	@After
	public void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void not_active() {
		load();
		assertTrue(context.getBeansOfType(ApplicationRegistrator.class).isEmpty());
	}

	@Test
	public void active() {
		load("spring.boot.admin.url:http://localhost:8081");
		context.getBean(ApplicationRegistrator.class);
	}

	private void load(String... environment) {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(ServerPropertiesAutoConfiguration.class);
		applicationContext.register(ManagementServerPropertiesAutoConfiguration.class);
		applicationContext.register(SpringBootAdminClientAutoConfiguration.class);
		EnvironmentTestUtils.addEnvironment(applicationContext, environment);
		applicationContext.refresh();
		this.context = applicationContext;
	}

}
