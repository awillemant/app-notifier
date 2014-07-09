package appnotifier.web.servlet;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AppNotifierServletTestCase {

    private HttpServletRequest request = mock(HttpServletRequest.class);

    private HttpServletResponse response = mock(HttpServletResponse.class);

    private AppNotifierServlet servlet = new AppNotifierServlet();

    private PrintWriter printWriter = mock(PrintWriter.class);

    @Before
    public void init() throws IOException {
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void shouldInitServlet() {
        // GIVEN
        // WHEN
        try {
            servlet.init();
        } catch (ServletException e) {
            fail(getStackTrace(e));
        }
        // THEN
        assertThat(servlet).isNotNull();
    }

    @Test
    public void shouldReturnModifiedJS() throws IOException {
        // GIVEN
        when(request.getScheme()).thenReturn("http");
        when(request.getServerName()).thenReturn("myserver");
        when(request.getServerPort()).thenReturn(1234);
        when(request.getContextPath()).thenReturn("/myContext");
        String expectedJS = getExpectedJS();
        // WHEN
        try {

            servlet.doGet(request, response);
        } catch (ServletException e) {
            fail(getStackTrace(e));
        }
        // THEN
        verify(response, times(1)).setContentType(eq("application/javascript"));
        verify(printWriter, times(1)).write(eq(expectedJS));
    }

    private String getExpectedJS() throws IOException {
        InputStream templateResource = this.getClass().getClassLoader().getResourceAsStream("appnotifierTemplate_expected.js");
        String template = IOUtils.toString(templateResource);
        templateResource.close();
        return template;
    }

}
