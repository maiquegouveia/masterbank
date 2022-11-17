package entities;

import java.io.File;
import java.util.Scanner;

public class GetBoletoStatus {
	
	public int GetStatus(String boletoCode) {
		String filePath = "C:\\Users\\Aluno\\eclipse-workspace\\masterbank_final\\src\\data\\deposits.csv";
		int status = 2;
		try{
			File file = new File(filePath);
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

}
