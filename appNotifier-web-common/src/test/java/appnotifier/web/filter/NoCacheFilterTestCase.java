package appnotifier.web.filter;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class NoCacheFilterTestCase {

    private HttpServletRequest request = mock(HttpServletRequest.class);

    private HttpServletResponse response = mock(HttpServletResponse.class);

    private FilterConfig filterConfig = mock(FilterConfig.class);

    private FilterChain chain = mock(FilterChain.class);

    @InjectMocks
    private NoCacheFilter filter = new NoCacheFilter();

    @Before
    public void init() throws ServletException, IOException {
        filter.init(filterConfig);
    }

    @After
    public void end() {
        filter.destroy();
    }

    @Test
    public void shouldWriteHeadersInResponse() throws IOException, ServletException {
        // GIVEN
        // WHEN
        filter.doFilter(request, response, chain);
        // THEN
        verify(response, times(1)).setHeader(eq("Cache-Control"), eq("no-cache, no-store, must-revalidate"));
        verify(response, times(1)).setHeader(eq("Pragma"), eq("no-cache"));
        verify(response, times(1)).setDateHeader(eq("Expires"), eq(0L));
    }
}