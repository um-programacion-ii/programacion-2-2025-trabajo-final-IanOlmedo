package ar.edu.um.gestioneventos.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AsientoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Asiento getAsientoSample1() {
        return new Asiento().id(1L).fila("fila1").numero(1);
    }

    public static Asiento getAsientoSample2() {
        return new Asiento().id(2L).fila("fila2").numero(2);
    }

    public static Asiento getAsientoRandomSampleGenerator() {
        return new Asiento().id(longCount.incrementAndGet()).fila(UUID.randomUUID().toString()).numero(intCount.incrementAndGet());
    }
}
