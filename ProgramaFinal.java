package Gestao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException; // linha 94
import java.util.ArrayList;
import java.util.InputMismatchException; // linha 196
import java.util.Scanner;

public class ProgramaFinal {

    private static ArrayList<Cliente> clientes = new ArrayList<>();
    final private static String save = "clientes_backup.rr";

    public static void main(String[] args) {
        clientes = BackupMetodo.lerClientes(save);
        if (clientes == null || clientes.size() == 0)
            clientes = new ArrayList<Cliente>();
        else {
            atualizarIdClienteProx(clientes);
            atualizarIdTicketProx(clientes);
        }
        int op;

        do {
            op = menuInicial();
            switch (op) {
            case 1:
                adicionarCliente();
                BackupMetodo.gravaClientes(clientes, save);
                break;
            case 2:
                consultarMenuCliente();
                break;
            case 3:
                editarCliente();
                BackupMetodo.gravaClientes(clientes, save);
                break;
            case 4:
                eliminarMenuCliente();
                BackupMetodo.gravaClientes(clientes, save);
                break;
            case 5:
                adicionarTicket();
                BackupMetodo.gravaClientes(clientes, save);
                break;
            case 6:
            	consultarMenuTickets();
                break;
            case 7:
                editarTicket();
                BackupMetodo.gravaClientes(clientes, save);
                break;
            case 8:
                eliminarTicketRegistado();
                BackupMetodo.gravaClientes(clientes, save);
                break;
            case 9:
				creditos();
				break;
            case 0:
            	BackupMetodo.gravaClientes(clientes, save);
                System.out.println("Obrigado por utilizar o nosso programa");
                break;
            }

        } while (op != 0);
    }
    
    //CLIENTE
    private static void adicionarCliente() {
    	
        cabecalho("Adicionar Cliente");
        String novoNome;
        
        do {
            System.out.println("Introduza o nome do cliente: ");
            novoNome = getString();
            if (novoNome.isEmpty()) {		//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
            	System.out.println("Erro! Opção inválida");
            }
        } while (novoNome.isEmpty());

        System.out.println("Introduza o endereço do cliente: ");
        String endereco = getString();

        int valueA = 0;
        boolean clienteExistente = false;
        boolean NIFValido = false;
        boolean NIPCValido = false;

        try {
            do {
                try {
                	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
                	System.out.println("\t\t\t\t\t\t\t|               TIPO DE CLIENTE               |");
                	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
                	System.out.println("\t\t\t\t\t\t\t| 1 - Cliente Final                           |");
                	System.out.println("\t\t\t\t\t\t\t| 2 - Cliente Revendedor                      |");
                	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
                	System.out.print("\t\t\t\t\t\t\t Opção (1-2): ");

                    valueA = getInt();
                    if (valueA != 1 && valueA != 2) {
                    	System.out.println("Erro! Opção inválida");
                    }
                } catch (Exception e) {
                    System.out.println("Erro! Opção inválida");
                    continue;
                }
            } while (valueA != 1 && valueA != 2);		//Condição de paragem do ciclo. Escolher um dos dois tipos de clientes

         // Se o cliente for do tipo 'Cliente Final'
            if (valueA == 1) {
                String NIF;
                do {
                    System.out.println("Introduza o NIF do Cliente: ");
                    NIF = getString();
                    if (NIF.isEmpty()) {	//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                        System.out.println("ERRO: insira um NIF válido");
                    } else if (!NIF.matches("\\d+")) { //Verificação para não poder introduzir letras mas poder repetir algarismos
                        System.out.println("ERRO: O NIF deve conter apenas números");
                    } else {
                        NIFValido = true;
                    }
                } while (!NIFValido);

                			//Percorre o arraylist de clientes para verificar se o NIF já se encontra associado a um cliente registado anteriormente
                for (Cliente cliente : clientes) {
                    if (cliente instanceof clienteFinal && ((clienteFinal) cliente).getNIF().equals(NIF)) {
                        System.out.println("Esse cliente já se encontra registado.");
                        clienteExistente = true;
                        break;
                    }
                }
                			//Se não existir, adiciona-o à lista de clientes
                if (!clienteExistente) {
                    System.out.println("Vai adicionar um cliente com estes dados:");
                    Cliente novoCliente = new clienteFinal(novoNome, endereco, NIF, "Final");
                    tabelaClientes();
                    System.out.println(novoCliente);
                    if (confirma()) {
                        clientes.add(novoCliente);
                        cabecalho("Cliente adicionado com sucesso!");
                    } else {
                        System.out.println("Criação de cliente cancelada!");
                    }
                }
                
             // Se o cliente for do tipo 'Cliente Revendedor'   
            } else if (valueA == 2) {
                String NIPC;
                do {
                    System.out.println("Introduza o NIPC: ");
                    NIPC = getString();
                    if (NIPC.isEmpty()) {	//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                        System.out.println("ERRO: insira um NIPC");
                    } else if (!NIPC.matches("\\d+")) {	//Verificação para não poder introduzir letras mas poder repetir algarismos
                        System.out.println("ERRO: O NIPC deve conter apenas números");
                    } else {
                        NIPCValido = true;
                    }
                } while (!NIPCValido);

    							//Percorre o arraylist de clientes para verificar se o NIPC já se encontra associado a um cliente registado anteriormente                
                for (Cliente cliente : clientes) {
                    if (cliente instanceof clienteRevendedor && ((clienteRevendedor) cliente).getNIPC().equals(NIPC)) {
                        System.out.println("Esse cliente já se encontra registado.");
                        clienteExistente = true;
                        break;
                    }
                }
                
                				//Se não existir, adiciona-o à lista de clientes
                if (!clienteExistente) {
                    System.out.println("Vais adicionar um cliente com estes dados:");
                    Cliente novoCliente = new clienteRevendedor(novoNome, endereco, NIPC, "Revendedor");
                    tabelaClientes();
                    System.out.println(novoCliente);
                    if (confirma()) {
                        clientes.add(novoCliente);
                        cabecalho("Cliente adicionado com sucesso!");
                    } else {
                        System.out.println("Criação de cliente cancelada!");
                    }
                }
            } else {
                System.out.println("Tipo de cliente inválido");
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO! Introduza apenas 1 ou 2.");
        }
    }

    private static void consultarMenuCliente() {
    	
        int valueC;
        boolean continuarConsulta;
        do {
            try {
            	System.out.println("\n");
            	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
            	System.out.println("\t\t\t\t\t\t\t|               C O N S U L T A               |");
            	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
            	System.out.println("\t\t\t\t\t\t\t| 1 - Consultar todos os clientes             |");
            	System.out.println("\t\t\t\t\t\t\t| 2 - Consultar um cliente                    |");
            	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
            	System.out.print("\t\t\t\t\t\t\t Opção (1-2): ");

                valueC = getInt();
                switch (valueC) {
                case 1:
                    consultartodos();
                    break;
                case 2:
                    consultarcliente();
                    break;
                default:
                    System.out.println("Erro! Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO! A opção introduzida deverá ser um número inteiro.");
                valueC = 0;		// Define valueC como 0 para garantir que o loop continua
            }
            System.out.println("Deseja continuar a consultar a Lista de Clientes? (S para continuar)");
            String continuar = getString();
            continuarConsulta = continuar.equalsIgnoreCase("S");
            
        } while (valueC != 1 && valueC != 2 && continuarConsulta);		//Condições de paragem do ciclo
    }
    
    private static void consultarcliente() {
    	
        try {
        	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
        	System.out.println("\t\t\t\t\t\t\t|        T I P O   D E   C O N S U L T A      |");
        	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
        	System.out.println("\t\t\t\t\t\t\t| 1 - Consultar por ID                        |");
        	System.out.println("\t\t\t\t\t\t\t| 2 - Consultar por Nome                      |");
        	System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
        	System.out.print("\t\t\t\t\t\t\t Opção (1-2): ");

            int valueC = getInt();
            
            //Se a opção for por ID 
            if (valueC == 1) {
                boolean encontrei = false;
                System.out.println("Insira o ID a consultar:");
                Integer idCliente = getInt();
                tabelaClientes();
                //Percorre o arraylist para ver se o idCliente existe ou não 
                for (Cliente x : clientes) {
                    if (x.getIdCliente().equals(idCliente)) {
                        System.out.println(x);
                        encontrei = true;
                    }
                }
                if (!encontrei)
                    System.out.println("Não existe registo do cliente com o ID " + idCliente);

            //Se a opção for por Nome    
            } else if (valueC == 2) {
                boolean encontrei = false;
                System.out.println("Insira o nome a consultar:");
                String nome = getString();
                if (nome.isEmpty()) {	//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                    System.out.println("É obrigatório pelo menos uma letra no nome.");
                } else {
                	//Percorre o arraylist para ver se o nome existe ou não
                    for (Cliente x : clientes) {
                        if (x.getNome().equalsIgnoreCase(nome)) {
                            cabecalho("Dados do Cliente:");
                            tabelaClientes();
                            System.out.println(x);
                            encontrei = true;
                        }
                    }
                    if (!encontrei)
                        System.out.println("Não existe registo do nome: " + nome);
                }
            } else {
                System.out.println("Opção inválida");
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO! O ID introduzido deverá ser um número inteiro.");
        }
    }

    private static void consultartodos() {
    	//Verifica se o arraylist de clientes está, ou não, vazio
        if (clientes.isEmpty()) {
            System.out.println("Não há clientes registados.");
            //caso não esteja, imprime a lista de clientes.
        } else {
            cabecalho("Lista de Clientes:");
            tabelaClientes();
            for (Cliente x : clientes)
                System.out.println(x);
        }
    }
    
    private static void editarCliente() {

  	  cabecalho("Editar Cliente");
      try {
          System.out.println("Insira o ID do cliente a ser editado:");
          int idCliente = getInt();
          Cliente x = getClienteByID(idCliente);
          //Se o id não foi encontrado
          if (x == null) {
              System.out.println("O cliente com o ID " + idCliente + " não existe");
              return; //regressa ao menuInicial()
              
          //Caso o id seja encontrado 
          } else {
              System.out.println("Vai alterar estes dados :");
              tabelaClientes();
              System.out.println(x);
              if (confirma()) {
                  String novoNome;
                  String novoEndereco;
                  String novoNIF;
                  String novoNIPC;

                  System.out.println("NOVOS DADOS para o Cliente:");
                  do {
                      System.out.print("Nome: ");
                      novoNome = getString();
                      if (novoNome.isEmpty()) {		//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                          System.out.println("Nome não pode ficar em branco. Por favor, insira um nome válido.");
                      }
                  } while (novoNome.isEmpty());	//Condição de saída do ciclo
                  x.setNome(novoNome);
                  
                  do {
                      System.out.print("Endereço: ");
                      novoEndereco = getString();
                      if (novoEndereco.isEmpty()) {	//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                          System.out.println("Endereço não pode ficar em branco. Por favor, insira um endereço válido.");
                      }
                  } while (novoEndereco.isEmpty());	//Condição de saída do ciclo
                  x.setEndereco(novoEndereco);

                  //Verifica, pelo id, se o cliente é FINAL ou REVENDEDOR
                  //se for Final solicita novo NIF:
                  if (x instanceof clienteFinal) {
                      do {
                          System.out.print("Novo NIF: ");
                          novoNIF = getString();
                          if (novoNIF.isEmpty()) {				//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                              System.out.println("NIF não pode ficar em branco. Por favor, insira um NIF válido.");
                          } else if (!novoNIF.matches("\\d+"))	//Verificação para não poder introduzir letras mas poder repetir algarismos
                              System.out.println("ERRO: O NIF deve conter apenas números");
                      } while (novoNIF.isEmpty() || !novoNIF.matches("\\d+")); //Condição de saída do ciclo
                      
                    //Percorre o arraylist de clientes para verificar se o NIF já se encontra associado a um cliente registado anteriormente
                      if (x instanceof clienteFinal) {
                          for (Cliente cliente : clientes) {
                              if (cliente != x && cliente instanceof clienteFinal && ((clienteFinal) cliente).getNIF().equals(novoNIF)) {
                                  System.out.println("Esse NIF já se encontra associado a um cliente.");	//Não atualiza esse dado
                                  System.out.println("Porém, os restantes dados foram atualizados.");
                                  return;	//Volta ao menuInicial();
                              }
                          }
                      }
                      ((clienteFinal) x).setNIF(novoNIF);
                      //Se passar todos os parâmetros, tudo é atualizado
                      cabecalho("Dados atualizados com sucesso!");
                  }

                  //se for Revendedor solicita novo NIPC:
                  if (x instanceof clienteRevendedor) {
                      do {
                          System.out.print("Novo NIPC: ");
                          novoNIPC = getString();
                          if (novoNIPC.isEmpty()) {				//Inibe o user de colocar apenas enter, mantendo-o no loop até inserir uma op válida
                              System.out.println("NIPC não pode ficar em branco. Por favor, insira um NIPC válido.");
                          } else if (!novoNIPC.matches("\\d+"))	//Verificação para não poder introduzir letras mas poder repetir algarismos
                              System.out.println("ERRO: O NIPC deve conter apenas números");
                      } while (novoNIPC.isEmpty() || !novoNIPC.matches("\\d+"));	//Condição de saída do ciclo

                    //Percorre o arraylist de clientes para verificar se o NIPC já se encontra associado a um cliente registado anteriormente
                      if (x instanceof clienteRevendedor) {
                          for (Cliente cliente : clientes) {
                              if (cliente != x && cliente instanceof clienteRevendedor
                                      && ((clienteRevendedor) cliente).getNIPC().equals(novoNIPC)) {
                                  System.out.println("Esse NIPC já se encontra associado a um cliente.");	//Não atualiza esse dado
                                  System.out.println("Porém, os restantes dados foram atualizados.");
                                  return;	//Volta ao menuInicial();
                              }
                          }
                      }
                      ((clienteRevendedor) x).setNIPC(novoNIPC);
                    //Se passar todos os parâmetros, tudo é atualizado
                      cabecalho("Dados atualizados com sucesso!");
                  }
                  return;
              }
              System.out.println("Cancelou a alteração de dados");
              return; //Volta ao menuInicial();
          }
      } catch (InputMismatchException e) {
          System.out.println("ERRO! O ID introduzido deverá ser um número inteiro.");
      }
  }
    
    private static void eliminarMenuCliente() {
    	
        try {
        	//Mensagem informativa, antes do menu, a indicar que apenas é possível eliminar um cliente que não tenha ticket aberto!
            System.out.println("\n\t\t\t        Só pode eliminar clientes que NÃO têm ticket asssociado");
            System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t|                  E L I M I N A R            |");
            System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t| 1 - Eliminar todos os Clientes              |");
            System.out.println("\t\t\t\t\t\t\t| 2 - Eliminar um Cliente                     |");
            System.out.println("\t\t\t\t\t\t\t+---------------------------------------------+");
            System.out.print("\t\t\t\t\t\t\t Opção (1-2): ");

            int valueE = getInt();
            //Se a opção for todos
            if (valueE == 1) {
                eliminarClientes();
            //Se a opção for apenas um
            } else if (valueE == 2) {
                eliminarUmCliente();
            } else
                System.out.println("Opção inválida");
        } catch (InputMismatchException e) {
            System.out.println("ERRO! A opção introduzida deverá ser um número inteiro.");
        }
    }

    private static void eliminarUmCliente() {
    	
        boolean regressar = true;
        do {
            cabecalho("Eliminar Cliente");
            try {
                System.out.println("Insira o ID do cliente que deseja eliminar:");
                Integer idCliente = getInt();
                Cliente x = getClienteByID(idCliente);	//chama a função getClienteByID
                if (x == null) {
                    System.out.println("O cliente com o ID " + idCliente + " não existe");
                    return;		//Condição para sair do ciclo e voltar ao menuInicial()
                
                    //Portanto, se o id do cliente existir 
                } else {
                    System.out.println("Vai eliminar este cliente: ");
                    tabelaClientes();
                    System.out.println(x);
                    if (confirma()) {
                        if (x.getTickets().isEmpty()) {	//Verifica se o cliente em questão não têm tickets	
                            clientes.remove(x);
                            cabecalho("O Cliente foi eliminado!");
                        // Não o elimina, caso tenha    
                        } else {
                            System.out.println("O Cliente em questão tem tickets associados!");
                            regressar = false;	//Sai do ciclo
                        }
                    } else {
                        System.out.println("Operação cancelada.");
                        regressar = false;	//Sai do ciclo
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO! O ID introduzido deverá ser um número inteiro.");
            }
        } while (regressar == true);	//O ciclo continua enquanto 'regressar' for verdadeiro
    }

    private static void eliminarClientes() {
    	
    	// ArrayList auxiliar para armazenar os clientes sem tickets associados
        ArrayList<Cliente> auxiliar = new ArrayList<>();

        //Percorro o arraylist de clientes
        for (Cliente x : clientes) 
        {
            if (!x.getTickets().isEmpty()) { // quando não está vazio, adiciono os clientes do arraylist Clientes para o ArrayList Auxiliar
                auxiliar.add(x);
            }
        }
        
        //Atualiza o arraylist de Clientes para conter APENAS os que não têm tickets associados.
        clientes = new ArrayList<>(auxiliar);
        System.out.println("Todos os clientes sem Ticket associados foram eliminados.");
    }

    //TICKET
    private static void adicionarTicket() {
    	
    	cabecalho("Criar Ticket");
        Integer idCliente;
        Cliente cliente;
        boolean previsto = false;
        boolean noTicket = false;

        do {
            try {
                System.out.println("Insira o ID do cliente: ");
                idCliente = getInt();
                cliente = getClienteByID(idCliente);

                if (cliente == null) {
                    System.out.println("O cliente com o ID " + idCliente + " não existe");
                    return;			//Condição de saída do ciclo para evitar um loop infinito
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: O ID do cliente deve ser um número inteiro.");
                idCliente = null; 	// Limpar o valor inválido para evitar um loop infinito
                cliente = null;
            }
        } while (cliente == null);

        System.out.println("Insira a descrição do ticket: ");
        String descricao = getString();

        do {
            try {
                LocalDateTime data = LocalDateTime.now();		//A data de criação do ticket corresponde à data atual.

                System.out.println("Insira a data prevista para o ticket (no formato YYYY/MM/DD): ");
                LocalDate dataPrevista = getData();

                //Pede ao user uma data no qual ele prevê que o ticket fique fechado
                if (dataPrevista.isBefore(LocalDate.now())) {		// por causa do import 
                    System.out.println("Insira uma data superior à data atual");
                } else {
                    int novoIdTicket = cliente.getTickets().size() + 1;
                    Ticket novoTicket = new Ticket(novoIdTicket, cliente, data, dataPrevista, descricao, "Registado",
                            null, null, null);
                    cabecalho("Notas: ");

                    //Detalhes do Ticket
                    String novaNota = "Estado [" + novoTicket.getEstado() + "] no dia [" + LocalDate.now() + "]"
                            + "\n\t   [NOTAS] " + getString() + "\n";
                    		//se estes valores não estiveres negativos, concatena na novaNota
                    if (novoTicket.getValor_mao() != null && novoTicket.getValor_pecas() != null && novoTicket.getDataFinal() != null) {
                        novaNota += "\n    Valor de Serviço: " + novoTicket.getValor_mao() + "\n      Valor de Peças: "
                                + novoTicket.getValor_pecas() + "\n Data Final: " + novoTicket.getDataFinal();
                    }
                    
                    //Mostra todos os dados inseridos
                    System.out.println("Vai criar este Ticket no cliente " + cliente.getNome() + ":");
                    tabelaTickets();
                    System.out.println(novoTicket);
                    novoTicket.getNotas().add(novaNota);
                    mostrarNotasTicket(novoTicket);
                    if (confirma()) {
                        cliente.getTickets().add(novoTicket);
                        cabecalho("Ticket criado com sucesso!");
                        noTicket = true;	//Condição para sair do ciclo
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                previsto = false;	//Condição para se manter no ciclo
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Certifique-se de usar o formato YYYY/MM/DD.");
                previsto = true;
            }
        } while (previsto || noTicket != true);		//Condições para o ciclo correr.
    }
    
    private static void consultarMenuTickets() {
    	
        boolean continuarConsulta;
        do {
            try {
            	System.out.println("\n");
            	System.out.println("\t\t\t\t\t\t\t+----------------------------------------+");
            	System.out.println("\t\t\t\t\t\t\t|               C O N S U L T A          |");
            	System.out.println("\t\t\t\t\t\t\t+----------------------------------------+");
            	System.out.println("\t\t\t\t\t\t\t| 1 - Todos os Tickets                   |");
            	System.out.println("\t\t\t\t\t\t\t| 2 - ID do Ticket (com histórico)       |");
            	System.out.println("\t\t\t\t\t\t\t| 3 - Estado do Ticket                   |");
            	System.out.println("\t\t\t\t\t\t\t| 4 - Entre Datas                        |");
            	System.out.println("\t\t\t\t\t\t\t| 5 - Tickets com Data Prevista Expirada |");
            	System.out.println("\t\t\t\t\t\t\t+----------------------------------------+");
            	System.out.print("\t\t\t\t\t\t\t Opção (1-5): ");

                int opcao = getInt();
                
                switch (opcao) {
                case 1:
                    consultarTicketTodos();
                    break;
                case 2:
                    consultarTicketId();
                    break;
                case 3:
                    consultarTicketEstado();
                    break;
                case 4:
                    consultarTicketData();
                    break;
                case 5:
                    consultarTicketExpirado();
                    break;
                default:
                    System.out.println("Opção inválida");
                }      
            } catch (InputMismatchException e) {
                System.out.println("ERRO! A opção introduzida deverá ser um número inteiro.");
            }
            
            System.out.println("Deseja continuar a consultar tickets? (S para continuar)");
            String continuar = getString();
            continuarConsulta = continuar.equalsIgnoreCase("S");
            
        } while (continuarConsulta);	//Condição na qual o ciclo continuar a correr, ie, o user querer continuar a consultar.
    }

    private static void consultarTicketTodos() {
    	
        boolean existem = false;	

        //Percorre o arraylist de clientes e verifica se estes têm tickets registados
        for (Cliente cliente : clientes) {
            if (!cliente.getTickets().isEmpty()) { //Verifica se há algum cliente com ticket, primeiro
                existem = true;	//se houver, é porque existem	
                break;
            }
        }
        //Se existirem, percorre todos os clientes e seus tickets, mostrando cada um deles
        if (existem) {
            tabelaTickets();
            for (Cliente cliente : clientes) {
                for (Ticket ticket : cliente.getTickets()) {
                    System.out.println(ticket);
                }
            }
        } else {
            System.out.println("Não existem tickets registados.");
        }
    }
    
    private static void consultarTicketId() {
    	
        boolean ticketEncontrado;
        do {
            ticketEncontrado = false;
            try {
                System.out.println("Insira o ID do ticket a consultar:");
                int idTicket = getInt();

                // Percorre todos os clientes e seus tickets para procurar o ticket com o ID inserido
                for (Cliente cliente : clientes) {
                    for (Ticket ticket : cliente.getTickets()) {
                    	//Se coincidir:
                        if (ticket.getIdTicket().equals(idTicket)) {
                            System.out.println("Ticket encontrado:");
                            tabelaTickets();
                            System.out.println(ticket);
                            ticketEncontrado = true; //var auxiliar toma o valor de true

                            System.out.println("Deseja aceder ao histórico deste ticket? (S para confirmar)");
                            String resposta = getString();
                            if (resposta.equalsIgnoreCase("S")) {
                                mostrarNotasTicket(ticket);
                            }
                            break;	//Condição de saída do ciclo
                        }
                    }
                    if (ticketEncontrado) {
                        break;	//Condição de saída do ciclo, mesmo que encontrado, pela condição de paragem do ciclo ser infinita
                    }
                }
                if (!ticketEncontrado) {
                    System.out.println("Ticket não encontrado.");
                    break;	//Condição de saída do ciclo
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO! A opção introduzida deverá ser um número inteiro.");
            }
        } while (false);	//Ciclo infinito mas com condições controlados em cada condição
    }

    private static void consultarTicketEstado() {
    	
    	cabecalho("Consulta por Estado");
        System.out.println("Escolha o estado do ticket a consultar:");

        String estado = menuEstado();

        boolean encontrado = false;
        tabelaTickets();
        //Percorre os clientes, e seus respectivos tickets, para verificar se o estado introduzido pela user consta dos dados guardados ou não
        //Se sim, mostra-os.
        for (Cliente cliente : clientes) {
            for (Ticket ticket : cliente.getTickets()) {
                if (ticket.getEstado().equalsIgnoreCase(estado)) {
                    System.out.println(ticket);
                    encontrado = true;
                }
            }
        }
        //Caso não seja encontrado ticket nenhum no estado escolhido pelo user
        if (!encontrado) {
            System.out.println("Nenhum ticket encontrado com o estado: " + estado);
        }
    }
    
    private static void consultarTicketData() {
    	
    	cabecalho("Consulta entre Datas");
        boolean continuarConsulta;
        do {
            continuarConsulta = false;	//Variável utilizada para condição na qual o ciclo corre

            try {
                System.out.println("Insira a primeira data (no formato YYYY/MM/DD): ");
                LocalDate primeiraData = getData();
                System.out.println("Insira a segunda data (no formato YYYY/MM/DD): ");
                LocalDate segundaData = getData();

                if (segundaData.isBefore(primeiraData)) {
                    System.out.println("A segunda data deve ser posterior à primeira.");
                    continuarConsulta = true; 
                    continue; 	// Volta ao início do loop para perguntar as datas novamente
                }

                boolean encontrado = false;
                tabelaTickets();
                //Percorre o arraylist de clientes e de tickets para verificar se há tickets entre as datas seleccionadas pelo user
                for (Cliente cliente : clientes) {
                    for (Ticket ticket : cliente.getTickets()) {
                        LocalDate dataTicket = ticket.getData().toLocalDate();
                        //Caso haja
                        if (!dataTicket.isBefore(primeiraData) && !dataTicket.isAfter(segundaData)) {
                            System.out.println(ticket);
                            encontrado = true;
                        }
                    }
                }
                //Caso não haja
                if (!encontrado) {
                    System.out.println("Nenhum ticket encontrado entre as datas fornecidas.");
                    continuarConsulta = true; // Ativar para continuar a consulta
                    break;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido! Formato: YYYY/MM/DD ");
                continuarConsulta = true;
            }
        } while (continuarConsulta); // Condição de continuidade do ciclo é a de que o user queira continuar a consultar
    }

    private static void consultarTicketExpirado() {

        cabecalho("Tickets com Data Prevista Expirada");
        boolean expirado = false;
        
        // Ciclo para verificar os tickets de todos os clientes
        for (Cliente cliente : clientes) {
            for (Ticket ticket : cliente.getTickets()) {
                LocalDate dataPrevista = ticket.getDataPrevista();      // Obtém a data prevista do ticket
                LocalDateTime dataFinal = ticket.getDataFinal();        // Obtém a data final do ticket
                
                // Verifica se ambas as datas foram preenchidas
                if (dataPrevista != null && dataFinal != null) {
                    
                    // Converte a data prevista para LocalDateTime para facilitar a comparação
                    // A conversão é feita apenas dentro desta função e não afeta outras partes do programa
                    LocalDateTime dataPrevistaDateTime = dataPrevista.atStartOfDay();
                    
                    // Verifica se a data final é depois da data prevista   
                    if (dataFinal.isAfter((dataPrevistaDateTime))) {
                        expirado = true;
                        tabelaTickets();
                        System.out.println(ticket);
                    }
                }
            }
        }
        // Verifica se não há tickets expirados 
        if (!expirado) {
            System.out.println("Não há tickets com data prevista expirada.");
        }
    }

    private static void editarTicket() {
        cabecalho("Editar Ticket");
        boolean entradaValida = false; // Variável para controlar a entrada válida do utilizador
        
        do {
            System.out.println("Insira o ID do ticket que deseja editar:");
            int idTicket;
            try {
                idTicket = getInt();    
            } catch (Exception e) {
                System.out.println("ERRO! O ID introduzido deverá ser um número inteiro.");
                continue; // Regressa ao início do ciclo
            }
            
            Ticket ticket = getTicketByID(idTicket);    
            //Se o ticket NÃO existir
            if (ticket == null) {       
                System.out.println("O ticket com o ID " + idTicket + " não existe.");   
                return; // Regressa ao início do ciclo
            //Se existir:
            } else {
                System.out.println("Vai alterar este ticket: ");
                tabelaTickets();
                System.out.println(ticket);
                System.out.println("Escolha o novo estado do ticket:");
                String novoEstado = menuEstado();   // Obtém o novo estado do ticket através do menu
                
                // VERIFICAÇÕES PARA IMPEDIR ALTERAÇÕES ENTRE CERTOS ESTADOS 
 
                if (novoEstado.equals("Registado") && !ticket.getEstado().equals("Registado")) {
                    System.out.println("Não é possível editar o ticket para 'Registado' depois do Ticket já ter mudado de estado anteriormente.");
                    continue;   // Regressa ao início do ciclo 
                }
                
                
                if (ticket.getEstado().equals("Registado")) {
                    if (!novoEstado.equals("Orçamentado")) {
                        System.out.println("Este Ticket encontra-se no estado 'Registado' e, como tal, só pode ser modificado para 'Orçamentado'\n");
                        continue; //Regressa ao início do ciclo 
                    }
                }     
                
                
                if (ticket.getEstado().equals("Orçamentado")) { 
                    if (novoEstado.equals("Fechado") && !ticket.getEstado().equals("Pronto")) {
                        System.out.println("\n Este Ticket encontra-se no estado 'Orçamentado' e, como tal, só pode ser alterado para 'Fechado' depois de 'Pronto'\n");
                        continue; //Regressa ao início do ciclo 
                    }
                }

                if (ticket.getEstado().equals("Pronto")) {
                    if (!novoEstado.equals("Fechado")) {
                        System.out.println("Este Ticket encontra-se no estado 'Pronto' e, como tal, só pode ser modificado para 'Fechado'\n");
                        continue; //Regressa ao início do ciclo
                    }
                    //Impedir que chegue a prontos antes de obter estes valores
                    if (!(ticket.getValor_mao() != null) || !(ticket.getValor_pecas() != null)) {
                        System.out.println("O ticket não pode ser atualizado para 'Pronto' porque ainda não foi 'Orçamentado'\n");
                        continue; //Regressa ao início do ciclo
                    }
                }
                
                //ESTADO: ORÇAMENTADO
                // Onde o utilizar insere os valores de mão de obra e peças e calculamos o total
                if (novoEstado.equals("Orçamentado")) { 
                    Float vMao;
                    try {
                        System.out.println("Insira o valor dos serviços prestados (S/IVA): ");
                        vMao = getFloat();
                        if (vMao == null) {
                            System.out.println("Erro: Insira um valor válido");
                        } else {
                            ticket.setValor_mao(vMao);
                            System.out.println("O valor de Serviços Prestados introduzido foi de : " + vMao + "€\n");
                        }
                    } catch (Exception e) {
                        System.out.println("ERRO! O Valor de Serviços Prestados deve ser numérico.");
                        continue;	//Regressa ao início do ciclo
                    }
                    try {
                        System.out.println("Insira o valor de peças a aplicar (S/IVA):");
                        Float vPecas = getFloat();
                        if (vPecas == null) {
                            System.out.println("Erro: Insira um valor válido");
                        } else {
                            ticket.setValor_pecas(vPecas);
                            System.out.println("O valor de valor de peças a aplicar foi de : " + vPecas + "€\n");
                        }
                        System.out.println("TOTAL (S/IVA) : " + (vPecas + vMao) + " €\n");
                    } catch (Exception e) {
                        System.out.println("ERRO! O Valor de Peças deve ser numérico.");
                        continue;	//Regressa ao início do ciclo
                    }
                }
 
					// ESTADO: PRONTO
					// Aqui, apuramos os descontos e os valores finais com o IVA
					if (novoEstado.equals("Pronto")) {
						ticket.setDataFinal(LocalDateTime.now()); //DataFinal é obtida como a data atual
						Cliente cliente = ticket.getcliente();

						// Verifica se o cliente é do tipo Cliente FINAL
						if (cliente instanceof clienteFinal) {
							System.out.println(
									"Trata-se de um CLIENTE FINAL. \n Deseja aplicar desconto sobre a mão de obra? (S para confirmar)");
							String resposta = getString().toLowerCase();

							// Se a resposta for afirmativa, aplica o desconto
							if (resposta.equals("s")) {
								float dMao = ((clienteFinal) cliente).getDesconto_Mao();
								// Verifica se o desconto é válido
								// aceitamos zero, não é obrigatório ter desconto
								if (dMao < 0) {
									System.out.println("Erro: Insira um valor válido");
								} else {
									// Calcula o valor da mão de obra com desconto
									float valor_MaoComD = ticket.getValor_mao() - (ticket.getValor_mao() * dMao);
									ticket.setValor_mao(valor_MaoComD);

									// Depois da opção do utilizador, mostra os descontos: 
									System.out.println("+-----------------------------------------------------+\n");
									System.out.println("DESCONTO de mão de obra APLICADO com sucesso.");
									System.out.println(
											"                   O valor do desconto foi de : " + (dMao * 100) + "%"); // ??
									System.out.println(
											"O valor da mão de obra, já com desconto, é de : " + valor_MaoComD + "€");

									System.out.println("+-----------------------------------------------------+\n");
									// Mostra o valor com o IVA: 
									System.out.println("                          VALOR TOTAL (C/IVA) : "
											+ String.format("%.2f", (valor_MaoComD + ticket.getValor_pecas()) * 1.23f)
											+ "€");
									System.out.println("+-----------------------------------------------------+\n");
								}
							}
							// Verifica se o cliente é do tipo Cliente REVENDEDOR    
						} else if (cliente instanceof clienteRevendedor) {
							System.out.println(
									"Trata-se de um CLIENTE REVENDEDOR. \n Deseja aplicar algum desconto? (S para confirmar)");
							String resposta = getString().toLowerCase();
							// Se a resposta for afirmativa, avança para perguntar se quer algum desconto
							if (resposta.equals("s")) {
								float dMao = 0;
								try {
									System.out.println("Introduza o desconto de mão de obra (em %) :");
									dMao = (getFloat() / 100);

									// Verifica se o desconto é válido
									// aceitamos zero, não é obrigatório ter desconto
									if (dMao < 0) {
										System.out.println("Erro: Insira um valor válido");
										return;

									} else {
										// Define o desconto de mão de obra para o revendedor
										((clienteRevendedor) cliente).setDesconto_Mao(dMao);
									}
								} catch (Exception e) {
									System.out.println("ERRO! O desconto deve ser numérico. ");
									dMao = 0;
								}

								float dTotal;
								try {
									System.out.println("Introduza o desconto total (em %) :");
									dTotal = (getFloat() / 100);

									// Verifica se o desconto é válido
									if (dTotal < 0) {
										System.out.println("Erro: Insira um valor válido");
										return;

									} else {
										// Define o desconto total para o revendedor
										((clienteRevendedor) cliente).setDesconto_Total(dTotal);
									}
								} catch (Exception e) {
									System.out.println("ERRO! O desconto deve ser numérico. ");
									dTotal = 0;
								}

								// Calculo dos descontos e o set dos mesmos 
								float valor_MaoComD = ticket.getValor_mao() - (ticket.getValor_mao() * dMao);
								float valor_TotalComD = (ticket.getValor_pecas() + valor_MaoComD)
										- ((ticket.getValor_pecas() + valor_MaoComD) * dTotal);

								ticket.setValor_total(valor_TotalComD);
								ticket.setValor_mao(valor_MaoComD);

								// Exibe informações sobre os descontos aplicados
								System.out.println("+-----------------------------------------------------+\n");
								System.out.println("DESCONTO de mão de obra APLICADO com sucesso.\n");
								System.out.println(
										"                             Desconto aplicado: " + (dMao * 100) + "%");
								System.out.println(
										"O valor da mão de obra, já com desconto, é de : " + valor_MaoComD + "€");

								System.out.println("\n+-------------------------------------------------------+\n");
								System.out.println("DESCONTO total APLICADO com sucesso.\n");
								System.out.println(
										"                             Desconto aplicado: " + (dTotal * 100) + "%");
								System.out.println("      O valor da total, já com desconto, é de : "
										+ String.format("%.2f", valor_TotalComD) + "€");
								System.out.println("+--------------------------------------------------------+\n");
								//E do total da fatura já com IVA
								System.out.printf("                         VALOR FINAL (C/IVA): %.2f €\n\n",
										valor_TotalComD * 1.23f);
								System.out.println("+--------------------------------------------------------+\n");
							}
						}
					} 
				// Atualiza os estados do ticket para o novo estado com o arraylist das notas incluido 
                ticket.setEstado(novoEstado);

                System.out.println("Insira notas sobre a alteração:");
                
                // Cria uma nova nota com o estado atualizado e a data atual
                String novaNota = "Estado [" + ticket.getEstado() + "] no dia [" + LocalDate.now() + "]"
                        + "\n\t   [NOTAS] " + getString() + "\n";
                
                // Adiciona informações extras à nova nota quando o estado é alterado para "Orçamentado"
                // += para concatenar com a string novaNota
                if (ticket.getValor_mao() != null && ticket.getValor_pecas() != null) {
                    Float valorTotal = ticket.getValor_mao() + ticket.getValor_pecas();
                    novaNota += "\nValor de Mão-de-Obra (S/IVA): " + ticket.getValor_mao() + " €"                                                                                               
                              + "\n      Valor de Peças (S/IVA): " + ticket.getValor_pecas() + " €"
                              + "\n        Valor Total  (S/IVA): " + valorTotal + " €\n";
                }
                // Adiciona informações extras à nova nota quando o estado é alterado para "Pronto"
                if (ticket.getDataFinal() != null) {
                    novaNota += String.format("\nData Final [" + ticket.getDataFinal().toLocalDate()) + "]";

                    //Caso seja Final
                    if (ticket.getcliente() instanceof clienteFinal) {
                        float dMao = ((clienteFinal) ticket.getcliente()).getDesconto_Mao();
                        float valor_MaoComD = ticket.getValor_mao() - (ticket.getValor_mao() * dMao);

                        novaNota += "\nValor de Desconto de Mão de Obra: " + (dMao * 100) + "%"
                                 + "\nTOTAL DA FATURA (C/IVA): " + String.format("%.2f", valor_MaoComD * 1.23f) + " €\n";

                    //Caso seja Revendedor
                    } else if (ticket.getcliente() instanceof clienteRevendedor) {
                        float dMao = ((clienteRevendedor) ticket.getcliente()).getDesconto_Mao();
                        float dTotal = ((clienteRevendedor) ticket.getcliente()).getDesconto_Total();

                        float valor_MaoComD = ticket.getValor_mao() - (ticket.getValor_mao() * dMao);
                        float valor_TotalComD = (ticket.getValor_pecas() + valor_MaoComD)
                                - ((ticket.getValor_pecas() + valor_MaoComD) * dTotal);

                        novaNota += "\nValor de Desconto de Mão de Obra: " + (dMao * 100) + "%"
                                  + "\nValor de Desconto Total: " + (dTotal * 100) + "%" + 
                        		    "\nTOTAL DA FATURA (C/IVA): " + String.format("%.2f", valor_TotalComD * 1.23f) + " €\n";
                    }
                }
                // Adiciona a nova nota ao ArrayList de notas do ticket
                ticket.getNotas().add(novaNota);
                cabecalho("Estado alterado para:"+novoEstado);
                // Define a entrada como válida para sair do ciclo do-while
                entradaValida = true;
                }
        } while (!entradaValida); // Condição de paragem do ciclo   
    }

    //MÉTODOS AUXILIARES
    private static void eliminarTicketRegistado() {
    	
        try {
            cabecalho("Eliminar Ticket");
            System.out.println("Insira o ID do ticket que deseja eliminar:");
            Integer idTicket = getInt();
            Ticket r = getTicketByID(idTicket);

            // Se o ticket não existir
            if (r == null) {                                
                System.out.println("O ticket com o ID " + idTicket + " não existe.");
            } else {
                
                // Se o estado do ticket for "Registado"
                if (r.getEstado().equals("Registado")) {    
                    
                	//Percorre o arraylist de clientes para verificar se pertence ao id do Ticket
                    for (Cliente x : clientes) {
                        if (x.getTickets().contains(r)) {       
                            System.out.println("Vai eliminar o seguinte ticket:");
                            tabelaTickets();
                            System.out.println(r);

                            if (confirma()) {       
                                x.getTickets().remove(r);
                                cabecalho("Ticket foi eliminado com sucesso!");
                            } else {
                                System.out.println("Operação cancelada.");
                            }
                            return; // Regressa ao menuInicial()
                        }
                    } //Se o ticket existir e estiver registado mas não associado a dito cliente
                    System.out.println("O ticket com o ID " + idTicket + " existe mas não está associado a esse cliente.");
                    
                } else {
                    System.out.println("O ticket não se encontra no estado Registado.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO! O ID introduzido deverá ser um número inteiro.");
        }
    }

    //AUXILIARES
    private static void mostrarNotasTicket(Ticket ticket) { 
    	//ArrayList de notas, presente na classe Ticket, e que cresce à medida que o estado é alterado e notas inseridas
        cabecalho("Notas do Ticket");
        for (String nota : ticket.getNotas()) {
            System.out.println(nota);
        }
    }

    private static void cabecalho(String titulo) {
    	// Calcula o número de espaços em branco antes e depois do título para o centrar
        int espacos = 32 - titulo.length();  	//32 é o valor max definido por nós para o nr de carateres dentro do cabecalho
        int espacosEsquerda = (espacos < 0) ? 0 : espacos / 2;
        int espacosDireita = (espacos < 0) ? 0 : espacos - espacosEsquerda;
        // Adiciona espaços em branco antes e depois do título o centrar
        titulo = " ".repeat(espacosEsquerda) + titulo + " ".repeat(espacosDireita);

        System.out.println("+--------------------------------+");
        System.out.println("|" + titulo + "|");
        System.out.println("+--------------------------------+");
    }
    
    private static void tabelaClientes() {
        System.out.println(
                "+----------+---------------------+----------------------------------------------------+-----------------+-----------------+\n"
              + "|    ID    |         Nome        |                     Endereço                       |       Tipo      |     NIF/NIPC    |\n"
              + "+----------+---------------------+----------------------------------------------------+-----------------+-----------------+");
    }

    private static void tabelaTickets() {
        System.out.println(
                 "+-----------+------------+---------------------+---------------------+------------------+---------------------+------------+---------------+----------------------------+\n"
               + "| ID Ticket | ID Cliente |       Cliente       |     Data criação    |   Data Prevista  |     Data Final      |    Tipo    |     Estado    |          Descrição         |\n"
               + "+-----------+------------+---------------------+---------------------+------------------+---------------------+------------+---------------+----------------------------+");
    }

    private static int menuInicial() {
    	
        @SuppressWarnings("resource")
        Scanner menuscan = new Scanner(System.in);
        System.out.println("\t\t\t\t\t\t\t+----------------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t|                    M E N U                   |");
        System.out.println("\t\t\t\t\t\t\t+----------------------------------------------+");
        System.out.println("\t\t\t\t\t\t\t|        CLIENTE        |        TICKET        |");
        System.out.println("\t\t\t\t\t\t\t|                       |                      |");
        System.out.println("\t\t\t\t\t\t\t|     1- Inserir        |     5- Inserir       |");
        System.out.println("\t\t\t\t\t\t\t|     2- Consultar      |     6- Consultar     |");
        System.out.println("\t\t\t\t\t\t\t|     3- Editar         |     7- Editar        |");
        System.out.println("\t\t\t\t\t\t\t|     4- Eliminar       |     8- Eliminar      |");
        System.out.println("\t\t\t\t\t\t\t|----------------------------------------------|");
        System.out.println("\t\t\t\t\t\t\t|     0- Sair                 9- Créditos      |");
        System.out.println("\t\t\t\t\t\t\t+----------------------------------------------+");
        System.out.print("\t\t\t\t\t\t\t Opção (0-9): ");

        int opcao;
        							// Ciclo para garantir que o utilizador insire um número inteiro válido de 0 a 9
        while (!menuscan.hasNextInt() || (opcao = menuscan.nextInt()) < 0 || opcao > 9) { 
            System.out.println("Por favor, insira um número válido de 0 a 9.");
            System.out.print(" Opção : ");
            menuscan.nextLine();
        }
        return opcao;   
    }

    private static String menuEstado() {
    	
        try {
            System.out.println("\t\t\t\t\t\t\t+------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t|            E S T A D O S           |");
            System.out.println("\t\t\t\t\t\t\t+------------------------------------+");
            System.out.println("\t\t\t\t\t\t\t| 1 - Registado                      |");
            System.out.println("\t\t\t\t\t\t\t| 2 - Orçamentado                    |");
            System.out.println("\t\t\t\t\t\t\t| 3 - Inviável                       |");
            System.out.println("\t\t\t\t\t\t\t| 4 - Em curso                       |");
            System.out.println("\t\t\t\t\t\t\t| 5 - Aguarda peças                  |");
            System.out.println("\t\t\t\t\t\t\t| 6 - Pronto                         |");
            System.out.println("\t\t\t\t\t\t\t| 7 - Fechado                        |");
            System.out.println("\t\t\t\t\t\t\t+------------------------------------+");
            System.out.print("\t\t\t\t\t\t\t Opção (1-7): ");

            int opcao = getInt();

            switch (opcao) {
            case 1:
                return "Registado";
            case 2:
                return "Orçamentado";
            case 3:
                return "Inviável";
            case 4:
                return "Em curso";
            case 5:
                return "Aguarda peças";
            case 6:
                return "Pronto";
            case 7:
                return "Fechado";
            default:
                System.out.println("Opção inválida.");
                return menuEstado();
            }
        } catch (InputMismatchException e) {
            System.out.println("ERRO! A opção introduzida deverá ser um número inteiro.");
            return menuEstado();
        }
    }
    
    private static boolean confirma() {
        char resposta;
        System.out.println("Deseja continuar? (S para confirmar) ");
        resposta = getChar();
     // Devolve true se a resposta for 'S' ou 's', caso contrário, devolve false
        if (resposta == 'S' || resposta == 's') 
            return true;
        return false;
    }

    private static Cliente getClienteByID(int ID) {     
    	//Função auxiliar para obter e verificar o ID inserido pelo utilizador - relativamente ao Cliente
        for (Cliente x : clientes) {
            if (x.getIdCliente().equals(ID)) {          // Verifica se o ID do cliente atual corresponde ao ID fornecido
                return x;	//devolve o cliente
            }
        }
        return null;	//devolve nulo
    }
    
    private static Ticket getTicketByID(int idTicket) {    
    	
    	//Função auxiliar para obter e verificar o ID inserido pelo utilizador - relativamente ao Ticket
        try {                                               
            for (Cliente x : clientes) {
                for (Ticket r : x.getTickets()) {
                    // Verifica se o ID do ticket atual corresponde ao ID inserido
                    if (r.getIdTicket().equals(idTicket)) {      
                        return r;   		// Retorna o ticket se este for encontrado
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO! O ID introduzido deverá ser um número inteiro.");

        }
        return null;
    }

    private static String getString() {
        Scanner t = new Scanner(System.in);
        return t.nextLine();
    }

    private static char getChar() {
        Scanner t = new Scanner(System.in);
        return t.nextLine().charAt(0);
    }

    private static LocalDate getData() {
        String dataString = getString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate data = LocalDate.parse(dataString, formatter);
        return data;
    }

    static int getInt() {
        Scanner t = new Scanner(System.in);
        return t.nextInt();
    }

    private static Float getFloat() {
        Scanner t = new Scanner(System.in);
        return t.nextFloat();
    }
    
    // Auxiliares do Backup de Dados
    private static void atualizarIdClienteProx(ArrayList<Cliente> clientes) {
        
        // Obtém o último cliente na lista e define o próximo ID de cliente, como o ID do último cliente + 1
        Cliente.setIdClienteProx(clientes.get(clientes.size() - 1).getIdCliente() + 1);
    }

    private static void atualizarIdTicketProx(ArrayList<Cliente> clientes) { //Atualiza o valor do próximo ID de ticket com base nos IDs existentes já atribuídos aos tickets.
        int maxId = 0;
        
        // Ciclo que percorre cada ticket na lista de tickets do cliente atual
        for (Cliente cliente : clientes) {                      
            ArrayList<Ticket> tickets = cliente.getTickets();   
            for (Ticket ticket : tickets) { 
                // Verifica se o ID do ticket atual é maior que o ID máximo atual
                if (ticket.getIdTicket() > maxId) {              
                    maxId = ticket.getIdTicket();
                }
            }
            Ticket.setIdTicketProx(maxId + 1);      // Define o próximo ID de ticket para o valor máximo encontrado + 1
        }
    }
    
    private static void creditos() {
        String creditos = ""
                + "+-----------------------------------------+\n"
                + "|                 Créditos                |\n"
                + "+-----------------------------------------+\n"
                + "|                                         |\n"
                + "|          Gestão de Reparações           |\n"
                + "|                                         |\n"
                + "|         Trabalho Prático Final          |\n"
                + "|                                         |\n"
                + "|      Programação Orientada a Objetos    |\n"
                + "|              UFCD - 5413                |\n"
                + "+-----------------------------------------+\n"
                + "| Desenvolvido por:                       |\n"
                + "| - Rui Mendes                            |\n"
                + "| - Rita Martins                          |\n"
                + "+-----------------------------------------+\n";
        
       // Percorre cada caractere da string de créditos
        for (char c : creditos.toCharArray()) {             // Imprime cada caractere com um pequeno intervalo
            System.out.print(c);
            try {
                Thread.sleep(10);                           // Intervalo entre cada caractere (em milissegundos)
            } catch (InterruptedException e) {
            }
        }
    }
}