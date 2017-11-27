package fr.ekinci.aop.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Gokan EKINCI
 */
@RestController
@RequestMapping(path = "/try-again")
public class TestingTryAgainController {

	@Autowired
	private TestingTryAgainService service;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public void restGetMethod() throws SQLException, IOException {
		// If you change parameter value, the test will fail
		service.launchCatchedMethodByTryAgainNTimes(3);
	}
}
