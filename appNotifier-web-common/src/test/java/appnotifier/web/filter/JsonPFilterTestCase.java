package appnotifier.web.filter;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class JsonPFilterTestCase {

    private HttpServletRequest request = mock(HttpServletRequest.class);

    private HttpServletResponse response = mock(HttpServletResponse.class);

    private ServletOutputStream responseWriter = mock(ServletOutputStream.class);

    private FilterConfig filterConfig = mock(FilterConfig.class);

    private FilterChain chain = mock(FilterChain.class);

    @InjectMocks
    private JsonPFilter filter = new JsonPFilter();

    @Before
    public void init() throws ServletException, IOException {
        filter.init(filterConfig);
        when(response.getOutputStream()).thenReturn(responseWriter);
    }

    @After
    public void end() {
        filter.destroy();
    }

    @Test
    public void shouldWrapResponseWithJsonPadding() throws IOException, ServletException {
        // GIVEN
        when(request.getParameter(eq("callback"))).thenReturn("callbackFunctionName");
        // WHEN
        filter.doFilter(request, response, chain);
        // THEN
        verify(responseWriter, times(1)).write(eq("callbackFunctionName(".getBytes()));
        verify(responseWriter, times(1)).write(eq(");".getBytes()));

    }
}