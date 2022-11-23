package entities;
import java.io.File;
import java.util.Scanner;
import entities.GetFiles;

public class GetBoletoValue {
	GetFiles getf = new GetFiles();
	
	public String GetValue(String boletoCode) {
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

}
