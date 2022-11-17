package entities;

import java.io.File;
import java.util.Scanner;

public class GetBoletoCpf {
	
	public String GetCpf(String boletoCode) {
		String cpf = "";
		String filePath = "C:\\Users\\Aluno\\eclipse-workspace\\masterbank_final\\src\\data\\deposits.csv";
		try{
			File file = new File(filePath);
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String currentLine = scan.nextLine();
				currentLine = currentLine.replace("\"", "");
				String [] lineContent = currentLine.split(",", 0);
				if (lineContent[2].equals(boletoCode)) {
					cpf = lineContent[1];
					scan.close();
					return cpf;
				}
			}
			scan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return cpf;
	}

}
