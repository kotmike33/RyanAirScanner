package Excel;

import Config.ConfigVariables;
import DEBUG.Debug;
import RyanAir.RyanAirActions;
import Telegram.MyBot;
import UsefulMethods.UsefulMethods;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ExcelMethods extends RyanAirActions {
	private static final String FILE_PATH = ConfigVariables.PROJECT_PATH + "src/main/resources/RyanAir.xlsx";
	private static String sheetName;

	public void putDataToExcel(String flightPrice, LocalDate flightDate, int rowNum) throws IOException {
		Debug debug = new Debug();
	//	try () {
			XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_PATH));
			UsefulMethods methods = new UsefulMethods();
			sheetName = today  + methods.getAbbreviation(
					ConfigVariables.FROM_COUNTRY,
					ConfigVariables.FROM_AIRPORT,
					ConfigVariables.TO_COUNTRY,
					ConfigVariables.TO_AIRPORT
			);
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetName);
			}
			workbook.setForceFormulaRecalculation(true);

			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			dateCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle price1CellStyle = workbook.createCellStyle();
			price1CellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			price1CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle price2CellStyle = workbook.createCellStyle();
			price2CellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
			price2CellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			flightPrice = flightPrice.replace(",", "");
			Row dateRow = sheet.getRow(2);
			if (dateRow == null) {
				dateRow = sheet.createRow(2);
			}
			Row priceRow = sheet.getRow(rowNum);
			if (priceRow == null) {
				priceRow = sheet.createRow(rowNum);
			}
			Cell dateCell;
			int x = 0;
			while (true) {
				dateCell = dateRow.getCell(x);
				if (dateCell == null) {
					dateCell = dateRow.createCell(x);
				}
				if (dateCell.getCellType().equals(CellType.BLANK) || dateCell.getStringCellValue().equals(flightDate.toString())) {
					break;
				}
				x++;
			}

			sheet.setColumnWidth(x, 15 * 256);
			dateCell.setCellValue(flightDate.toString());
			dateCell.setCellStyle(dateCellStyle);
			debug.testDebug("Putting the data to excel sheet x = " + x + ", y = " + rowNum);

			Cell priceCell = priceRow.getCell(x);
			if (priceCell == null) {
				priceCell = priceRow.createCell(x);
			}
			priceCell.setCellValue(Integer.parseInt(flightPrice));
			if (rowNum == 3) {
				priceCell.setCellStyle(price1CellStyle);
			} else {
				priceCell.setCellStyle(price2CellStyle);
			}

			try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
				workbook.write(fileOut);
			}
	//	} catch (Exception e) {
	//		debug.errorMessageDebug(e.getMessage());
	//	}
	}

	public void reviewCollectedData() throws IOException {
		String reportMessage = "";
		Debug debug = new Debug();
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_PATH));
		UsefulMethods methods = new UsefulMethods();
		sheetName = today + methods.getAbbreviation(
				ConfigVariables.FROM_COUNTRY,
				ConfigVariables.FROM_AIRPORT,
				ConfigVariables.TO_COUNTRY,
				ConfigVariables.TO_AIRPORT
		);
	//	sheetName = "2023-07-15SAPM"; //DEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUGDEBUG
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}
		Row dateRow = sheet.getRow(2);
		if (dateRow == null) {
			debug.warningDebug("reviewCollectedData: dateRow is null");
			throw new IOException("dateRow is null");
		}
		Row price1Row = sheet.getRow(3);
		Row price2Row = sheet.getRow(4);

		List <Double> price1List = new ArrayList<>();
		List <Double> price2List = new ArrayList<>();
		List <LocalDate> date1List = new ArrayList<>();
		List <LocalDate> date2List = new ArrayList<>();

		for (int i=0;i>-1;i++){
			Cell dateCell = dateRow.getCell(i);
			if(i>300){
				debug.errorMessageDebug("CHECK DATEROW EMPTY CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}
			if (dateCell == null){
				debug.functionDebug("out of dates. breaking");
				break;
			}
			LocalDate date1 = LocalDate.parse(dateCell.getStringCellValue());
			LocalDate date2 = null;

			Cell price1Cell = price1Row.getCell(i);
			double price1 = 0;
			try{
				price1 = price1Cell.getNumericCellValue();
			}catch (Exception CellIsEmptyException){
				debug.functionDebug("Exception during loading the price1 cell value");
			}

			LocalDate date2Temp = null;
			if(price1!=0) {
				double minPrice2 = Double.MAX_VALUE;

				for (int x = 0; x>-1; x++) {
					dateCell = dateRow.getCell(x);
					if (dateCell == null){
						debug.functionDebug("dateCell == null, x = " + x);
						break;
					}
					date2Temp = LocalDate.parse(dateCell.getStringCellValue());
					if(ChronoUnit.DAYS.between(date1,date2Temp) >= ConfigVariables.DESIRED_DAYS_NUM_FROM) {
						if (ChronoUnit.DAYS.between(date1, date2Temp) > ConfigVariables.DESIRED_DAYS_NUM_TO) {
							break;
						}

						Cell price2Cell = price2Row.getCell(x);
						double price2;
						try {
							price2 = price2Cell.getNumericCellValue();
							if(price2<minPrice2){
								minPrice2 = price2;
								date2 = date2Temp;
							}
						} catch (Exception CellIsEmptyException) {
							price2 = 0;
						}
					}
				}
				if(minPrice2<Double.MAX_VALUE){
					price1List.add(price1);
					date1List.add(date1);
					price2List.add(minPrice2);
					date2List.add(date2);
				}else {
					debug.functionDebug("minPrice2 value is MAX");
				}
			}else {
				debug.functionDebug("Failed to load the price1 cell value");
			}
		}

		List <Double> totalPriceList = new ArrayList<>(); //linked to price 1 list
		for (int i=0;i<price1List.size();i++){
			totalPriceList.add(price1List.get(i)+price2List.get(i));
		}
		optimizeFinalFlightsNumber(totalPriceList, 6.0);

		List <Double> finalPrice1List = new ArrayList<>();
		List <Double> finalPrice2List = new ArrayList<>();
		List <LocalDate> finalDate1List = new ArrayList<>();
		List <LocalDate> finalDate2List = new ArrayList<>();
		List <Double> finalTotalPriceList = new ArrayList<>();

		for(int i=0;i<totalPriceList.size();i++){
			if(totalPriceList.get(i).intValue() != 0){
				finalPrice1List.add(price1List.get(i));
				finalPrice2List.add(price2List.get(i));
				finalDate1List.add(date1List.get(i));
				finalDate2List.add(date2List.get(i));
				finalTotalPriceList.add(price1List.get(i)+price2List.get(i));
			}
		}

		//DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG
		if(finalPrice1List.size()>0) {
			ExcelMethods excelMethods = new ExcelMethods();
			excelMethods.debugLists(finalPrice1List, finalPrice2List, finalDate1List, finalDate2List);
		}
		//DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG_DEBUG

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		MyBot bot = new MyBot();
		bot.sendLinkToChannel("✈\uFE0F✈\uFE0F  "+ "[RyanAir Search Link](" + RyanAirActions.searchURL + ")" + "  ✈\uFE0F✈\uFE0F");

		for (int i = 0; i < finalPrice1List.size(); i++) {
			reportMessage += ( "*Flight number*\\: " + methods.numberToEmoji(i + 1) + "\n\n");
			reportMessage += ("From *" + ConfigVariables.FROM_AIRPORT+ "*" + methods.getFlagEmoji(ConfigVariables.FROM_COUNTRY) +
					" ➡ To  *" + ConfigVariables.TO_AIRPORT + "*" + methods.getFlagEmoji(ConfigVariables.TO_COUNTRY) +
					" on\\: " +
					 "*" + finalDate1List.get(i).format(formatter).replace("-", "\\-") + "*" +
					" 	Price\\: " + "*" + String.valueOf(finalPrice1List.get(i).intValue()).replace(".", "\\.") + "*" + "\n");
			reportMessage += ("From *" + ConfigVariables.TO_AIRPORT + "*" + methods.getFlagEmoji(ConfigVariables.TO_COUNTRY) +
					" ➡ To *" + ConfigVariables.FROM_AIRPORT + "*" + methods.getFlagEmoji(ConfigVariables.FROM_COUNTRY) +
					" on\\: " +
					"*" + finalDate2List.get(i).format(formatter).replace("-", "\\-") + "*" +
					" 	Price\\: " + "*" + String.valueOf(finalPrice2List.get(i).intValue()).replace(".", "\\.") + "*" + "\n" + "\n");
			reportMessage += ("Total price\\: " + "*" + String.valueOf(finalTotalPriceList.get(i).intValue()).replace(".", "\\.") + "*" + "\n");
			debug.functionDebug(reportMessage);
			bot.sendMessageToChannel(reportMessage);
			reportMessage = "";
		}
	}

	private void optimizeFinalFlightsNumber(List <Double> totalPriceList, Double matchFactor) throws IOException {
		Debug debug = new Debug();
		int nullCounter = 0;
		Double minTotalPrice = Double.MAX_VALUE;
		for (int i=0;i<totalPriceList.size();i++){
			if (minTotalPrice>totalPriceList.get(i) && totalPriceList.get(i).intValue()!=0){
				minTotalPrice=totalPriceList.get(i);
			}
		}
		for (int i=0;i<totalPriceList.size();i++){
			if(totalPriceList.get(i)>(minTotalPrice*matchFactor)){
				totalPriceList.set(i, (double) 0);
			}
			if(totalPriceList.get(i).intValue() == 0){
				nullCounter++;
			}
		}
		debug.functionDebug("nullCounter = " + nullCounter);

		if(totalPriceList.size()-nullCounter>5){
			debug.functionDebug("totalPriceList.size() is " + (totalPriceList.size()-nullCounter));
			debug.functionDebug(" starting optimizing with the new matchFactor = " + (matchFactor-0.2));
			optimizeFinalFlightsNumber(totalPriceList,(matchFactor-0.05));
		}
	}
	private void debugLists(List<Double> price1List, List<Double> price2List, List<LocalDate> date1List, List<LocalDate> date2List) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_PATH));
		UsefulMethods methods = new UsefulMethods();
		sheetName = today + methods.getAbbreviation(
				ConfigVariables.FROM_COUNTRY,
				ConfigVariables.FROM_AIRPORT,
				ConfigVariables.TO_COUNTRY,
				ConfigVariables.TO_AIRPORT
		);
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}

		int rowIndex = 11;
		CellStyle style = workbook.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Row date1Row = sheet.createRow(rowIndex);
		for (int i = 0; i < date1List.size(); i++) {
			Cell cell = date1Row.createCell(i);
			cell.setCellValue(date1List.get(i).toString());
		}

		Row price1Row = sheet.createRow(++rowIndex);
		for (int i = 0; i < price1List.size(); i++) {
			Cell cell = price1Row.createCell(i);
			cell.setCellValue(price1List.get(i).intValue());
		}

		Row date2Row = sheet.createRow((rowIndex+=2));
		for (int i = 0; i < date2List.size(); i++) {
			Cell cell = date2Row.createCell(i);
			cell.setCellValue(date2List.get(i).toString());
		}

		Row price2Row = sheet.createRow(++rowIndex);
		for (int i = 0; i < price2List.size(); i++) {
			Cell cell = price2Row.createCell(i);
			cell.setCellValue(price2List.get(i).intValue());
		}

		try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
			workbook.write(outputStream);
		}
	}
	@Test
	public void test() throws IOException {
		ExcelMethods excelMethods = new ExcelMethods();
		excelMethods.reviewCollectedData();
	}
}
