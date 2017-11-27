package fr.ekinci.aop.test;

import org.springframework.web.bind.annotation.*;

/**
 * @author Gokan EKINCI
 */
@RestController
@RequestMapping(path = "/route-controller")
public class TestingRouteController {


	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public String getOne(@PathVariable("id") String id) {
		return "{foo: \"bar\"}";
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public String post(@RequestBody String body) {
		return "{foo: \"bar\"}";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void put(@PathVariable("id") String id, @RequestBody  String body) {

	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") String id) {

	}
}
