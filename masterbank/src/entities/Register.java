package entities;
import java.io.File;
import java.io.FileWriter;
import com.opencsv.CSVWriter;
import java.util.Scanner;
import entities.GenerateRandomNumber;
import entities.GetFiles;

public class Register {
	private String username;
	private String email;
	private int password;
	private String birthDate;
	private String cpf;
	private String phoneNumber;
	String value = "1";
	GetFiles getf = new GetFiles();
	
	public Register(String username, String email, int password, String birthDate, String cpf, String phoneNumber) {
		this.username = username;
		this.email = email;
		this.password = password;
		String strPassword;
		strPassword = Integer.toString(password);
		this.birthDate = birthDate;
		this.cpf = cpf;
		this.phoneNumber = phoneNumber;
	
		// Aqui estamos iniciando as variaveis para a conta bancaria do usuario //
		String balance = "0";
		String investments = "0";
		String newCardNumber = "";
		String expirationDate = "10/25";
		String newSecurityCode = "";
		String pixNumber = "";
		int valid = 0;
		
		// Aqui estamos escrevendo as informacoes pessoas do usuario no arquivo //
		try {
			File file = new File(getf.pathUsernames);
			FileWriter outputfile = new FileWriter(file, true);
			CSVWriter writer = new CSVWriter(outputfile);
			String [] data = {username, birthDate, phoneNumber, cpf, email, strPassword, value};
			writer.writeNext(data);
			writer.close();
			valid = 1;
		} catch (Exception e) {
			System.out.println(e);
			valid = 0;
		}
		
		// Aqui estamos iniciando a conta bancaria do usuario e escrevendo no arquivo //
		if (valid == 1) {
			GenerateRandomNumber random = new GenerateRandomNumber();
			random.dataOption = 2;
			random.position = 6;
			random.isSecurity = 0;
			pixNumber = random.Random();
			random.dataOption = 2;
			random.position = 3;
			random.isSecurity = 0;
			newCardNumber = random.Random();
			random.dataOption = 2;
			random.position = 5;
			random.isSecurity = 1;
			newSecurityCode = random.Random();
			try {
				File file = new File(getf.pathAccounts);
				FileWriter outputfile = new FileWriter(file, true);
				CSVWriter writer = new CSVWriter(outputfile);
				String [] data = {cpf, balance, investments, newCardNumber, expirationDate, newSecurityCode, pixNumber, email};
				writer.writeNext(data);
				writer.close();
				valid = 1;
			} catch (Exception e) {
				System.out.println(e);
				valid = 0;
			}
			if (valid == 1) {
				System.out.println("\nCadastro realizado.");
			} else {
				System.out.println("\nCadastro não realizado.");
			}
		} else {
			System.out.println("\nCadastro não realizado.");
		}
		
	}

}
