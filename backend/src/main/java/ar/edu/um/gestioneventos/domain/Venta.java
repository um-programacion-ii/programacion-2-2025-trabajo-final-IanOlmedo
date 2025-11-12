package ar.edu.um.gestioneventos.domain;

import ar.edu.um.gestioneventos.domain.enumeration.EstadoVenta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Venta.
 */
@Entity
@Table(name = "venta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_venta", nullable = false)
    private Instant fechaVenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoVenta estado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Evento evento;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_venta__asiento",
        joinColumns = @JoinColumn(name = "venta_id"),
        inverseJoinColumns = @JoinColumn(name = "asiento_id")
    )
    @JsonIgnoreProperties(value = { "evento_con_asientos", "ns" }, allowSetters = true)
    private Set<Asiento> asientos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Venta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaVenta() {
        return this.fechaVenta;
    }

    public Venta fechaVenta(Instant fechaVenta) {
        this.setFechaVenta(fechaVenta);
        return this;
    }

    public void setFechaVenta(Instant fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public EstadoVenta getEstado() {
        return this.estado;
    }

    public Venta estado(EstadoVenta estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public Evento getEvento() {
        return this.evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Venta evento(Evento evento) {
        this.setEvento(evento);
        return this;
    }

    public Set<Asiento> getAsientos() {
        return this.asientos;
    }

    public void setAsientos(Set<Asiento> asientos) {
        this.asientos = asientos;
    }

    public Venta asientos(Set<Asiento> asientos) {
        this.setAsientos(asientos);
        return this;
    }

    public Venta addAsiento(Asiento asiento) {
        this.asientos.add(asiento);
        return this;
    }

    public Venta removeAsiento(Asiento asiento) {
        this.asientos.remove(asiento);
        return this;
    }

    public User getUsuario() {
        return this.usuario;
    }

    public void setUsuario(User user) {
        this.usuario = user;
    }

    public Venta usuario(User user) {
        this.setUsuario(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venta)) {
            return false;
        }
        return getId() != null && getId().equals(((Venta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venta{" +
            "id=" + getId() +
            ", fechaVenta='" + getFechaVenta() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
