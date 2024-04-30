package Gestao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Integer idTicketProx = 1;
	private Integer idTicket;
	private LocalDateTime data;
	private LocalDate dataPrevista;
	private LocalDateTime dataFinal;
	private Cliente nomecliente;
	private String descricao;
	private String estado;
	private Float valor_mao;
	private Float valor_pecas;
	private Float valor_total;
	private ArrayList <String> notas;

	// ---------------------------------------- CONSTRUTOR
	
	public Ticket(Integer idTicket, Cliente nomecliente, LocalDateTime data, LocalDate dataPrevista, String descricao, String estado, Float valor_mao, Float valor_pecas, LocalDateTime dataFinal) {		
		super();
		this.idTicket = idTicketProx++;
		this.data = data;
		this.dataPrevista = dataPrevista;
		this.dataFinal = dataFinal;
		this.nomecliente = nomecliente;
		this.descricao = descricao;
		this.estado = estado;
		this.valor_mao = valor_mao;
		this.valor_pecas = valor_pecas;	
		this.notas = new ArrayList<>();
	}

	// ---------------------------------------- GETS E SETS
	
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(LocalDateTime localDateTime) {
		this.dataFinal = localDateTime;
	}


	public LocalDate getDataPrevista() {
		return dataPrevista;
	}


	public void setDataPrevista(LocalDate dataPrevista) {
		this.dataPrevista = dataPrevista;
	}


	public ArrayList<String> getNotas() {
		return notas;
	}

	public void setNotas(ArrayList<String> notas) {
		this.notas = notas;
	}

	public static Integer getIdTicketProx() {
		return idTicketProx;
	}

	public static void setIdTicketProx(Integer idTicketProx) {
		Ticket.idTicketProx = idTicketProx;
	}

	public Integer getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	public LocalDateTime getData() {
	    return data;
	}

	public void setData(LocalDateTime data) {
	    this.data = data;
	}

	public Cliente getcliente() {
		return nomecliente;
	}

	public void setcliente(Cliente cliente) {
		this.nomecliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Float getValor_mao() {
		return valor_mao;
	}

	public void setValor_mao(Float valor_mao) {
		this.valor_mao = valor_mao;
	}

	public Float getValor_pecas() {
		return valor_pecas;
	}

	public void setValor_pecas(Float valor_pecas) {
		this.valor_pecas = valor_pecas;
	}
	
	public Float getValor_total() {
		return valor_total;
	}

	public void setValor_total(Float valor_total) {
		this.valor_total = valor_total;
	}

	//Formatar as datas e horas no padrão "yyyy-MM-dd HH:mm:ss"
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	// ---------------------------------------- toSTRING
	
	@Override
    public String toString() {
        String dataCriacaoFormatada = data.format(formatter);
        String dataFinalFormatada = (dataFinal != null) ? dataFinal.format(formatter) : "Sem Data";
        
        return String.format("| %-9s | %-10s | %-19s | %-19s | %-16s | %-19s | %-10s | %-13s | %-26s |\n",
                idTicket, nomecliente.getIdCliente(), nomecliente.getNome(), dataCriacaoFormatada,
                dataPrevista, dataFinalFormatada, nomecliente.getTipo(), estado, descricao)
                + "+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------+";
    }
}
