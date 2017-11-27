package fr.ekinci.tutorialspringsecurityjwt.security.filters;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Gokan EKINCI
 */
@Slf4j
@Component
public class ZuulFilterImplementation extends ZuulFilter {

	private final String  role_cons="ROLE_BANK_ADVISOR";
	private final String  role_client="ROLE_BANK_CLIENT";
	private int niveau_Authentification=0; //permet de gérer la redirection

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * If return `true` or `false`, routing is done.
	 * If return `true` then execute {@link com.netflix.zuul.IZuulFilter#run()}
	 */
	@Override
	public boolean shouldFilter() {
		// TODO : Handle your users and their roles
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		///////////////////////pour rediriger//////////////

		if (request.getParameter("role").equals(role_cons))
		{
			niveau_Authentification=1;
		}
		else if (request.getParameter("role").equals(role_client)) {
			niveau_Authentification=2;
		}
		/////////////////////////////////////////////////////:

		if(request.getParameter("role").equals(role_cons)||request.getParameter("role").equals(role_client)) {
			RequestContext.getCurrentContext()
					.setSendZuulResponse(
							SecurityContextHolder.getContext()
									.getAuthentication()
									.isAuthenticated()
					);

			return true;
		}

		else return false;
	}

	@Override
	public Object run() {
		final RequestContext ctx = RequestContext.getCurrentContext();
		final HttpServletRequest request = ctx.getRequest();

		// Log called route
		log.info(String.format("[%s][%s][%s]",
				ZuulFilterImplementation.class.getSimpleName(),
				request.getMethod(),
				request.getRequestURL().toString()
		));


		try {
			String url="";
			if (niveau_Authentification==1){
				url = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).path("/advisor-management").build()
						.toUriString();
				ctx.setRouteHost(new URL(url));
				ctx.set("requestURI", url);

			}

			if (niveau_Authentification==2){
				url = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).path("/client-service").build()
						.toUriString();
				ctx.setRouteHost(new URL(url));
				ctx.set("requestURI", url);

			}
//log redirections
			log.info(String.format("[%s][%s][%s]",
					ZuulFilterImplementation.class.getSimpleName(),
					request.getMethod(),
					ctx.getRouteHost()
			));


/*

postman:http://localhost:25001/client-authentification?role=ROLE_BANK_CLIENT
log: [ZuulFilterImplementation][GET][http://localhost:25001/client-authentification/client-service]


http://localhost:25001/advisor-authentification?role=ROLE_BANK_ADVISOR
log: [ZuulFilterImplementation][GET][http://localhost:25001/advisor-authentification/advisor-management]


par la suite il faut faire évoluer le controller du frontapi
pour rediriger les appels à client-authentification et advisor-authentification
sur les services internes respectives, sur les ports 25003 et 25004
 */

			// Return value is not used by Zuul yet
		} catch (MalformedURLException mue) {
			log.error("Cannot forward to outage period website");
		}

		return null;
	}

}
