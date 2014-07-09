package appnotifier.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.mock.MockHttpRequest;
import org.junit.Test;

import appnotifier.core.entity.Notification;
import appnotifier.web.test.AbstractControllerJUnit4SpringContextTests;
import appnotifier.web.test.MockHttpResponseWrapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class PublicWSTestCase extends AbstractControllerJUnit4SpringContextTests<PublicWS> {

    @Override
    protected Class<PublicWS> getControllerType() {
        return PublicWS.class;
    }

    @Test
    public void shouldListAllNotifications() throws URISyntaxException, JsonParseException, JsonMappingException, IOException {
        // GIVEN
        MockHttpRequest request = MockHttpRequest.get("/rs/public/42");
        request.contentType(MediaType.APPLICATION_JSON);
        // WHEN
        MockHttpResponseWrapper<Notification> retour = executeRequestAndMapItToList(request, Notification.class);
        // THEN
        assertThat(retour.getResponse().getStatus()).isEqualTo(200);
        assertThat(retour.getReturnedParsedContentList()).hasSize(2);
        assertThat(retour.getReturnedParsedContentList()).extracting("message").containsOnly("Message1", "Message2");
    }
}
