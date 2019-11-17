package ivan;

import java.io.IOException;

public class Runner {

    public static void main(String[] args) {
		FileIni fileINI = new FileIni();
		try {
			fileINI.setInformation("C://Users//karpi//Desktop//ИСР//tests//lab1//testsFiles//input.ini");
		} catch (IOException e) {
			System.out.println(e);
		}
		System.out.print(fileINI.toString());
    }
}
