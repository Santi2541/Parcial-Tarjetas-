package co.edu.poli.Parcial_2_Practica.modelo;

/**
 * Tarjeta abstracta de la jerarquía (Débito / Crédito).
 * Incluye el {@link EstadoTarjeta} que controla si puede ser utilizada (HU-01).
 */
public abstract class Tarjeta {

	private String numero;

	private String fechaExp;

	private EstadoTarjeta estado;

	private Titular titular;

	/**
	 * @param numero   número de la tarjeta.
	 * @param fechaExp fecha de expedición.
	 * @param estado   estado de la tarjeta (obligatorio).
	 * @param titular  titular de la tarjeta.
	 * @throws IllegalArgumentException si el estado es nulo.
	 */
	public Tarjeta(String numero, String fechaExp, EstadoTarjeta estado, Titular titular) {
		this.numero = numero;
		this.fechaExp = fechaExp;
		setEstado(estado);
		this.titular = titular;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(String fechaExp) {
		this.fechaExp = fechaExp;
	}

	/**
	 * @return el estado actual de la tarjeta (nunca nulo).
	 */
	public EstadoTarjeta getEstado() {
		return estado;
	}

	/**
	 * Asigna el estado de la tarjeta.
	 *
	 * @param estado nuevo estado.
	 * @throws IllegalArgumentException si el estado es nulo (no se permite una
	 *                                  tarjeta sin estado válido).
	 */
	public void setEstado(EstadoTarjeta estado) {
		if (estado == null) {
			throw new IllegalArgumentException("El estado de la tarjeta es obligatorio");
		}
		this.estado = estado;
	}

	/**
	 * @return {@code true} si la tarjeta está activa y puede ser utilizada.
	 */
	public boolean puedeUtilizarse() {
		return estado == EstadoTarjeta.ACTIVA;
	}

	public Titular getTitular() {
		return titular;
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	/**
	 * Cambia el estado a {@link EstadoTarjeta#BLOQUEADA}.
	 *
	 * @return mensaje de confirmación.
	 */
	public String bloquear() {
		this.estado = EstadoTarjeta.BLOQUEADA;
		return "Tarjeta " + numero + " BLOQUEADA.";
	}

	/**
	 * Cambia el estado a {@link EstadoTarjeta#ACTIVA}.
	 *
	 * @return mensaje de confirmación.
	 */
	public String activar() {
		this.estado = EstadoTarjeta.ACTIVA;
		return "Tarjeta " + numero + " ACTIVADA.";
	}

	/**
	 * Cambia el estado a {@link EstadoTarjeta#CANCELADA}.
	 *
	 * @return mensaje de confirmación.
	 */
	public String cancelar() {
		this.estado = EstadoTarjeta.CANCELADA;
		return "Tarjeta " + numero + " CANCELADA.";
	}

	@Override
	public String toString() {
		return "numero=" + numero + ", fechaExp=" + fechaExp + ", estado=" + estado + ", titular=" + titular;
	}
}