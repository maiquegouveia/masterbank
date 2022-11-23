package entities;
import java.io.File;
import java.util.Scanner;
import entities.GetFiles;

public class GetBoletoCpf {
	GetFiles getf = new GetFiles();
	
	public String GetCpf(String boletoCode) {
		String cpf = "";
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
