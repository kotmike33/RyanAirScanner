package DEBUG;

import Config.ConfigVariables;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Debug{
	File file = new File(ConfigVariables.PROJECT_PATH + "src/main/resources/logs.txt");

	public void errorMessageDebug(String logs) throws IOException {

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		BufferedWriter writer;
		try {
			writer =new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.out.println("Warning: FileWriter does not start.");
			throw new RuntimeException(e);
		}
		writer.write("-------------ERROR MESSAGE-----------------"+ "\r\n" );
		writer.write(formatter.format(date) + ":    " + logs+ "\r\n");
		writer.write("------------/ERROR MESSAGE-----------------"+ "\r\n" );
		writer.flush();
		writer.close();
	}
	public void testDebug(String logs) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		BufferedWriter writer;
		try {
			writer =new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.out.println("Warning: FileWriter does not start.");
			throw new RuntimeException(e);
		}
		writer.write(formatter.format(date) + ": --- " + logs + "-------------"+ "\r\n" );
		writer.flush();
		writer.close();
	}
	public void functionDebug(String logs) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		BufferedWriter writer;
		try {
			writer =new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.out.println("Warning: FileWriter does not start.");
			throw new RuntimeException(e);
		}
		writer.write(formatter.format(date) + ":    " + logs + "\r\n");
		writer.flush();
		writer.close();
	}
	public void warningDebug(String logs) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		BufferedWriter writer;
		try {
			writer =new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.out.println("Warning: FileWriter does not start.");
			throw new RuntimeException(e);
		}
		System.out.println("-------------------WARNING DEBUG------------------------");
		System.out.println(formatter.format(date) + ":    " + logs+ "\r\n");
		System.out.println("------------------/WARNING DEBUG------------------------");
		writer.write("-------------------WARNING DEBUG------------------------"+ "\r\n");
		writer.write(formatter.format(date) + ":    " + logs+ "\r\n");
		writer.write("------------------/WARNING DEBUG------------------------"+ "\r\n");
		writer.flush();
		writer.close();
	}
	public void systemDebug(String logs) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());

		BufferedWriter writer;
		try {
			writer =new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			System.out.println("Warning: FileWriter does not start.");
			throw new RuntimeException(e);
		}
		writer.write(formatter.format(date) + " SELENIUM:   " + logs+ "\r\n");
		writer.flush();
		writer.close();
	}

	public void cleanLogs() throws IOException {
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("");
		fileWriter.flush();
		fileWriter.close();
	}
}
