package ar.edu.um.gestioneventos.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EventoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Evento getEventoSample1() {
        return new Evento().id(1L).titulo("titulo1").resumen("resumen1");
    }

    public static Evento getEventoSample2() {
        return new Evento().id(2L).titulo("titulo2").resumen("resumen2");
    }

    public static Evento getEventoRandomSampleGenerator() {
        return new Evento().id(longCount.incrementAndGet()).titulo(UUID.randomUUID().toString()).resumen(UUID.randomUUID().toString());
    }
}
