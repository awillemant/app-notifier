package appnotifier.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

@Controller
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/rs/properties")
public class PropertiesWS {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesWS.class);

	@Value("${templatePath:classpath:configuration.properties}")
	private Resource configurationResource;

	private Properties configurationProperties;


	@GET
	@Path("/urlRoot")
	public String getUrlRoot() {
		LOGGER.info("getting root URL");
		return configurationProperties.getProperty("rootUrl");
	}


	@PostConstruct
	public void init() throws IOException {
		LOGGER.debug("Initializing PropertyWS");
		InputStream configurationStream = configurationResource.getInputStream();
		configurationProperties = new Properties();
		configurationProperties.load(configurationStream);
		configurationStream.close();
	}
}