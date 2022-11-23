package entities;
import java.io.File;
import java.util.Scanner;
import entities.GetFiles;

public class GetBoletoStatus {
	GetFiles getf = new GetFiles();
	
	public int GetStatus(String boletoCode) {
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

}
