package Gestao;

import java.io.*;
import java.util.ArrayList;

public class BackupMetodo {

	public static void gravaClientes(ArrayList<Cliente> clientes, String save) {
		File f = new File(save);
		try {
			f.createNewFile(); // Criar novo ficheiro
			ObjectOutputStream ficheiro = new ObjectOutputStream(new FileOutputStream(save));
			ficheiro.writeObject(clientes); // Escrever o arrayList no ficheiro
			System.out.println("[GUARDADO]");
			ficheiro.flush();
			ficheiro.close();
		} catch (IOException e) {
			e.printStackTrace(); // Se a operação der erro mostra o erro...
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Cliente> lerClientes(String save) {

		ArrayList<Cliente> clientes = new ArrayList<>();
		File ficheiro = new File(save);
		if (!ficheiro.exists())
			ficheiro = new File(save);
		else {
			ObjectInputStream f;
			try {
				f = new ObjectInputStream(new FileInputStream(save));
				clientes = (ArrayList<Cliente>) f.readObject();
				System.out.println("Dados carregados com sucesso.");
			} catch (IOException e) {
				System.out.println("Ficheiro: " + ficheiro.getAbsolutePath());
				e.printStackTrace(); // Se a operação der erro mostra o erro...
			} catch (ClassNotFoundException e) {
				System.out.println("Ficheiro: " + ficheiro.getAbsolutePath());
				e.printStackTrace(); // Se a operação der erro mostra o erro...
			}

		}
		return clientes;
	}
}
