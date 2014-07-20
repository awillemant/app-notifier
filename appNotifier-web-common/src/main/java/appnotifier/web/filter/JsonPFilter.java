package appnotifier.web.filter;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPFilter extends AbstractFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonPFilter.class);


	@Override
	protected Logger getLogger() {
		return LOGGER;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.debug("passing through '{}'", this.getClass().getSimpleName());
		HttpServletRequest req = (HttpServletRequest) request;
		String callbackFunctionName = req.getParameter("callback");
		OutputStream responseWriter = response.getOutputStream();
		responseWriter.write((callbackFunctionName + "(").getBytes());
		chain.doFilter(request, response);
		responseWriter.write(");".getBytes());
	}
}
