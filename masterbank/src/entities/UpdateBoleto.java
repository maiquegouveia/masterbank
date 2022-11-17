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

public class UpdateBoleto {
	
	public void Update(String boletoCode) {
		Path path = Paths.get("C:\\Users\\Maique\\Desktop\\deposits.csv");
		Charset charset = StandardCharsets.UTF_8;
		try {
			List<String> lines = Files.readAllLines(path, charset);
			File file = new File("C:\\Users\\Maique\\Desktop\\deposits.csv");
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
