package appnotifier.web.provider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.powermock.modules.junit4.PowerMockRunner;

import appnotifier.web.provider.annotation.JsonViewDefaultInclusion;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
public class DefaultViewInclusionHandlingProviderTestCase {

    private Annotation jsonView = new AnnotationInstance(JsonView.class);

    private Annotation jsonViewDefaultInclusion = new AnnotationInstance(JsonViewDefaultInclusion.class);

    @Spy
    @InjectMocks
    private DefaultViewInclusionHandlingProvider provider = new DefaultViewInclusionHandlingProvider();

    @org.mockito.Mock
    private ResteasyJackson2Provider providerMock;

    @Test
    public void shouldNotDetectDefaultViewAnnotationWithOnlyJsonView() {
        // GIVEN
        Annotation[] annotations = new Annotation[] { jsonView };
        // WHEN
        boolean retour = provider.containsDefaultViewAnnotation(annotations);
        // THEN
        assertThat(retour).isFalse();
    }

    @Test
    public void shouldNotDetectDefaultViewAnnotationWithOnlyJsonViewDefaultInclusion() {
        // GIVEN
        Annotation[] annotations = new Annotation[] { jsonViewDefaultInclusion };
        // WHEN
        boolean retour = provider.containsDefaultViewAnnotation(annotations);
        // THEN
        assertThat(retour).isFalse();
    }

    @Test
    public void shouldDetectDefaultViewAnnotationWithBothAnnotations() {
        // GIVEN
        Annotation[] annotations = new Annotation[] { jsonViewDefaultInclusion, jsonView };
        // WHEN
        boolean retour = provider.containsDefaultViewAnnotation(annotations);
        // THEN
        assertThat(retour).isTrue();
    }

    @Test
    public void shouldConfigureMapperWithBothAnnotations() {
        // GIVEN
        Annotation[] annotations = new Annotation[] { jsonViewDefaultInclusion, jsonView };
        // WHEN
        ObjectMapper mapper = provider.configureMapperForDefaultView(String.class, annotations, MediaType.APPLICATION_JSON_TYPE);
        // THEN
        assertThat(mapper.getSerializationConfig().isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)).isTrue();
    }

    @Test
    public void shouldNotConfigureMappernWithOnlyJsonView() {
        // GIVEN
        Annotation[] annotations = new Annotation[] { jsonView };
        // WHEN
        ObjectMapper mapper = provider.configureMapperForDefaultView(String.class, annotations, MediaType.APPLICATION_JSON_TYPE);
        // THEN
        assertThat(mapper.getSerializationConfig().isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION)).isFalse();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldCallMapperConfigurationBeforeWriting() throws IOException {
        // GIVEN
        Class<?> clazz = String.class;
        Annotation[] annotations = new Annotation[] { jsonView };
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        // WHEN
        provider.writeTo(mock(Object.class), clazz, mock(Type.class), annotations, mediaType, mock(MultivaluedMap.class), mock(OutputStream.class));
        // THEN
        verify(provider, times(1)).configureMapperForDefaultView(eq(clazz), eq(annotations), eq(mediaType));
    }

    @Test
    public void shouldCallDelegateIsReadable() throws IOException {
        // GIVEN
        Class<?> clazz = String.class;
        Annotation[] annotations = new Annotation[] { jsonView };
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Type typeMock = mock(Type.class);
        // WHEN
        provider.isReadable(clazz, typeMock, annotations, mediaType);
        // THEN
        verify(providerMock, times(1)).isReadable(eq(clazz), eq(typeMock), eq(annotations), eq(mediaType));
    }

    @Test
    public void shouldCallDelegateIsWritable() throws IOException {
        // GIVEN
        Class<?> clazz = String.class;
        Annotation[] annotations = new Annotation[] { jsonView };
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        Type typeMock = mock(Type.class);
        // WHEN
        provider.isWriteable(clazz, typeMock, annotations, mediaType);
        // THEN
        verify(providerMock, times(1)).isWriteable(eq(clazz), eq(typeMock), eq(annotations), eq(mediaType));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void shouldCallDelegateWriteTo() throws IOException {
        // GIVEN
        Class<?> clazz = String.class;
        Annotation[] annotations = new Annotation[] { jsonView };
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        // WHEN
        Object valueMock = mock(Object.class);
        Type typeMock = mock(Type.class);
        MultivaluedMap mapMock = mock(MultivaluedMap.class);
        OutputStream outputStreamMock = mock(OutputStream.class);
        provider.writeTo(valueMock, clazz, typeMock, annotations, mediaType, mapMock, outputStreamMock);
        // THEN
        verify(providerMock, times(1)).writeTo(eq(valueMock), eq(clazz), eq(typeMock), eq(annotations), eq(mediaType), eq(mapMock), eq(outputStreamMock));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void shouldCallDelegateReadFrom() throws IOException {
        // GIVEN
        Class<Object> clazz = Object.class;
        Annotation[] annotations = new Annotation[] { jsonView };
        MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
        // WHEN
        Type typeMock = mock(Type.class);
        MultivaluedMap mapMock = mock(MultivaluedMap.class);
        InputStream inputStreamMock = mock(InputStream.class);
        provider.readFrom(clazz, typeMock, annotations, mediaType, mapMock, inputStreamMock);
        // THEN
        verify(providerMock, times(1)).readFrom(eq(clazz), eq(typeMock), eq(annotations), eq(mediaType), eq(mapMock), eq(inputStreamMock));
    }
}
