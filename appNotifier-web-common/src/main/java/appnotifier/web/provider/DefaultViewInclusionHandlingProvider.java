package appnotifier.web.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import appnotifier.web.provider.annotation.JsonViewDefaultInclusion;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
@Consumes({ "application/*+json", "text/json" })
@Produces({ "application/*+json", "text/json" })
public class DefaultViewInclusionHandlingProvider extends JacksonJaxbJsonProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultViewInclusionHandlingProvider.class);

    private ResteasyJackson2Provider delegateProvider;

    public DefaultViewInclusionHandlingProvider() {
        super();
        this.delegateProvider = new ResteasyJackson2Provider();
    }

    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return delegateProvider.isReadable(aClass, type, annotations, mediaType);
    }

    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return delegateProvider.isWriteable(aClass, type, annotations, mediaType);
    }

    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException {
        return delegateProvider.readFrom(type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        configureMapperForDefaultView(type, annotations, mediaType);
        delegateProvider.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    protected ObjectMapper configureMapperForDefaultView(Class<?> type, Annotation[] annotations, MediaType mediaType) {
        LOGGER.debug("detection de la default view inclusion...");
        ObjectMapper mapper = locateMapper(type, mediaType);
        boolean defaultView = false;
        if (containsDefaultViewAnnotation(annotations)) {
            LOGGER.debug("default view inclusion detectee !");
            defaultView = true;
        } else {
            LOGGER.debug("default view non detectee");
        }
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, defaultView);
        setMapper(mapper);
        return mapper;
    }

    protected boolean containsDefaultViewAnnotation(Annotation[] annotations) {
        boolean hasJsonView = false;
        boolean hasJsonViewDefaultInclusion = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(JsonViewDefaultInclusion.class)) {
                hasJsonViewDefaultInclusion = true;
            }
            if (annotation.annotationType().equals(JsonView.class)) {
                hasJsonView = true;
            }
        }
        return hasJsonView && hasJsonViewDefaultInclusion;
    }
}