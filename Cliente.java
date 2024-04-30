package Gestao;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Cliente  implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Integer idClienteProx = 1;
	private Integer idCliente;
	private String nome;
	private String endereco;
	private String tipo;
	private ArrayList <Ticket> tickets;
	
	// ---------------------------------------- CONSTRUTOR
	
	public Cliente(String nome, String endereco, String tipo) {			//o cliente tem de ser obrigatoriamente criado com um nome
		super();
		this.idCliente = idClienteProx++;
		this.nome = nome;
		this.endereco = endereco;
		this.tipo = tipo;
		this.tickets = new ArrayList<>();		//quando crio um ticket ele é associado ao cliente
	}
	
	// ---------------------------------------- GETS E SETS
	
	public Integer getIdCliente() {
		return idCliente;
	}
	
	public static Integer getIdClienteProx() {
		return idClienteProx;
	}

	public static void setIdClienteProx(Integer idClienteProx) {
		Cliente.idClienteProx = idClienteProx;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	// ---------------------------------------- toSTRING
	@Override
	public String toString() {
	    return String.format(
	            "| %-8s | %-19s | %-50s | %-15s |\n", idCliente, nome, endereco, tipo);
	}
	// ---------------------------------------- MÉTODOS ABSTRATOS

	public abstract Float getDesconto_Total();	
	
	public abstract Float getDesconto_Mao();
		
}
