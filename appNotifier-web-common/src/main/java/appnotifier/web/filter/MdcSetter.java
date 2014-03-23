package appnotifier.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import appnotifier.core.context.UtilisateurContext;

public class MdcSetter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(MdcSetter.class);


	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try {
			UtilisateurContext u = getConnectedUser();
			if (u != null) {
				MDC.put("loggedUser", u.getUsername());
			}
			chain.doFilter(req, resp);
		} catch (Exception e) {
			logger.warn("Erreur dans l'ecriture du MDC", e);
		} finally {
			MDC.remove("loggedUser");
		}
	}


	private UtilisateurContext getConnectedUser() {
		try {
			SecurityContext context = SecurityContextHolder.getContext();
			if (context == null)
				return null;
			Authentication authentication = context.getAuthentication();
			if (authentication == null)
				return null;
			UtilisateurContext principal = (UtilisateurContext) authentication.getPrincipal();
			return principal;
		} catch (ClassCastException e) {
			logger.warn("erreur dans le recuperation du loggedUser");
			return null;
		} catch (Throwable e) {
			logger.error("erreur dans le recuperation du loggedUser", e);
			return null;
		}
	}


	public void init(FilterConfig arg0) throws ServletException {
		logger.info("Initializing {}...", this.getClass().getSimpleName());
	}


	public void destroy() {
	}
}
