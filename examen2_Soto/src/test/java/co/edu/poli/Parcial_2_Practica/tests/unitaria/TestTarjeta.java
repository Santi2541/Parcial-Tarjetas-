package co.edu.poli.Parcial_2_Practica.tests.unitaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import co.edu.poli.Parcial_2_Practica.modelo.Credito;
import co.edu.poli.Parcial_2_Practica.modelo.Debito;
import co.edu.poli.Parcial_2_Practica.modelo.EstadoTarjeta;
import co.edu.poli.Parcial_2_Practica.modelo.Tarjeta;
import co.edu.poli.Parcial_2_Practica.modelo.Titular;

/**
 * Pruebas unitarias del modelo {@link Tarjeta} y de la HU-01 (estado de la
 * tarjeta).
 */
public class TestTarjeta {

	private final Titular titular = new Titular("1", "Test");

	/**
	 * Verifica que la acción lance {@link IllegalArgumentException}.
	 *
	 * @param accion código a ejecutar.
	 */
	private static void assertLanzaIllegalArgument(Runnable accion) {
		try {
			accion.run();
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("Se esperaba IllegalArgumentException");
	}

	@Test
	void bloquear_cambiaEstadoABloqueada() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 1000);

		String mensaje = t.bloquear();

		assertEquals(EstadoTarjeta.BLOQUEADA, t.getEstado());
		assertTrue(mensaje.contains("BLOQUEADA"));
	}

	@Test
	void activar_cambiaEstadoAActiva() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.BLOQUEADA, titular, 1000);

		String mensaje = t.activar();

		assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());
		assertTrue(mensaje.contains("ACTIVADA"));
	}

	@Test
	void cancelar_cambiaEstadoACancelada() {
		Tarjeta t = new Credito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 5000);

		String mensaje = t.cancelar();

		assertEquals(EstadoTarjeta.CANCELADA, t.getEstado());
		assertTrue(mensaje.contains("CANCELADA"));
	}

	@Test
	void constructor_conEstadoNulo_lanzaExcepcion() {
		assertLanzaIllegalArgument(() -> new Debito("123", "25/12/2025", null, titular, 1000));
		assertLanzaIllegalArgument(() -> new Credito("123", "25/12/2025", null, titular, 1000));
	}

	@Test
	void setEstado_conNulo_lanzaExcepcionYConservaEstado() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 1000);

		assertLanzaIllegalArgument(() -> t.setEstado(null));

		assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());
	}

	@Test
	void setEstado_asignaCadaEstadoValido() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 1000);

		for (EstadoTarjeta e : EstadoTarjeta.values()) {
			t.setEstado(e);
			assertEquals(e, t.getEstado());
		}
	}

	@Test
	void puedeUtilizarse_soloCuandoEstaActiva() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 1000);
		assertTrue(t.puedeUtilizarse());

		t.setEstado(EstadoTarjeta.BLOQUEADA);
		assertFalse(t.puedeUtilizarse());

		t.setEstado(EstadoTarjeta.CANCELADA);
		assertFalse(t.puedeUtilizarse());
	}

	@Test
	void estadoTarjeta_tieneExactamenteTresEstados() {
		assertEquals(3, EstadoTarjeta.values().length);
		assertNotNull(EstadoTarjeta.valueOf("ACTIVA"));
		assertNotNull(EstadoTarjeta.valueOf("BLOQUEADA"));
		assertNotNull(EstadoTarjeta.valueOf("CANCELADA"));
	}

	@Test
	void desdeBD_convierteTextoValido() {
		assertEquals(EstadoTarjeta.ACTIVA, EstadoTarjeta.desdeBD("ACTIVA"));
		assertEquals(EstadoTarjeta.BLOQUEADA, EstadoTarjeta.desdeBD("bloqueada"));
		assertEquals(EstadoTarjeta.CANCELADA, EstadoTarjeta.desdeBD(" CANCELADA "));
	}

	@Test
	void desdeBD_conTextoInvalido_lanzaExcepcion() {
		assertLanzaIllegalArgument(() -> EstadoTarjeta.desdeBD("XYZ"));
		assertLanzaIllegalArgument(() -> EstadoTarjeta.desdeBD(""));
		assertLanzaIllegalArgument(() -> EstadoTarjeta.desdeBD(null));
	}

	@Test
	void getters_retornaValoresCorrectos() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 1000);

		assertEquals("123", t.getNumero());
		assertEquals("25/12/2025", t.getFechaExp());
		assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());
		assertEquals(titular, t.getTitular());
	}

	@Test
	void setters_modificanValores() {
		Titular nuevo = new Titular("2", "Nuevo");
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 1000);

		t.setNumero("999");
		t.setFechaExp("01/01/2030");
		t.setEstado(EstadoTarjeta.CANCELADA);
		t.setTitular(nuevo);

		assertEquals("999", t.getNumero());
		assertEquals("01/01/2030", t.getFechaExp());
		assertEquals(EstadoTarjeta.CANCELADA, t.getEstado());
		assertEquals(nuevo, t.getTitular());
	}

	@Test
	void toString_contieneDatosYEstado() {
		Tarjeta t = new Debito("123", "25/12/2025", EstadoTarjeta.BLOQUEADA, titular, 1000);

		String texto = t.toString();

		assertTrue(texto.contains("123"));
		assertTrue(texto.contains("25/12/2025"));
		assertTrue(texto.contains("Bloqueada"));
	}
}