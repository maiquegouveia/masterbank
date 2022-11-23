package entities;
import java.util.Random;
import java.io.File;
import java.util.Scanner;
import entities.GetFiles;

public class GenerateRandomNumber {
	public int dataOption;
	public int position;
	public int isSecurity;
	GetFiles getf = new GetFiles();
	
	public String Random() {
		Random rand = new Random();
		String strRandomNumber = "";
		int upperbound;
		try {
			if (isSecurity == 1) {
				upperbound = 1000;
			} else {
				upperbound = 1000000000;
			}
			int intRandom = rand.nextInt(upperbound);
			strRandomNumber = Integer.toString(intRandom);
			if (dataOption == 1) {
				File file = new File(getf.pathUsernames);
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					String currentLine = scan.nextLine();
					currentLine = currentLine.replace("\"", "");
					String [] lineContent = currentLine.split("[,]", 0);
					if (lineContent[position].equals(strRandomNumber) || intRandom < 100) {
						intRandom = rand.nextInt(upperbound);
						strRandomNumber = Integer.toString(intRandom);
					} 
				}
				scan.close();
			} else if (dataOption == 2) {
				File file = new File(getf.pathAccounts);
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					String currentLine = scan.nextLine();
					currentLine = currentLine.replace("\"", "");
					String [] lineContent = currentLine.split("[,]", 0);
					if (lineContent[position].equals(strRandomNumber)) {
						intRandom = rand.nextInt(upperbound);
						strRandomNumber = Integer.toString(intRandom);
					}
				}
				scan.close();
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return strRandomNumber;
	}

}
