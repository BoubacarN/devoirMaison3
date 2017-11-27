package fr.ekinci.aop.test;

import fr.ekinci.aop.TryAgainAspect;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.SQLException;

/**
 * @author Gokan EKINCI
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
	TryAgainAspectTest.Context.class
})
public class TryAgainAspectTest {

	@Autowired
	private TestingTryAgainService service;

	@Test
	public void launchCatchedMethodByTryAgainNTimes_ExecuteTryAgainMethod_ShouldNotThrowException()
			throws Throwable {
		int nbTimesExceptionLaunched = service.launchCatchedMethodByTryAgainNTimes(3);
		Assert.assertEquals(3, nbTimesExceptionLaunched);
		Assert.assertEquals(4, service.getTotalNumberOfExecution());
	}

	@Test(expected = SQLException.class)
	public void stopLaunchingSQLExceptionAfterNExecution_ExecuteTryAgainMethod_ShouldThrowException()
			throws Throwable {
		service.stopLaunchingSQLExceptionAfterNExecution(2);
	}

	@Import({
		AppTest.class,
		TestingTryAgainService.class,
		TryAgainAspect.class
	})
	public static class Context {

	}
}
