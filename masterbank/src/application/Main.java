package application;
import java.util.Scanner;
import entities.Register;
import entities.Login;
import entities.CheckIfExists;
import entities.BirthDate;
import application.Home;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Login lg = new Login();
		CheckIfExists ci = new CheckIfExists();
		BirthDate bd = new BirthDate();
		Home home = new Home();
		
		String username;
		String email;
		String cpf;
		String phoneNumber;
		int password;
		int checkPassword;
		int menuOption;
		
		do {
			System.out.println("\nBem vindo(a) ao MasterBank!");
			System.out.println("1 - Acessar Conta");
			System.out.println("2 - Abrir Conta");
			System.out.println("3 - Sair");
			System.out.println("Escolha uma opção -->");
			menuOption = scan.nextInt();
			
			switch(menuOption){
			case 1:
				System.out.println("Digite o nome de usuário:");
				lg.username = scan.next();
				System.out.println("Digite a senha:");
				lg.password = scan.next();
				
				int valid = lg.LoginCheck();
	
				if(valid == 1) {
					System.out.println("Login feito com sucesso.");
					home.username = lg.username;
					home.password = lg.password;
					home.BankHome();
				} else {
					System.out.println("Nome de usuário ou senha incorretos.");
				}
				break;
			case 2:
				int i = 0;
				System.out.println("Digite o nome de usuário:");
				username = scan.next();
				ci.info = username;
				ci.position = 0;
				int containsUsername = ci.Check();
				
				if (containsUsername == 1) {
					System.out.println("Este nome de usuário já está cadastrado.");
					break;
				}
				
				System.out.println("Digite o seu email:");
				email = scan.next();
				ci.info = email;
				ci.position = 4;
				int containsEmail = ci.Check();
				
				if (containsEmail == 1) {
					System.out.println("Este email já está cadastrado.");
					break;
				}
				
				System.out.println("Digite o seu número de telefone com DDD:");
				phoneNumber = scan.next();
				int numberSize = phoneNumber.length();
				if (numberSize == 11) {
					ci.info = phoneNumber;
					ci.position = 2;
					int containsPhoneNumber = ci.Check();
					
					if (containsPhoneNumber == 1) {
						System.out.println("Este número de telefone já está cadastrado.");
						break;
					}
				} else {
					System.out.println("Número de telefone inválido.");
					break;
				}
				
				System.out.println("Digite o seu cpf APENAS NÚMEROS:");
				cpf = scan.next();
				int cpfSize = cpf.length();
				if(cpfSize == 11) {
					ci.info = cpf;
					ci.position = 3;
					int containsCpf = ci.Check();
					
					if (containsCpf == 1) {
						System.out.println("Este número de cpf já está cadastrado.");
						break;
					}
				} else {
					System.out.println("Número de cpf inválido.");
					break;
				}
				
				System.out.println("Data de Nascimento");
				System.out.println("Digite o dia do seu nascimento:");
				bd.day = scan.next();
				System.out.println("Digite o mês do seu nascimento:");
				bd.month = scan.next();
				System.out.println("Digite o ano do seu nascimento:");
				bd.year = scan.next();
				String strDate = bd.Date();
				
				if (strDate == null) {
					break;
				}
				
				do {	
					System.out.println("Digite a senha:");
					password = scan.nextInt();
					System.out.println("Digite a sua senha novamente:");
					checkPassword = scan.nextInt();
					
					if(checkPassword == password) {
						i = 1;
						Register register = new Register(username, email, password, strDate, cpf, phoneNumber);
					} else {
						System.out.println("As senhas não coincidem.");
					}
				} while(i != 1);
				break;
			case 3:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida.");
			}
			
		} while (menuOption != 3);
		
		scan.close();
	}
}
