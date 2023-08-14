package JFrame;

import Config.ConfigVariables;
import MainSelenium.SeleniumStarter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class RyanairScannerUI extends JFrame {
	private final JTextField countryFromTextField;
	private final JTextField countryToTextField;
	private final JTextField airportFromTextField;
	private final JTextField airportToTextField;
	private final JTextField daysToSpendFromTextField;
	private final JTextField daysToSpendToTextField;
	private final JTextField daysToScanTextField;
	private final JTextField scanEveryTextField;

	public RyanairScannerUI() throws IOException {
		super("Ryanair Scanner");

		JLabel titleLabel = new JLabel("Find Your Next Trip");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 32));

		Font boldFont = new Font("Arial", Font.BOLD, 16);

		countryFromTextField = new JTextField(25);
		countryFromTextField.setText(RyanairScannerUI.getConfigValue("fromCountry"));
		countryFromTextField.setFont(boldFont);
		countryFromTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		countryToTextField = new JTextField(25);
		countryToTextField.setText(RyanairScannerUI.getConfigValue("toCountry"));
		countryToTextField.setFont(boldFont);
		countryToTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		airportFromTextField = new JTextField(25);
		airportFromTextField.setText(RyanairScannerUI.getConfigValue("fromAirport"));
		airportFromTextField.setFont(boldFont);
		airportFromTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		airportToTextField = new JTextField(25);
		airportToTextField.setText(RyanairScannerUI.getConfigValue("toAirport"));
		airportToTextField.setFont(boldFont);
		airportToTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		daysToSpendFromTextField = new JTextField(12);
		daysToSpendFromTextField.setText(RyanairScannerUI.getConfigValue("desiredDaysNumFrom"));
		daysToSpendFromTextField.setFont(boldFont);
		daysToSpendFromTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		daysToSpendToTextField = new JTextField(12);
		daysToSpendToTextField.setText(RyanairScannerUI.getConfigValue("desiredDaysNumTo"));
		daysToSpendToTextField.setFont(boldFont);
		daysToSpendToTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		daysToScanTextField = new JTextField(12);
		daysToScanTextField.setText(RyanairScannerUI.getConfigValue("numOfDaysToBeScanned"));
		daysToScanTextField.setFont(boldFont);
		daysToScanTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));

		scanEveryTextField = new JTextField(12);
		scanEveryTextField.setText(RyanairScannerUI.getConfigValue("scanningFrequencyDays"));
		scanEveryTextField.setFont(boldFont);
		scanEveryTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		JButton searchButton = new JButton("Search");

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(10, 10, 10, 10);
		add(titleLabel, gbc);

		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("From Country:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(countryFromTextField, gbc);
		countryFromTextField.addActionListener(e -> {
			String selectedCountryFrom = countryFromTextField.getText();
		});

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("From Airport:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(airportFromTextField, gbc);

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("To Country:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(countryToTextField, gbc);

		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("To Airport:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(airportToTextField, gbc);

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Number of Days to Spend FROM:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(daysToSpendFromTextField, gbc);

		gbc.gridy = 6;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Number of Days to Spend TO:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(daysToSpendToTextField, gbc);

		gbc.gridy = 7;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Number of Days to Scan:"), gbc);

		gbc.gridx =1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(daysToScanTextField, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(scanEveryTextField, gbc);

		gbc.gridy = 9;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(searchButton, gbc);
		searchButton.addActionListener(e -> {
			handleSearchButtonActionListener();
		});
		Color backgroundColor = Color.decode("#F89845");

		titleLabel.setBackground(backgroundColor);
		countryFromTextField.setBackground(backgroundColor);
		countryToTextField.setBackground(backgroundColor);
		airportFromTextField.setBackground(backgroundColor);
		airportToTextField.setBackground(backgroundColor);
		daysToSpendFromTextField.setBackground(backgroundColor);
		daysToSpendToTextField.setBackground(backgroundColor);
		daysToScanTextField.setBackground(backgroundColor);
		scanEveryTextField.setBackground(backgroundColor);
		searchButton.setBackground(Color.BLACK);
		searchButton.setForeground(Color.WHITE);

		getContentPane().setBackground(backgroundColor);
		setSize(650, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void handleSearchButtonActionListener(){
		try {
			if (
					countryFromTextField.getText().isBlank() ||
							airportFromTextField.getText().isBlank() ||
							countryToTextField.getText().isBlank() ||
							airportToTextField.getText().isBlank() ||
							daysToSpendFromTextField.getText().isBlank() ||
							daysToSpendToTextField.getText().isBlank() ||
							daysToScanTextField.getText().isBlank()
			) {
				JFrame frame = new JFrame("Error");
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Empty input fields are not allowed.");
				label.setFont(new Font("Arial", Font.BOLD, 20));
				panel.add(label);
				frame.add(panel);
				frame.pack();
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}else {
				updateConfig("fromCountry", countryFromTextField.getText());
				updateConfig("fromAirport", airportFromTextField.getText());
				updateConfig("toCountry", countryToTextField.getText());
				updateConfig("toAirport", airportToTextField.getText());
				updateConfig("desiredDaysNumFrom", daysToSpendFromTextField.getText());
				updateConfig("desiredDaysNumTo", daysToSpendToTextField.getText());
				updateConfig("numOfDaysToBeScanned", daysToScanTextField.getText());
				this.dispose();
				SeleniumStarter.setUp();
			}
		} catch (IOException | InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void updateConfig(String key, String value) throws IOException {
		Map<String, String> props = new LinkedHashMap<>();
		FileInputStream in = new FileInputStream(ConfigVariables.PROJECT_PATH + "src/main/resources/config.conf");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains("=")) {
				String[] parts = line.split("=", 2);
				String k = parts[0].trim();
				String v = parts[1].trim();
				props.put(k, v);
			}
		}
		reader.close();
		in.close();
		props.put(key, "\"" + value + "\"");

		FileOutputStream out = new FileOutputStream(ConfigVariables.PROJECT_PATH + "src/main/resources/config.conf");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

		for (Map.Entry<String, String> entry : props.entrySet()) {
			writer.write(entry.getKey() + " = " + entry.getValue());
			writer.newLine();
		}
		writer.close();
		out.close();
	}
	public static String getConfigValue(String key) throws IOException {
		FileInputStream in = new FileInputStream(ConfigVariables.PROJECT_PATH + "src/main/resources/config.conf");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains("=")) {
				String[] parts = line.split("=", 2);
				String k = parts[0].trim();
				String v = parts[1].trim();
				if (k.equals(key)) {
					reader.close();
					in.close();
					return v.replaceAll("\"", "");
				}
			}
		}
		reader.close();
		in.close();
		return null;
	}
	public static void main(String[] args) throws IOException {
		new RyanairScannerUI();
	}
}