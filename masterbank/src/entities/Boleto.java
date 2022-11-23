package entities;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import com.opencsv.CSVWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import entities.GetFiles;

public class Boleto {
	public String boletoCode;
	String cpf;
	String balance;
	String investments;
	String cardNumber;
	String expirationDate = "10/25";
	String securityCode;
	String pixNumber;
	String email;
	GetFiles getf = new GetFiles();
	
	public int GetStatus() {
		int status = 2;
		try{
			File file = new File(getf.pathDeposits);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split(",", 0);
				if (lineContent[2].equals(boletoCode)) {
					String strStatus = lineContent[0];
					status = Integer.parseInt(strStatus);
					scan.close();
					return status;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
	
	public String GetValue() {
		String value = "";
		try {
			File file = new File(getf.pathDeposits);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split(",", 0);
				if (lineContent[2].equals(boletoCode)) {
					value = lineContent[3];
					scan.close();
					return value;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return value;
	}

	public void GetCpf() {
		try{
			File file = new File(getf.pathDeposits);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split(",", 0);
				if (lineContent[2].equals(boletoCode)) {
					cpf = lineContent[1];
					scan.close();
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String Payment() {
		GetCpf();
		Path path = Paths.get(getf.pathAccounts);
		Charset charset = StandardCharsets.UTF_8;
		String valid = "0";
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File(getf.pathAccounts);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			for (String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				String checkCpf = lineContent[0];
				balance = lineContent[1];
				investments = lineContent[2];
				cardNumber = lineContent[3];
				securityCode = lineContent[5];
				pixNumber = lineContent[6];
				email = lineContent[7];
				
				if (cpf.equals(checkCpf)) {
					String strValue = GetValue();
					Double currentBalance = Double.parseDouble(balance);
					Double value = Double.parseDouble(strValue);
					currentBalance = currentBalance + value;
					balance = Double.toString(currentBalance);
					String [] data = {cpf, balance, investments, cardNumber, expirationDate, securityCode, pixNumber, email};
					writer.writeNext(data);
					valid = "1";
					writer.close();
					return valid;
				} else {
					writer.writeNext(lineContent);
				}
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return valid;
	}

	public void UpdateBoleto(){
		Path path = Paths.get(getf.pathDeposits);
		Charset charset = StandardCharsets.UTF_8;
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File(getf.pathDeposits);
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			for (String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				String status = lineContent[0];
				String cpf = lineContent[1];
				String depositCode = lineContent[2];
				String value = lineContent[3];
				if (depositCode.equals(boletoCode)) {
					status = "1";
					String [] data = {status, cpf, depositCode, value};
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

}
