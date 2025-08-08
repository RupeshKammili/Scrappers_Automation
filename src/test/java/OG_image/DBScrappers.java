package OG_image;

import java.io.FileInputStream;
import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DBScrappers {

	@DataProvider()
	public static Object[] needTotestUrls() throws Exception {

		File file = new File("./azure-deploy/wwwroot/uploads/Phase2URLs.xlsx");
		FileInputStream stream = new FileInputStream(file);

		Workbook workbook;
		if (file.getName().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(stream); // For .xlsx
		} else if (file.getName().endsWith(".xls")) {
			workbook = new HSSFWorkbook(stream); // For .xls
		} else {
			throw new IllegalArgumentException("Unsupported file type: " + file.getName());
		}

		Sheet sheet = workbook.getSheetAt(0);
		int row = sheet.getPhysicalNumberOfRows();
		Object[] c = new Object[row];

		for (int i = 0; i < row; i++) {
			c[i] = sheet.getRow(i).getCell(0).getStringCellValue();
			System.out.println(c[i]);
		}

		workbook.close();
		stream.close();
		return c;

	}

}
