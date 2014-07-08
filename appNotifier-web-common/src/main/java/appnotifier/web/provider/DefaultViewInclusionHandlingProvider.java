package appnotifier.web.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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

/**
 * 
 * Ce provider basé sur ResteasyJacksonProvider permet de détecter si
 * l'annotation 'JsonViewDefaultInclusion' est présente ou pas sur la méthode
 * qui gère la requête REST
 * 
 * Dans le cas où l'annotation est présente, tous les attributs par défaut (du
 * mapping JSON sur l'entité) seront affichés
 * 
 * @author a148744
 * 
 */
@Provider
public class DefaultViewInclusionHandlingProvider extends ResteasyJackson2Provider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultViewInclusionHandlingProvider.class);

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        configureMapperForDefaultView(type, annotations, mediaType);
        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    private void configureMapperForDefaultView(Class<?> type, Annotation[] annotations, MediaType mediaType) {
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
    }

    private boolean containsDefaultViewAnnotation(Annotation[] annotations) {
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
