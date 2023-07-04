package JFrame;

import javax.swing.*;
import java.awt.*;

public class RyanairScannerUI extends JFrame {
	private final JTextField countryFromTextField;
	private final JTextField countryToTextField;
	private final JTextField cityFromTextField;
	private final JTextField cityToTextField;
	private final JTextField daysToSpendFromTextField;
	private final JTextField daysToSpendToTextField;
	private final JTextField daysToScanTextField;

	public RyanairScannerUI() {
		super("Ryanair Scanner");

		JLabel titleLabel = new JLabel("Find Your Next Trip");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

		countryFromTextField = new JTextField(20);
		countryToTextField = new JTextField(20);

		cityFromTextField = new JTextField(20);
		cityToTextField = new JTextField(20);

		daysToSpendFromTextField = new JTextField(10);
		daysToSpendToTextField = new JTextField(10);
		daysToScanTextField = new JTextField(10);

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
		add(cityFromTextField, gbc);
		cityFromTextField.addActionListener(e -> {
			String selectedCityFrom = cityFromTextField.getText();
		});

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("To Country:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(countryToTextField, gbc);
		countryToTextField.addActionListener(e -> {
			String selectedCountryTo = countryToTextField.getText();
		});

		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("To Airport:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(cityToTextField, gbc);
		cityToTextField.addActionListener(e -> {
			String selectedCityTo = cityToTextField.getText();
		});

		gbc.gridy = 5;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Number of Days to Spend FROM:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(daysToSpendFromTextField, gbc);
		daysToSpendFromTextField.addActionListener(e -> {
			String daysToSpendFrom = daysToSpendFromTextField.getText();
		});

		gbc.gridy = 6;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Number of Days to Spend TO:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(daysToSpendToTextField, gbc);
		daysToSpendToTextField.addActionListener(e -> {
			String daysToSpendTo = daysToSpendToTextField.getText();
		});

		gbc.gridy = 7;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		add(new JLabel("Number of Days to Scan:"), gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(daysToScanTextField, gbc);
		daysToScanTextField.addActionListener(e -> {
			String daysToScan = daysToScanTextField.getText();
		});

		gbc.gridy = 8;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(searchButton, gbc);
		searchButton.addActionListener(e -> {

		});

		setSize(650, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new RyanairScannerUI();
	}
}