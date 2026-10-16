package co.edu.poli.Parcial_2_Practica.modelo;

/**
 * Estados en los que puede encontrarse una tarjeta (HU-01).
 * <ul>
 * <li>{@link #ACTIVA}: la tarjeta puede ser utilizada.</li>
 * <li>{@link #BLOQUEADA}: la tarjeta no puede ser utilizada temporalmente.</li>
 * <li>{@link #CANCELADA}: la tarjeta fue dada de baja.</li>
 * </ul>
 * El nombre del enum ({@link #name()}) es el valor que se persiste en la base
 * de datos; {@link #toString()} devuelve la etiqueta que se muestra en la
 * interfaz.
 */
public enum EstadoTarjeta {

	ACTIVA("Activa"), BLOQUEADA("Bloqueada"), CANCELADA("Cancelada");

	private final String etiqueta;

	EstadoTarjeta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return etiqueta legible para mostrar en la interfaz.
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * Convierte el valor almacenado en la base de datos en un estado.
	 *
	 * @param valor texto almacenado (ej. "ACTIVA"), sin distinguir mayúsculas.
	 * @return el estado correspondiente.
	 * @throws IllegalArgumentException si el valor es nulo, vacío o no corresponde
	 *                                  a un estado válido.
	 */
	public static EstadoTarjeta desdeBD(String valor) {
		if (valor == null || valor.isBlank()) {
			throw new IllegalArgumentException("Estado de tarjeta inválido: " + valor);
		}
		try {
			return EstadoTarjeta.valueOf(valor.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Estado de tarjeta inválido: " + valor);
		}
	}

	@Override
	public String toString() {
		return etiqueta;
	}
}