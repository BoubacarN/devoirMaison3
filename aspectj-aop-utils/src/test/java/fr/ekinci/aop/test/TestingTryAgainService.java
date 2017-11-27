package fr.ekinci.aop.test;

import fr.ekinci.aop.TryAgain;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gokan EKINCI
 */
@Service
public class TestingTryAgainService {
	private final AtomicInteger count = new AtomicInteger(0);

	/**
	 * This method simulate N times launched exception
	 * If `times` = 3, method launches exception 3 times, then stops
	 *
	 * @throws IOException
	 */
	@TryAgain(exceptions = {SQLException.class, IOException.class})
	public int launchCatchedMethodByTryAgainNTimes(Integer times) throws SQLException, IOException {
		int incrementationValue = count.getAndIncrement();
		if (incrementationValue < times) {
			throw new IOException();
		}

		return incrementationValue;
	}

	/**
	 * Same method than {@link TestingTryAgainService#launchCatchedMethodByTryAgainNTimes(Integer)} but you throw {@link SQLException}
	 * This TryAgain method does catch IOException, but not catch SQLException
	 *
	 * @throws Throwable
	 */
	@TryAgain(exceptions = {IOException.class})
	public int stopLaunchingSQLExceptionAfterNExecution(Integer times) throws Throwable {
		int incrementationValue = count.getAndIncrement();
		if (incrementationValue < times) {
			throw new SQLException();
		}

		return incrementationValue;
	}

	/**
	 * @return Number of execution
	 */
	public int getTotalNumberOfExecution() {
		return count.get();
	}
}
