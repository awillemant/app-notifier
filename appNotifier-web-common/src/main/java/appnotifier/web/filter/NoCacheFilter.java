package appnotifier.web.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoCacheFilter extends AbstractFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(NoCacheFilter.class);


	@Override
	protected Logger getLogger() {
		return LOGGER;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.debug("passing through '{}'", this.getClass().getSimpleName());
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// HTTP 1.1.
		httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		// HTTP 1.0.
		httpResponse.setHeader("Pragma", "no-cache");
		// Proxies.
		httpResponse.setDateHeader("Expires", 0);
		chain.doFilter(request, response);
	}
}
