package appnotifier.web.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoCacheFilter implements Filter{

	private static Logger logger  = LoggerFactory.getLogger(NoCacheFilter.class);
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		
		logger.debug("Filtering '{}' ...",uri);
			
		rep.setHeader( "Pragma", "no-cache" );
		rep.setHeader( "Cache-Control", "no-cache" );
		rep.setDateHeader( "Expires", 0 );
	
		rep.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.info("Initializing {}...",this.getClass().getSimpleName());
		
	}

	
}
