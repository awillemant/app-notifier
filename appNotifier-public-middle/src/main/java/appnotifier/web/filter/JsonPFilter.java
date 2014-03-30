package appnotifier.web.filter;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonPFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(JsonPFilter.class);


	@Override
	public void destroy() {
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String callbackFunctionName = req.getParameter("callback");
		OutputStream responseWriter = response.getOutputStream();
		responseWriter.write((callbackFunctionName + "(").getBytes());
		chain.doFilter(request, response);
		responseWriter.write(");".getBytes());
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("Initializing {}...", this.getClass().getSimpleName());
	}
}
