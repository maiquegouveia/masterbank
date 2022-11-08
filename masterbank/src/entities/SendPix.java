package entities;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import com.opencsv.CSVWriter;

public class SendPix {
	public String pixKey;
	public double doubleBalance;
	double currentBalance;
	String cpf;
	String balance;
	String investments;
	String newCardNumber;
	String expirationDate;
	String newSecurityCode;
	String pixNumber;
	Scanner scan = new Scanner(System.in);
	
	public double Send() {
		String [] lineContent;
		int valid = 0;
		
		try {
			String filePath = "C:\\Users\\Maique\\Desktop\\accounts.csv";
			File file = new File(filePath);
			Scanner scanf = new Scanner(file);
			
			while(scanf.hasNextLine()) {
				String currentLine = scanf.nextLine();
				currentLine = currentLine.replace("\"", "");
				lineContent = currentLine.split("[,]", 0);
				String checkCodPix = lineContent[6];
				String checkCpf = lineContent[0];
				
				if (pixKey.equals(checkCodPix) || pixKey.equals(checkCpf)) {
					cpf = lineContent[0];
					balance = lineContent[1];
					investments = lineContent[2];
					newCardNumber = lineContent[3];
					expirationDate = lineContent[4];
					newSecurityCode = lineContent[5];
					pixNumber = lineContent[6];
					valid = 1;
				}
			}
			scanf.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (valid == 1) {
			System.out.println("Qual o valor da transferência?");
			double amountToSend = scan.nextDouble();
			if (amountToSend > doubleBalance) {
				System.out.println("Valor maior que saldo da conta.");
			} else {
				currentBalance = Double.parseDouble(balance);
				currentBalance = currentBalance + amountToSend;
				balance = Double.toString(currentBalance);
				doubleBalance = doubleBalance - amountToSend;
				sendPix();
				System.out.println("Transferência pix realizada com sucesso.");
			}
		}
		return doubleBalance;
	}
	
	public void sendPix() {
		Path path = Paths.get("C:\\Users\\Maique\\Desktop\\accounts.csv");
		Charset charset = StandardCharsets.UTF_8;
		
		try { 
			List<String> lines = Files.readAllLines(path, charset);
			String filePath = "C:\\Users\\Maique\\Desktop\\accounts.csv";
			File file = new File(filePath);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			String [] newData = {cpf, balance, investments, newCardNumber, expirationDate, newSecurityCode, pixNumber};
			for (String line : lines) {
				String currentLine = line;
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split("[,]", 0);
				if (lineContent[0].equals(newData[0])) {
					writer.writeNext(newData);
				} else {
					writer.writeNext(lineContent);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
