package appnotifier.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;

public abstract class AbstractFilter implements Filter {

    public AbstractFilter() {
        super();
    }

    protected abstract Logger getLogger();

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        getLogger().info("Initializing {}...", this.getClass().getSimpleName());
    }

    @Override
    public void destroy() {
        getLogger().info("Destroying {}...", this.getClass().getSimpleName());
    }

    @Override
    public abstract void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

}