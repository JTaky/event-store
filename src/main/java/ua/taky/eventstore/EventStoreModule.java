package ua.taky.eventstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.name.Names;

public class EventStoreModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventStoreModule.class);

    private static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";	

	@Override
	protected void configure() {
		bind(HttpServer.class);
        // application properties
        loadApplicationProperties(binder());
        LOGGER.trace(getClass().getName() + " was configured.");		
	}
	
    private void loadApplicationProperties(Binder binder) {
        try {
            InputStream stream = null;
            try {
                stream = getApplicationPropertiesInputStream();
                Properties appProperties = new Properties();
                appProperties.load(stream);
                Names.bindProperties(binder, appProperties);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        } catch (IOException e) {
            // This is the preferred way to tell Guice something went wrong
            binder.addError(e);
        }
    }

    private InputStream getApplicationPropertiesInputStream() {
        File applicationPropertiesFile = findApplicationPropertiesFile();
        InputStream stream;
        if (applicationPropertiesFile == null) {
            stream = getClass().getResourceAsStream("/" + APPLICATION_PROPERTIES_FILE_NAME);
        }
        else {
            try {
                stream = new FileInputStream(applicationPropertiesFile);
            } catch (FileNotFoundException e) {
                LOGGER.error("Cannot create steam for application.properties file.", e);
                stream = getClass().getResourceAsStream("/" + APPLICATION_PROPERTIES_FILE_NAME);
            }
        }
        return stream;
    }

    private File findApplicationPropertiesFile() {
        File file = new File(APPLICATION_PROPERTIES_FILE_NAME);
        if (file.exists()) {
            return file;
        }
        else {
            return null;
        }
    }	

}
