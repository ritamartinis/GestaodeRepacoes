package Gestao;

public class clienteRevendedor extends Cliente {
	private static final long serialVersionUID = 1L;
	private String NIPC;
	private Float Desconto_Mao = 0f;
	private Float Desconto_Total = 0f;
	
	// ---------------------------------------- CONSTRUTOR
	
	public clienteRevendedor(String nome, String endereco, String NIPC, String tipo) {
		super(nome, endereco, tipo);
		this.NIPC = NIPC;
	}

	// ---------------------------------------- GETS E SETS
	
	public String getNIPC() {
		return NIPC;
	}


	public void setNIPC(String NIPC) {
		this.NIPC = NIPC;
	}

	public void setDesconto_Mao(Float desconto_Mao) {
		Desconto_Mao = desconto_Mao;
	}

	public void setDesconto_Total(Float desconto_Total) {
		this.Desconto_Total = desconto_Total;
	}

	// ---------------------------------------- toSTRING
	@Override	
	public String toString() {
	    String parentString = super.toString();
	    return parentString.substring(0, parentString.lastIndexOf("|") - 1) + String.format(" | NIPC: %-10s", NIPC) + "|" +
	    		"\n+-------------------------------------------------------------------------------------------------------------------------+";
	    
	}
	
	// ---------------------------------------- MÉTODOS ABSTRATOS
	
	@Override
    public Float getDesconto_Mao() {
        return Desconto_Mao;
    }

    @Override
    public Float getDesconto_Total() {
        return Desconto_Total;
    }	
}
