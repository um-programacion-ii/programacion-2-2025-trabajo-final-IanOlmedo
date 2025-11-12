package ar.edu.um.gestioneventos.domain;

import static ar.edu.um.gestioneventos.domain.SesionUsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import ar.edu.um.gestioneventos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SesionUsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SesionUsuario.class);
        SesionUsuario sesionUsuario1 = getSesionUsuarioSample1();
        SesionUsuario sesionUsuario2 = new SesionUsuario();
        assertThat(sesionUsuario1).isNotEqualTo(sesionUsuario2);

        sesionUsuario2.setId(sesionUsuario1.getId());
        assertThat(sesionUsuario1).isEqualTo(sesionUsuario2);

        sesionUsuario2 = getSesionUsuarioSample2();
        assertThat(sesionUsuario1).isNotEqualTo(sesionUsuario2);
    }
}
