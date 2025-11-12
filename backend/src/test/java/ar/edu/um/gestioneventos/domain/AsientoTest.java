package ar.edu.um.gestioneventos.domain;

import static ar.edu.um.gestioneventos.domain.AsientoTestSamples.*;
import static ar.edu.um.gestioneventos.domain.EventoTestSamples.*;
import static ar.edu.um.gestioneventos.domain.VentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.gestioneventos.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AsientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asiento.class);
        Asiento asiento1 = getAsientoSample1();
        Asiento asiento2 = new Asiento();
        assertThat(asiento1).isNotEqualTo(asiento2);

        asiento2.setId(asiento1.getId());
        assertThat(asiento1).isEqualTo(asiento2);

        asiento2 = getAsientoSample2();
        assertThat(asiento1).isNotEqualTo(asiento2);
    }

    @Test
    void evento_con_asientosTest() {
        Asiento asiento = getAsientoRandomSampleGenerator();
        Evento eventoBack = getEventoRandomSampleGenerator();

        asiento.setEvento_con_asientos(eventoBack);
        assertThat(asiento.getEvento_con_asientos()).isEqualTo(eventoBack);

        asiento.evento_con_asientos(null);
        assertThat(asiento.getEvento_con_asientos()).isNull();
    }

    @Test
    void nTest() {
        Asiento asiento = getAsientoRandomSampleGenerator();
        Venta ventaBack = getVentaRandomSampleGenerator();

        asiento.addN(ventaBack);
        assertThat(asiento.getNs()).containsOnly(ventaBack);
        assertThat(ventaBack.getAsientos()).containsOnly(asiento);

        asiento.removeN(ventaBack);
        assertThat(asiento.getNs()).doesNotContain(ventaBack);
        assertThat(ventaBack.getAsientos()).doesNotContain(asiento);

        asiento.ns(new HashSet<>(Set.of(ventaBack)));
        assertThat(asiento.getNs()).containsOnly(ventaBack);
        assertThat(ventaBack.getAsientos()).containsOnly(asiento);

        asiento.setNs(new HashSet<>());
        assertThat(asiento.getNs()).doesNotContain(ventaBack);
        assertThat(ventaBack.getAsientos()).doesNotContain(asiento);
    }
}
