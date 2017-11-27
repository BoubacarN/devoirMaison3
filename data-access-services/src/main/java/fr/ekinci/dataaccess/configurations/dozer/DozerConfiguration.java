package fr.ekinci.dataaccess.configurations.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Global configuration with XML file only:
 *     https://stackoverflow.com/questions/10033937/configure-custom-converters-using-dozer-java-api
 *     http://dozer.sourceforge.net/documentation/globalConfiguration.html
 *
 * @author Gokan EKINCI
 */
@Configuration
public class DozerConfiguration {

	@Bean
	public Mapper dozer() throws IOException {
		final DozerBeanMapper dozer = new DozerBeanMapper();
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("dozerConfiguration/dozer.xml")) {
			dozer.addMapping(is);
			return dozer;
		}
	}
}
