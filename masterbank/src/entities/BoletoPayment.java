package entities;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.opencsv.CSVWriter;
import entities.GetBoletoCpf;
import entities.GetBoletoValue;

public class BoletoPayment {
	
	public int Payment(String boletoCode) {
		GetBoletoValue getvalue = new GetBoletoValue();
		GetBoletoCpf getcpf = new GetBoletoCpf();
		String cpf = getcpf.GetCpf(boletoCode);
		
		Path path = Paths.get("C:\\Users\\Maique\\Desktop\\accounts.csv");
		Charset charset = StandardCharsets.UTF_8;
		int valid = 0;
		
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File("C:\\Users\\Maique\\Desktop\\accounts.csv");
			FileWriter fwriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fwriter);
			for (String line : lines) {
				line = line.replace("\"", "");
				String [] lineContent = line.split(",", 0);
				String checkCpf = lineContent[0];
				String balance = lineContent[1];
				String investments = lineContent[2];
				String cardNumber = lineContent[3];
				String expirationDate = lineContent[4];
				String securityCode = lineContent[5];
				String pixNumber = lineContent[6];
				
				if (cpf.equals(checkCpf)) {
					String strValue = getvalue.GetValue(boletoCode);
					Double currentBalance = Double.parseDouble(balance);
					Double value = Double.parseDouble(strValue);
					currentBalance = currentBalance + value;
					balance = Double.toString(currentBalance);
					String [] data = {cpf, balance, investments, cardNumber, expirationDate, securityCode, pixNumber};
					writer.writeNext(data);
					valid = 1;
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

}
