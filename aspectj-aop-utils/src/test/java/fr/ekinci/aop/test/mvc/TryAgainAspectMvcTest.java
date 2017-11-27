package fr.ekinci.aop.test.mvc;

import fr.ekinci.aop.TryAgainAspect;
import fr.ekinci.aop.test.AppTest;
import fr.ekinci.aop.test.TestingTryAgainController;
import fr.ekinci.aop.test.TestingTryAgainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Gokan EKINCI
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TestingTryAgainController.class)
@ContextConfiguration(classes = {
	TryAgainAspectMvcTest.Context.class
})
public class TryAgainAspectMvcTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestingTryAgainService service;

	@Test
	public void restGetMethod_ExecuteTryAgainMethod_ShouldNotThrowException() throws Exception {
		// WHEN
		final String getRoute = "/try-again";
		this.mvc.perform(
			get(getRoute)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
		)
			.andExpect(status().isOk());

		// THEN
		Assert.assertEquals(4, service.getTotalNumberOfExecution());
	}


	@Import({
		AppTest.class,
		TestingTryAgainService.class,
		TryAgainAspect.class
	})
	public static class Context {

	}
}
