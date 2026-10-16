package co.edu.poli.Parcial_2_Practica.tests.integracion;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.Parcial_2_Practica.modelo.Credito;
import co.edu.poli.Parcial_2_Practica.modelo.Debito;
import co.edu.poli.Parcial_2_Practica.modelo.EstadoTarjeta;
import co.edu.poli.Parcial_2_Practica.modelo.Tarjeta;
import co.edu.poli.Parcial_2_Practica.modelo.Titular;
import co.edu.poli.Parcial_2_Practica.servicios.ConexionBD;
import co.edu.poli.Parcial_2_Practica.servicios.DAOTarjeta;

/**
 * Pruebas de integración de {@link DAOTarjeta}. Requieren la BD creada con
 * ScriptDB.sql y el archivo .env configurado. Limpian sus propios datos.
 */
public class TestDAOTarjeta {

	private static final String[] NUMEROS = { "999001", "999002", "999003", "999004", "999005" };

	DAOTarjeta dao = new DAOTarjeta();

	@BeforeEach
	@AfterEach
	void limpiar() throws Exception {
		Connection con = ConexionBD.getInstancia().getConexion();
		for (String n : NUMEROS) {
			try (PreparedStatement ps = con.prepareStatement("DELETE FROM tarjeta WHERE numero = ?")) {
				ps.setString(1, n);
				ps.executeUpdate();
			}
		}
	}

	@Test
	void create_debito_y_readone() throws Exception {
		Titular titular = new Titular("T001", "Test");
		Debito debito = new Debito("999001", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 5000.0);

		String result = dao.create(debito);

		assertTrue(result.contains("guardada"));

		Tarjeta t = dao.readone("999001");

		assertNotNull(t);
		assertTrue(t instanceof Debito);
		assertEquals(5000.0, ((Debito) t).getSaldo());
		assertEquals(EstadoTarjeta.ACTIVA, t.getEstado());
	}

	@Test
	void create_credito_y_readone() throws Exception {
		Titular titular = new Titular("T001", "Test");
		Credito credito = new Credito("999002", "25/12/2025", EstadoTarjeta.ACTIVA, titular, 10000.0);

		String result = dao.create(credito);

		assertTrue(result.contains("guardada"));

		Tarjeta t = dao.readone("999002");

		assertNotNull(t);
		assertTrue(t instanceof Credito);
		assertEquals(10000.0, ((Credito) t).getLimite());
	}

	@Test
	void estado_sePersisteYSeRecuperaParaCadaValor() throws Exception {
		Titular titular = new Titular("T001", "Test");
		EstadoTarjeta[] estados = EstadoTarjeta.values();

		for (int i = 0; i < estados.length; i++) {
			String numero = NUMEROS[2 + i];
			dao.create(new Debito(numero, "25/12/2025", estados[i], titular, 100.0));

			Tarjeta leida = dao.readone(numero);

			assertNotNull(leida);
			assertEquals(estados[i], leida.getEstado());
		}
	}

	@Test
	void readone_noExiste() throws Exception {
		assertNull(dao.readone("000000"));
	}
}