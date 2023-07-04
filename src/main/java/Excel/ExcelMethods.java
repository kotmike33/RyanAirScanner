package Excel;

import Config.ConfigVariables;
import DEBUG.Debug;
import RyanAir.RyanAirActions;
import UsefulMethods.UsefulMethods;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ExcelMethods extends RyanAirActions {
	private static final String FILE_PATH = "C:/Users/kotmi/IdeaProjects/RyanAirScanner/src/main/resources/RyanAir.xlsx";
	private static String sheetName;

	public void putDataToExcel(String flightPrice, LocalDate flightDate, int rowNum) throws IOException {
		Debug debug = new Debug();
		try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_PATH))) {
			UsefulMethods methods = new UsefulMethods();
			sheetName = today + " " + methods.getAbbreviation(
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
		} catch (Exception e) {
			debug.errorMessageDebug(e.getMessage());
		}
	}
	@Test
	public void reviewCollectedData() throws IOException {
		Debug debug = new Debug();
		//try{
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(FILE_PATH));

		UsefulMethods methods = new UsefulMethods();
		sheetName = today + " " + methods.getAbbreviation(
				ConfigVariables.FROM_COUNTRY,
				ConfigVariables.FROM_AIRPORT,
				ConfigVariables.TO_COUNTRY,
				ConfigVariables.TO_AIRPORT
		);

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

		List<Integer> potentialLowCostFlights1 = new ArrayList<>();
		List<LocalDate> potentialLowCostFlightsDates1 = new ArrayList<>();
		List<Integer> potentialLowCostFlights2 = new ArrayList<>();
		List<LocalDate> potentialLowCostFlightsDates2 = new ArrayList<>();
		int minPrice1 = 999999999;
		int minPrice2 = 999999999;
		String minPrice1Date = "";
		String minPrice2Date = "";
		Cell dateTempCell = dateRow.getCell(0);
		LocalDate daysCounter1 = LocalDate.parse(dateTempCell.getStringCellValue());
		LocalDate daysCounter2 = LocalDate.parse(dateTempCell.getStringCellValue());
		for (int i = 0; i > -1; i++) {
			Cell price1Cell = price1Row.getCell(i);
			Cell price2Cell = price2Row.getCell(i);
			Cell dateCell = dateRow.getCell(i);
			if (dateCell == null) {
				break;
			}
			if (price1Cell != null) {
				if (price1Cell.getNumericCellValue() < minPrice1) {
					minPrice1 = (int) price1Cell.getNumericCellValue();
					minPrice1Date = dateCell.getStringCellValue();
				}
				if (ChronoUnit.DAYS.between(daysCounter1,LocalDate.parse(dateCell.getStringCellValue()))
						> ConfigVariables.SCANNING_FREQUENCY_DAYS) {
					if(minPrice1!=999999999) {
						daysCounter1 = LocalDate.parse(dateCell.getStringCellValue());
						potentialLowCostFlights1.add(minPrice1);
						potentialLowCostFlightsDates1.add(LocalDate.parse(minPrice1Date));
						minPrice1 = 999999999;
					}
				}
			}
			if (price2Cell != null) {
				if (price2Cell.getNumericCellValue() < minPrice2) {
					if(minPrice2==999999999) {
						daysCounter2 = LocalDate.parse(dateCell.getStringCellValue());
					}
					minPrice2 = (int) price2Cell.getNumericCellValue();
					minPrice2Date = dateCell.getStringCellValue();
				}
				if (ChronoUnit.DAYS.between(daysCounter2,LocalDate.parse(dateCell.getStringCellValue()))
						> ConfigVariables.SCANNING_FREQUENCY_DAYS) {
					if(minPrice2!=999999999) {
						daysCounter2 = LocalDate.parse(dateCell.getStringCellValue());
						potentialLowCostFlights2.add(minPrice2);
						potentialLowCostFlightsDates2.add(LocalDate.parse(minPrice2Date));
						minPrice2 = 999999999;
					}
				}
			}
		}

		List<LocalDate> finalSuitableDates1 = new ArrayList<>();
		List<LocalDate> finalSuitableDates2 = new ArrayList<>();
		List<Integer> finalSuitableFlightPrice1 = new ArrayList<>();
		List<Integer> finalSuitableFlightPrice2 = new ArrayList<>();
		for (int i = 0; i < potentialLowCostFlights1.size(); i++) {
			for (int j = 0; j < potentialLowCostFlights2.size(); j++) {
				if (ChronoUnit.DAYS.between(potentialLowCostFlightsDates1.get(i), potentialLowCostFlightsDates2.get(j)) >= ConfigVariables.DESIRED_DAYS_NUM_FROM
						&& ChronoUnit.DAYS.between(potentialLowCostFlightsDates1.get(i), potentialLowCostFlightsDates2.get(j)) <= ConfigVariables.DESIRED_DAYS_NUM_TO
						&& potentialLowCostFlightsDates1.get(i).isBefore(potentialLowCostFlightsDates2.get(j))) {
					finalSuitableDates1.add(potentialLowCostFlightsDates1.get(i));
					finalSuitableFlightPrice1.add(potentialLowCostFlights1.get(i));
					finalSuitableDates2.add(potentialLowCostFlightsDates2.get(j));
					finalSuitableFlightPrice2.add(potentialLowCostFlights2.get(j));
				}
			}
		}

		List<Integer> totalFlightPrice = new ArrayList<>();
		for (int i = 0; i < finalSuitableFlightPrice1.size(); i++) {
			totalFlightPrice.add(finalSuitableFlightPrice1.get(i) + finalSuitableFlightPrice2.get(i));
		}

		for (int i = 0; i < totalFlightPrice.size() - 1; i++) {
			for (int j = 0; j < totalFlightPrice.size() - i - 1; j++) {
				if (totalFlightPrice.get(j) > totalFlightPrice.get(j + 1)) {

					int tempPrice = totalFlightPrice.get(j);
					totalFlightPrice.set(j, totalFlightPrice.get(j + 1));
					totalFlightPrice.set(j + 1, tempPrice);

					LocalDate tempDate = finalSuitableDates1.get(j);
					finalSuitableDates1.set(j, finalSuitableDates1.get(j + 1));
					finalSuitableDates1.set(j + 1, tempDate);

					tempDate = finalSuitableDates2.get(j);
					finalSuitableDates2.set(j, finalSuitableDates2.get(j + 1));
					finalSuitableDates2.set(j + 1, tempDate);

					int tempFlightPrice = finalSuitableFlightPrice1.get(j);
					finalSuitableFlightPrice1.set(j, finalSuitableFlightPrice1.get(j + 1));
					finalSuitableFlightPrice1.set(j + 1, tempFlightPrice);

					tempFlightPrice = finalSuitableFlightPrice2.get(j);
					finalSuitableFlightPrice2.set(j, finalSuitableFlightPrice2.get(j + 1));
					finalSuitableFlightPrice2.set(j + 1, tempFlightPrice);
				}
			}
		}

		List<String> formattedFinalSuitableDates1 = new ArrayList<>();
		List<String> formattedFinalSuitableDates2 = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (LocalDate date : finalSuitableDates1) {
			String formattedDate = date.format(formatter);
			formattedFinalSuitableDates1.add(formattedDate);
		}
		for (LocalDate date : finalSuitableDates2) {
			String formattedDate = date.format(formatter);
			formattedFinalSuitableDates2.add(formattedDate);
		}

		debug.functionDebug("FINAL:");
		for (int i = 0; i < finalSuitableFlightPrice1.size(); i++) {
			debug.functionDebug("________________________________");
			debug.functionDebug("Flight number: " + (i + 1));
			debug.functionDebug("To  " + ConfigVariables.TO_AIRPORT + " on: " + formattedFinalSuitableDates1.get(i)
					+ " Price: " + finalSuitableFlightPrice1.get(i));
			debug.functionDebug("To " + ConfigVariables.FROM_AIRPORT + " on: " + formattedFinalSuitableDates2.get(i)
					+ " Price: " + finalSuitableFlightPrice2.get(i));
			debug.functionDebug("Total price: " + totalFlightPrice.get(i));
			debug.functionDebug("________________________________");
		}
	/*	} catch (Exception e) {
			debug.errorMessageDebug(e.getMessage());
		}*/
	}
}
