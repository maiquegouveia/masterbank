package entities;
import entities.GetFiles;
import java.util.List;
import java.util.Scanner;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import entities.CheckIfExists;

public class AdmMethods {
	GetFiles getf = new GetFiles();
	Scanner scan = new Scanner(System.in);
	CheckIfExists ci = new CheckIfExists();
	String cpf;
	public void ConsultUserData() {
		int menuOption;
		do {
			System.out.println("\nMétodos de Busca\n");
			System.out.println("1 - Nome de Usuário");
			System.out.println("2 - Email");
			System.out.println("3 - Cpf");
			System.out.println("4 - Sair");
			System.out.println("\nEscolha uma opção -->");
			menuOption = scan.nextInt();
			int position;
			String info;
			switch(menuOption) {
				case 1:
					System.out.println("\nDigite o nome de usuário:");
					info = scan.next();
					position = 0;
					GetData(info, position);
					break;
				case 2:
					System.out.println("\nDigite o email do usuário:");
					info = scan.next();
					position = 4;
					GetData(info, position);
					break;
				case 3:
					System.out.println("\nDigite o cpf do usuário:");
					info = scan.next();
					position = 3;
					GetData(info, position);
					break;
				case 4:
					System.out.println("\nVoltando ao menu principal...");
					break;
				default:
					System.out.println("\nOpção inválida.");
			}
		} while (menuOption != 4);
		
	}
	
	public void ModifyUserData() {
		System.out.println("\nDigite o cpf do usuário:");
		cpf = scan.next();
		ci.info = cpf;
		ci.position = 3;
		int containsCpf = ci.Check();
		if (containsCpf == 1) {
			int menuOption;
			do {
				System.out.println("\nAlterações Permitidas\n");
				System.out.println("1 - Nome de Usuário");
				System.out.println("2 - Email");
				System.out.println("3 - Número de Telefone");
				System.out.println("4 - Senha de acesso");
				System.out.println("5 - Sair");
				System.out.println("\nEscolha uma opção -->");
				menuOption = scan.nextInt();

				int valid;
				switch(menuOption) {
					case 1:
						System.out.println("\nDigite um novo nome de usuário:");
						ci.info = scan.next();
						ci.position = 0;
						valid = ci.Check();
						if(valid == 1) {
							System.out.println("\nEste nome de usuário já está cadastrado.");
						} else {
							Update(ci.info, ci.position);
						}
						menuOption = 5;
						break;
					case 2:
						System.out.println("\nDigite um novo email:");
						ci.info = scan.next();
						ci.position = 4;
						valid = ci.Check();
						if(valid == 1) {
							System.out.println("\nEste email já está cadastrado.");
						} else {
							Update(ci.info, ci.position);
						}
						menuOption = 5;
						break;
					case 3:
						System.out.println("\nDigite um novo número de telefone:");
						ci.info = scan.next();
						ci.position = 2;
						valid = ci.Check();
						if(valid == 1) {
							System.out.println("\nEste número de telefone já está cadastrado.");
						} else {
							int numberSize = ci.info.length();
							if(numberSize == 11) {
								Update(ci.info, ci.position);
							} else {
								System.out.println("\nNúmero de telefone inválido.");
							}
						}
						menuOption = 5;
						break;
					case 4:
						int i = 0;
						do {	
							System.out.println("\nDigite uma nova senha:");
							int password = scan.nextInt();
							System.out.println("\nDigite a sua nova senha novamente:");
							int checkPassword = scan.nextInt();
							
							if(checkPassword == password) {
								String info = Integer.toString(password);
								int position = 5;
								Update(info, position);
							} else {
								System.out.println("\nAs senhas não coincidem.");
							}
						} while(i != 1);
						menuOption = 5;
						break;
					case 5:
						System.out.println("\nVoltando ao menu principal...");
						break;
					default:
						System.out.println("\nOpção inválida.");
				}
			} while (menuOption != 5);
		} else {
			System.out.println("\nDados não foram encontrados.");
		}
	}
	
	public int GetData(String info, int position) {
		int valid = 0;
		try {
			File file = new File(getf.pathUsernames);
			Scanner scan = new Scanner(file);
			
			while(scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split(",", 0);
				String checkInfo = lineContent[position];
				
				if(info.equals(checkInfo)) {
					System.out.println("\nDados do Usuário\n");
					System.out.println("Nome de Usuário: " + lineContent[0]);
					System.out.println("Data de Nascimento: " + lineContent[1]);
					System.out.println("Número de Telefone: " + lineContent[2]);
					System.out.println("Número do Cpf: " + lineContent[3]);
					System.out.println("Email: " + lineContent[4]);
					valid = 1;
				}
			}
			if(valid == 0) {
				System.out.println("\nDados não foram encontrados.");
			}
			scan.close();
		} catch (Exception e) {
			System.out.println("\nOcorreu um erro. Tente novamente mais tarde.");
		}
		return valid;
	}

	public void RemoveUser() {
		String superUserCpf = "06116509592";
		System.out.println("\nAVISO!!!");
		System.out.println("TODOS OS DADOS DO USUÁRIO SERÃO REMOVIDOS");
		System.out.println("NÃO HÁ FORMA DE RECUPERAR OS DADOS REMOVIDOS");
		System.out.println("\nDigite o cpf do usuário ou 0 para sair:");
		cpf = scan.next();
		if(cpf.equals(superUserCpf)) {
			System.out.println("\nO SuperUser não pode ser removido.");
		} else {
			ci.info = cpf;
			ci.position = 3;
			int valid = ci.Check();
			if(valid == 1) {
				RemoveFromUsernames();
				RemoveFromAccounts();
				System.out.println("\nUsuário removido do sistema.");
			} else {
				System.out.println("\nDados não foram encontrados.");
			}
		}
	}

	public void RemoveFromUsernames() {
		Path path = Paths.get(getf.pathUsernames);
		Charset charset = StandardCharsets.UTF_8;
		
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File(getf.pathUsernames);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			for(String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				String checkCpf = lineContent[3];
				if(checkCpf.equals(cpf)) {
					
				} else {
					writer.writeNext(lineContent);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("\nOcorreu um erro. Tente novamente mais tarde.");
			System.out.println(e);
		}
	}
	
	public void RemoveFromAccounts() {
		Path path = Paths.get(getf.pathAccounts);
		Charset charset = StandardCharsets.UTF_8;
		
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File(getf.pathAccounts);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			for(String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				String checkCpf = lineContent[0];
				if(checkCpf.equals(cpf)) {
					
				} else {
					writer.writeNext(lineContent);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("\nOcorreu um erro. Tente novamente mais tarde.");
			System.out.println(e);
		}
	}
	
	public void Update(String info, int position) {
		Path path = Paths.get(getf.pathUsernames);
		Charset charset = StandardCharsets.UTF_8;
		
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File(getf.pathUsernames);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			for(String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				String checkInfo = lineContent[3];
				if(checkInfo.equals(cpf)) {
					lineContent[position] = info;
					String lineUsername = lineContent[0];
					String lineBirthDate = lineContent[1];
					String linePhoneNumber = lineContent[2];
					String lineCpf = lineContent[3];
					String lineEmail = lineContent[4];
					String linePassword = lineContent[5];
					String lineValue = lineContent[6];
					String [] data = {lineUsername, lineBirthDate, linePhoneNumber, lineCpf, lineEmail, linePassword, lineValue};
					writer.writeNext(data);
					System.out.println("\nAlteração realizada com sucesso.");
				} else {
					writer.writeNext(lineContent);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println("\nOcorreu um erro. Tente novamente mais tarde.");
		}
	}
}
