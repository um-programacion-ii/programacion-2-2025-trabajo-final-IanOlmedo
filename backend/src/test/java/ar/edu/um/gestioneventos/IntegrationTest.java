package ar.edu.um.gestioneventos;

import ar.edu.um.gestioneventos.config.AsyncSyncConfiguration;
import ar.edu.um.gestioneventos.config.EmbeddedKafka;
import ar.edu.um.gestioneventos.config.EmbeddedRedis;
import ar.edu.um.gestioneventos.config.EmbeddedSQL;
import ar.edu.um.gestioneventos.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { GestionEventosApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
@EmbeddedKafka
public @interface IntegrationTest {
}
