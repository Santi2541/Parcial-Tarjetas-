package co.edu.poli.Parcial_2_Practica.modelo;

public class Credito extends Tarjeta {

	private double limite;

	public Credito(String numero, String fechaExp, EstadoTarjeta estado, Titular titular, double limite) {
		super(numero, fechaExp, estado, titular);
		this.limite = limite;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	@Override
	public String toString() {
		return "Credito [" + super.toString() + ", limite=" + limite + "]";
	}
}