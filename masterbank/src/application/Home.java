package application;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.opencsv.CSVWriter;
import java.util.List;
import java.util.Random;
import entities.SendPix;
import entities.GetBoletoValue;
import entities.GetBoletoStatus;
import entities.UpdateBoleto;
import entities.BoletoPayment;

public class Home {
	public String username;
	public String password;
	String email;
	String cpf;
	String birthDate;
	String phoneNumber;
	String balance;
	double doubleBalance;
	double doubleInvestments;
	String investments;
	String cardNumber;
	String expirationDate = "10/25";
	String securityCode;
	String pixNumber;
	Scanner scan = new Scanner(System.in);

	
	public void ReadFiles() {
		// Recebendo as informações da conta do usuário \\
		try {
			String filePath = "C:\\Users\\Maique\\Desktop\\usernames.csv";
			File file = new File(filePath);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				String lineUsername = lineContent[0];
				String lineBirthDate = lineContent[1];
				String linePhoneNumber = lineContent[2];
				String lineCpf = lineContent[3];
				String lineEmail = lineContent[4];
				
				if(username.equals(lineUsername)) {
					birthDate = lineBirthDate;
					phoneNumber = linePhoneNumber;
					cpf = lineCpf;
					email = lineEmail;
				} 
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// Recebendo informações bancárias do usuário \\
		try {
			String filePath = "C:\\Users\\Maique\\Desktop\\accounts.csv";
			File file = new File(filePath);
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				String lineCpf = lineContent[0];
				String lineBalance = lineContent[1];
				String lineInvestments = lineContent[2];
				String lineCardNumber = lineContent[3];
				String lineSecurityCode = lineContent[5];
				String linePixNumber = lineContent[6];
				
				if (cpf.equals(lineCpf)) {
					balance = lineBalance;
					investments = lineInvestments;
					cardNumber = lineCardNumber;
					securityCode = lineSecurityCode;
					pixNumber = linePixNumber;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void Update() {
		Path path = Paths.get("C:\\Users\\Maique\\Desktop\\accounts.csv");
		Charset charset = StandardCharsets.UTF_8;
		
		try {
			List<String> lines = Files.readAllLines(path, charset);
			String filePath = "C:\\Users\\Maique\\Desktop\\accounts.csv";
			File file = new File(filePath);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			String [] data = {cpf, balance, investments, cardNumber, expirationDate, securityCode, pixNumber};
			for (String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				if (lineContent[0].equals(data[0])) {
					writer.writeNext(data);
				} else {
					writer.writeNext(lineContent);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void Pix() {
		SendPix send = new SendPix();
		int menuOption;
		do {
			ReadFiles();
			doubleBalance = Double.parseDouble(balance);
			System.out.println("\nÁrea Pix");
			System.out.println("Saldo: R$ " + doubleBalance);
			System.out.println("\nSuas chaves Pix:");
			System.out.println("CPF: " + cpf);
			System.out.println("Código Pix: " + pixNumber);
			System.out.println("\n1 - Fazer Pix");
			System.out.println("2 - Sair\n");
			System.out.println("Escolha uma opção -->");
			menuOption = scan.nextInt();
			
			switch(menuOption) {
			case 1:
				System.out.println("\nDigite a chave pix da pessoa:");
				String sendPixKey = scan.next();
				send.pixKey = sendPixKey;
				send.doubleBalance = doubleBalance;
				doubleBalance = send.Send();
				balance = Double.toString(doubleBalance);
				Update();
				break;
			case 2:
				System.out.println("\nVoltando ao menu principal...");
				break;
			default:
				System.out.println("\nOpção inválida.");
			}
		} while (menuOption != 2);
	}
	
	public void DepositMoney() {
		Random rand = new Random();
		double amountToDeposit;
	
		System.out.println("Quanto você quer depositar na conta?");
		amountToDeposit = scan.nextDouble();
		
		int valid = 0;
		String strRandomNumber = "";
		try {
			String status = "0";
			int upperbound = 1000000000;
			int intRandom = rand.nextInt(upperbound);
			strRandomNumber = Integer.toString(intRandom);
			String value = Double.toString(amountToDeposit);
			
			String filePath = "C:\\Users\\Maique\\Desktop\\deposits.csv";
			File file = new File(filePath);
			FileWriter outputfile = new FileWriter(file, true);
			CSVWriter writer = new CSVWriter(outputfile);
			Scanner scanf = new Scanner(file);
			
			while (scanf.hasNextLine()) {
				String currentLine = scanf.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				
				if (lineContent[2].equals(strRandomNumber)) {
					intRandom = rand.nextInt(upperbound);
					strRandomNumber = Integer.toString(intRandom);
				} 
			}
			String [] data = {status, cpf, strRandomNumber, value};
			writer.writeNext(data);
			valid = 1;
			
			writer.close();
			scanf.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (valid == 1) {
			System.out.println("Boleto para depósito gerado com sucesso.");
			System.out.println("Código de Barras: " + strRandomNumber);
		} else {
			System.out.println("Ocorreu um erro. Tente novamente mais tarde.");
		}
	}
	
	public void TopUpPhone() {
		ReadFiles();
		doubleBalance = Double.parseDouble(balance);
		System.out.println("Recarga Celular");
		System.out.println("Saldo: R$ " + doubleBalance);
		System.out.println("\nDigite o número de telefone:");
		String number = scan.next();
		int numberSize = number.length();
		
		if (numberSize == 11) {
			System.out.println("Confirmar número de telefone:");
			String checkNumber = scan.next();
			if (checkNumber.equals(number)) {
				System.out.println("Digite o valor da recarga:");
				double value = scan.nextDouble();
				if (value > doubleBalance) {
					System.out.println("\nValor da recarga maior que saldo da conta.");
				} else {
					doubleBalance = doubleBalance - value;
					balance = Double.toString(doubleBalance);
					Update();
					System.out.println("\nRecarga realizada com sucesso.");
				}
			} else {
				System.out.println("\nNúmeros não coincidem.");
			}
		} else {
			System.out.println("\nNúmero de telefone inválido.");
		}
		
	}

	public void payBoleto() {
		GetBoletoStatus getstatus = new GetBoletoStatus();
		GetBoletoValue getvalue = new GetBoletoValue();
		BoletoPayment payment = new BoletoPayment();
		UpdateBoleto upboleto = new UpdateBoleto();
		
		ReadFiles();
		doubleBalance = Double.parseDouble(balance);
		
		System.out.println("\nPagamento de Boletos");
		System.out.println("Saldo: R$ " + doubleBalance);
		System.out.println("\nDigite o código de barras do boleto:");
		String boletoCode = scan.next();
		int status = getstatus.GetStatus(boletoCode);
		
		if (status == 0) {
			String strValue = getvalue.GetValue(boletoCode);
			Double value = Double.parseDouble(strValue);
			int continueOption;
			do {
				System.out.println("\nO valor deste boleto é R$ " + strValue);
				System.out.println("Deseja proseguir com o pagamento?");
				System.out.println("\n1 - Sim");
				System.out.println("2 - Não");
				System.out.println("\nEscolha uma opção -->");
				continueOption = scan.nextInt();
				
				switch(continueOption) {
				case 1:
					if (value > doubleBalance) {
						System.out.println("\nO valor do boleto é maior que o saldo da conta.");
						continueOption = 2;
						break;
					} else {
						int valid = payment.Payment(boletoCode);
						if (valid == 1) {
							doubleBalance = doubleBalance - value;
							balance = Double.toString(doubleBalance);
							Update();
							upboleto.Update(boletoCode);
							System.out.println("\nO pagamento do boleto foi realizado com sucesso.");
							continueOption = 2;
						} else {
							System.out.println("\nFalha no pagamento. Tente novamente mais tarde.");
							continueOption = 2;
						}
						
					}
					break;
				case 2:
					System.out.println("\nVoltando ao menu principal...");
					break;
				default:
					System.out.println("\nOpção inválida.");
				}
			} while (continueOption != 2);
			
		} else if (status == 1) {
			System.out.println("\nEste boleto já foi pago.");
		} else {
			System.out.println("\nBoleto não encontrado.");
		}
	}
	
	public void Invest() {
		ReadFiles();
		doubleBalance = Double.parseDouble(balance);
		doubleInvestments = Double.parseDouble(investments);
		int option;
		do {
			System.out.println("\nMeus Investimentos");
			System.out.println("Dinheiro Investido: R$ " + doubleInvestments);
			System.out.println("Saldo: R$ " + doubleBalance);
			System.out.println("\n1 - Fazer Investimento");
			System.out.println("2 - Retirar Investimento");
			System.out.println("3 - Sair");
			System.out.println("\nEscolha uma opção -->");
			option = scan.nextInt();
			double amount;
			switch(option) {
				case 1:
					System.out.println("\nQuanto você deseja investir?");
					amount = scan.nextDouble();
					if(amount > doubleBalance) {
						System.out.println("\nSem saldo suficiente para realizar a operação.");
					} else {
						int check = 0;
						try {
							doubleBalance = doubleBalance - amount;
							balance = Double.toString(doubleBalance);
							doubleInvestments = doubleInvestments + amount;
							investments = Double.toString(doubleInvestments);
						} catch (Exception e){
							System.out.println("\nOcorreu um erro. Tente novamente mais tarde.");
							check = 1;
						} finally {
							if(check == 0) {
								Update();
								System.out.println("\nOperação realizada com sucesso.");
							}
						}
					}
					break;
				case 2:
					System.out.println("\nQuanto você deseja tirar de investimento?");
					amount = scan.nextDouble();
					if(amount > doubleInvestments) {
						System.out.println("\nSem saldo suficiente para realizar a operação.");
					} else {
						int check = 0;
						try {
							doubleBalance = doubleBalance + amount;
							balance = Double.toString(doubleBalance);
							doubleInvestments = doubleInvestments - amount;
							investments = Double.toString(doubleInvestments);
						} catch (Exception e) {
							System.out.println("\nOcorreu um erro. Tente novamente mais tarde.");
							check = 1;
						} finally {
							if(check == 0) {
								Update();
								System.out.println("\nOperação realizada com sucesso.");
							}
						}
					}
					break;
				case 3:
					System.out.println("\nVoltando ao menu principal...");
					break;
				default:
					System.out.println("\nOpção inválida.");
			}
		} while (option != 3);
	}
	
	public void  BankHome(){
		int menuOption;
		do {	
			ReadFiles();
			doubleBalance = Double.parseDouble(balance);
			System.out.println("\nOlá, " + username + "!");
			System.out.println("Saldo: R$ " + doubleBalance + "\n");
			System.out.println("1 - Pagamentos");
			System.out.println("2 - Cartão");
			System.out.println("3 - Pix");
			System.out.println("4 - Investimentos");
			System.out.println("5 - Recarga");
			System.out.println("6 - Depósito");
			System.out.println("7 - Sair");
			System.out.println("\nEscolha uma opção -->");
			menuOption = scan.nextInt();
			
			switch(menuOption) {
			case 1:
				payBoleto();
				break;
			case 2:
				System.out.println("\nInformações do seu cartão:");
				System.out.println("Número do Cartão: " + cardNumber);
				System.out.println("Data de Vencimento: " + expirationDate);
				System.out.println("Código de Verificação: " + securityCode);
				break;
			case 3:
				Pix();
				break;
			case 4:
				Invest();
				break;
			case 5:
				TopUpPhone();
				break;
			case 6:
				DepositMoney();
				break;
			case 7:
				break;
			default:
				System.out.println("Opção inválida.");
			}
		} while (menuOption != 7);
	}
}
