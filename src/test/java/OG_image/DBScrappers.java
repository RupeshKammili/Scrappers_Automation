package OG_image; 

import java.io.FileInputStream;
import java.io.File;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DBScrappers {
	
	@DataProvider()
	public static Object[] needTotestUrls( ) throws Exception {
		
		FileInputStream stream = new FileInputStream(new File("C:\\Users\\v-rkammili\\Desktop\\Scrapper_URLs.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(stream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		int row = sheet.getPhysicalNumberOfRows();
		Object[] c = new Object[row];
		for(int i=0; i<row; i++) {
			
			c[i] = sheet.getRow(i).getCell(0).getStringCellValue();
		}
		
		workbook.close();
		stream.close();
		return c;

	}

}
