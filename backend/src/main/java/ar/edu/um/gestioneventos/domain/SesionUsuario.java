package ar.edu.um.gestioneventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A SesionUsuario.
 */
@Entity
@Table(name = "sesion_usuario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SesionUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "estado_flujo")
    private String estadoFlujo;

    @Column(name = "datos_temporales")
    private String datosTemporales;

    @NotNull
    @Column(name = "ultima_actualizacion", nullable = false)
    private Instant ultimaActualizacion;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SesionUsuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoFlujo() {
        return this.estadoFlujo;
    }

    public SesionUsuario estadoFlujo(String estadoFlujo) {
        this.setEstadoFlujo(estadoFlujo);
        return this;
    }

    public void setEstadoFlujo(String estadoFlujo) {
        this.estadoFlujo = estadoFlujo;
    }

    public String getDatosTemporales() {
        return this.datosTemporales;
    }

    public SesionUsuario datosTemporales(String datosTemporales) {
        this.setDatosTemporales(datosTemporales);
        return this;
    }

    public void setDatosTemporales(String datosTemporales) {
        this.datosTemporales = datosTemporales;
    }

    public Instant getUltimaActualizacion() {
        return this.ultimaActualizacion;
    }

    public SesionUsuario ultimaActualizacion(Instant ultimaActualizacion) {
        this.setUltimaActualizacion(ultimaActualizacion);
        return this;
    }

    public void setUltimaActualizacion(Instant ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SesionUsuario user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SesionUsuario)) {
            return false;
        }
        return getId() != null && getId().equals(((SesionUsuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SesionUsuario{" +
            "id=" + getId() +
            ", estadoFlujo='" + getEstadoFlujo() + "'" +
            ", datosTemporales='" + getDatosTemporales() + "'" +
            ", ultimaActualizacion='" + getUltimaActualizacion() + "'" +
            "}";
    }
}
