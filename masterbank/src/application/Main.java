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
			System.out.println("\nBem vindo(a) ao MasterBank!\n");
			System.out.println("1 - Acessar Conta");
			System.out.println("2 - Abrir Conta");
			System.out.println("3 - Sair");
			System.out.println("\nEscolha uma opcao -->");
			menuOption = scan.nextInt();
			
			switch(menuOption){
			case 1:
				System.out.println("\nDigite o nome de usuario:");
				lg.username = scan.next();
				System.out.println("\nDigite a senha:");
				lg.password = scan.next();
				
				int valid = lg.LoginCheck();
	
				if(valid == 1) {
					System.out.println("\nLogin feito com sucesso.");
					home.username = lg.username;
					home.password = lg.password;
					home.BankHome();
				} else {
					System.out.println("\nNome de usu�rio ou senha incorretos.");
				}
				break;
			case 2:
				int i = 0;
				System.out.println("\nDigite o nome de usuario:");
				username = scan.next();
				ci.info = username;
				ci.position = 0;
				int containsUsername = ci.Check();
				
				if (containsUsername == 1) {
					System.out.println("\nEste nome de usuario ja esta cadastrado.");
					break;
				}
				
				System.out.println("\nDigite o seu email:");
				email = scan.next();
				ci.info = email;
				ci.position = 4;
				int containsEmail = ci.Check();
				
				if (containsEmail == 1) {
					System.out.println("\nEste email ja esta cadastrado.");
					break;
				}
				
				System.out.println("\nDigite o seu numero de telefone com DDD:");
				phoneNumber = scan.next();
				int numberSize = phoneNumber.length();
				if (numberSize == 11) {
					ci.info = phoneNumber;
					ci.position = 2;
					int containsPhoneNumber = ci.Check();
					
					if (containsPhoneNumber == 1) {
						System.out.println("\nEste numero de telefone ja esta cadastrado.");
						break;
					}
				} else {
					System.out.println("\nNumero de telefone invalido.");
					break;
				}
				
				System.out.println("\nDigite o seu cpf APENAS NUMEROS:");
				cpf = scan.next();
				int cpfSize = cpf.length();
				if(cpfSize == 11) {
					ci.info = cpf;
					ci.position = 3;
					int containsCpf = ci.Check();
					
					if (containsCpf == 1) {
						System.out.println("\nEste numero de cpf ja esta cadastrado.");
						break;
					}
				} else {
					System.out.println("\nNumero de cpf invalido.");
					break;
				}
				
				System.out.println("\nData de Nascimento");
				System.out.println("Digite o dia do seu nascimento:");
				bd.day = scan.next();
				System.out.println("Digite o mes do seu nascimento:");
				bd.month = scan.next();
				System.out.println("Digite o ano do seu nascimento:");
				bd.year = scan.next();
				String strDate = bd.Date();
				
				if (strDate == null) {
					break;
				}
				
				do {	
					System.out.println("\nDigite a senha:");
					password = scan.nextInt();
					System.out.println("\nDigite a sua senha novamente:");
					checkPassword = scan.nextInt();
					
					if(checkPassword == password) {
						i = 1;
						Register register = new Register(username, email, password, strDate, cpf, phoneNumber);
					} else {
						System.out.println("\nAs senhas nao coincidem.");
					}
				} while(i != 1);
				break;
			case 3:
				System.out.println("\nSaindo...");
				break;
			default:
				System.out.println("\nOpcao invalida.");
			}
			
		} while (menuOption != 3);
		
		scan.close();
	}
}
