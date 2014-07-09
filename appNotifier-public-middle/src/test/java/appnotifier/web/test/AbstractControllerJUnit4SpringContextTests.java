package appnotifier.web.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.spring.SpringResourceFactory;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(locations = { "classpath*:META-INF/applicationContext-test*.xml", })
public abstract class AbstractControllerJUnit4SpringContextTests<T> extends AbstractJUnit4SpringContextTests {

    private Dispatcher dispatcher;

    private Class<T> controllerType;

    private ObjectMapper mapper;

    protected abstract Class<T> getControllerType();

    @Before
    public void init() {
        controllerType = getControllerType();
        dispatcher = MockDispatcherFactory.createDispatcher();
        addControllerClassToRegistry();
        mapper = new ObjectMapper();
    }

    private void addControllerClassToRegistry() {
        Map<String, T> bean = applicationContext.getBeansOfType(controllerType);
        for (Entry<String, T> entry : bean.entrySet()) {
            SpringResourceFactory controller = new SpringResourceFactory(entry.getKey(), applicationContext, controllerType);
            dispatcher.getRegistry().addResourceFactory(controller);
        }
    }

    protected MockHttpResponse executeRequest(MockHttpRequest request) {
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        return response;
    }

    protected <E> MockHttpResponseWrapper<E> executeRequestAndMapItToList(MockHttpRequest request, Class<E> targetClass) throws JsonParseException, JsonMappingException, IOException {
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, targetClass);
        List<E> values = mapper.readValue(response.getContentAsString(), type);
        return new MockHttpResponseWrapper<E>(null, values, response);
    }
}
