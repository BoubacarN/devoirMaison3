package fr.ekinci.aop.test.mvc;

import fr.ekinci.aop.RestLogAspect;
import fr.ekinci.aop.test.AppTest;
import fr.ekinci.aop.test.TestingRouteController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author Gokan EKINCI
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TestingRouteController.class)
@ContextConfiguration(classes = {
	RestLogAspectMvcTest.Context.class
})
public class RestLogAspectMvcTest {

	private static final List<LogRecord> logRecords = Collections.synchronizedList(new ArrayList<>());

	@Autowired
	private MockMvc mvc;

	@Before
	public void beforeTest() {
		logRecords.clear();
	}

	@Test
	public void testRestLogAspect() throws Exception {
		final String sentBody = "mock";
		final String expectedResult = "{foo: \"bar\"}";

		// WHEN
		final String getRoute = "/route-controller/id";
		this.mvc.perform(
			get(getRoute)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(sentBody)
		)
			.andExpect(status().isOk())
			.andExpect(content().json(expectedResult));

		final String postRoute = "/route-controller";
		this.mvc.perform(
			post(postRoute)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(sentBody)
		)
			.andExpect(status().isOk())
			.andExpect(content().json(expectedResult));

		final String putRoute = "/route-controller/id";
		this.mvc.perform(
			put(putRoute)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(sentBody)
		)
			.andExpect(status().isOk());

		final String deleteRoute = "/route-controller/id";
		this.mvc.perform(
			delete(deleteRoute)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
		)
			.andExpect(status().isOk());

		// THEN
		Assert.assertEquals("Number of LogRecord", 4, logRecords.size());

		final String controllerName = TestingRouteController.class.getSimpleName();
		final String getLogResultPattern = String.format("\\[REST\\]\\[[0-9]{1,}ms\\]\\[%s\\]\\[GET\\]\\[/route-controller/\\{id\\}\\]\\[getOne\\(\\)\\] id", controllerName);
		Assert.assertTrue(
			logRecords.stream().anyMatch(logRecord -> logRecord.getMessage().matches(getLogResultPattern))
		);

		final String postLogResultPattern = String.format("\\[REST\\]\\[[0-9]{1,}ms\\]\\[%s\\]\\[POST\\]\\[/route-controller\\]\\[post\\(\\)\\] mock", controllerName);
		Assert.assertTrue(
			logRecords.stream().anyMatch(logRecord -> logRecord.getMessage().matches(postLogResultPattern))
		);

		final String putLogResultPattern = String.format("\\[REST\\]\\[[0-9]{1,}ms\\]\\[%s\\]\\[PUT\\]\\[/route-controller/\\{id\\}\\]\\[put\\(\\)\\] id, mock", controllerName);
		Assert.assertTrue(
			logRecords.stream().anyMatch(logRecord -> logRecord.getMessage().matches(putLogResultPattern))
		);

		final String deleteLogResultPattern = String.format("\\[REST\\]\\[[0-9]{1,}ms\\]\\[%s\\]\\[DELETE\\]\\[/route-controller/\\{id\\}\\]\\[delete\\(\\)\\] id", controllerName);
		Assert.assertTrue(
			logRecords.stream().anyMatch(logRecord -> logRecord.getMessage().matches(deleteLogResultPattern))
		);
	}


	@Import({
		AppTest.class,
		RestLogAspect.class
	})
	public static class Context {

		@PostConstruct
		public void init() {
			addLoggerAppenderForRestLogAspect();
		}

		public void addLoggerAppenderForRestLogAspect(){
			Logger log = Logger.getLogger(RestLogAspect.class.getName());
			log.addHandler(new Handler() {
				@Override
				public void publish(LogRecord record) {
					logRecords.add(record);
				}

				@Override
				public void flush() {}

				@Override
				public void close() throws SecurityException {}
			});
		}
	}
}
