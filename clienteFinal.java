package Gestao;

public class clienteFinal extends Cliente {
	private static final long serialVersionUID = 1L;
	private String NIF;
	private Float Desconto_Mao = 0.03f;
	
	// ---------------------------------------- CONSTRUTOR
	
	public clienteFinal(String nome, String endereco, String NIF, String tipo) {
		super(nome, endereco, tipo);
		this.NIF = NIF;	
	}

	// ---------------------------------------- GETS E SETS
	
	public String getNIF() {
		return NIF;
	}

	public void setNIF(String NIF) {
		this.NIF = NIF;
	}
	
	public void setDesconto_Mao(Float desconto_Mao) {
		this.Desconto_Mao = desconto_Mao;
	}
	
		// ---------------------------------------- toSTRING
		@Override	
		public String toString() {
		    String parentString = super.toString();
		    return parentString.substring(0, parentString.lastIndexOf("|") - 1) + String.format(" | NIF: %-11s", NIF) + "|" +
		    		"\n+-------------------------------------------------------------------------------------------------------------------------+";
		    
		}
		
	// ---------------------------------------- MÉTODOS ABSTRATOS
	@Override
	public Float getDesconto_Total() {
		return 0f;	
		//é preferível aparecer uma mensagem em texto
	}

	@Override
	public Float getDesconto_Mao() {
		return Desconto_Mao;
	}	
}
	
