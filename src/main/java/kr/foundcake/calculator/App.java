package kr.foundcake.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

public class App extends JFrame {

	private Double result = null;

	private String value = "0";

	private String operator = null;

	private final DecimalFormat fieldFormat = new DecimalFormat("#.################");

	private final JTextField resultFiled = new JTextField();

	private final JTextField valueFiled = new JTextField();

	public App() {
		setTitle("계산기");

		resultFiled.setHorizontalAlignment(JTextField.RIGHT);
		resultFiled.setEditable(false);
		resultFiled.setFont(valueFiled.getFont().deriveFont(Font.PLAIN, 30));
		resultFiled.setPreferredSize(new Dimension(300, 30));

		valueFiled.setHorizontalAlignment(JTextField.RIGHT);
		valueFiled.setEditable(false);
		valueFiled.setFont(valueFiled.getFont().deriveFont(Font.BOLD, 40));
		valueFiled.setPreferredSize(new Dimension(300, 50));

		changeFiled();

		String[] buttonLabels = {
				"%", "CE", "C", "←",
				"¹/x", "x²", "²√x", "÷",
				"7", "8", "9", "×",
				"4", "5", "6", "-",
				"1", "2", "3", "+",
				"+/-", "0", "."
		};

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 4, 3, 5));

		for(String label: buttonLabels){
			JButton button = new JButton(label);
			button.setBackground(Color.white);
			button.setPreferredSize(new Dimension(72, 55));
			switch(label) {
				case "←":
					button.addActionListener(this::backSpace);
					break;
				case "CE":
					button.addActionListener(this::clearEntry);
					break;
				case "C":
					button.addActionListener(this::clear);
					break;
				case "%":
					button.addActionListener((ActionEvent e) -> {
						value = fieldFormat.format(Double.parseDouble(value) * 0.01);
						changeFiled();
					});
					break;
				case "¹/x":
					button.addActionListener((ActionEvent e) -> {
						value = fieldFormat.format(1 / Double.parseDouble(value));
						changeFiled();
					});
					break;
				case "²√x":
					button.addActionListener((ActionEvent e) -> {
						value = fieldFormat.format(Math.sqrt(Double.parseDouble(value)));
						changeFiled();
					});
					break;
				case "x²":
					button.addActionListener((ActionEvent e) -> {
						Double tmp = Double.valueOf(value);
						value = fieldFormat.format(tmp * tmp);
						changeFiled();
					});
					break;
				case "+/-":
					button.addActionListener((ActionEvent e) -> {
						if(value.equals("0")) return;
						value = fieldFormat.format(Double.parseDouble(value) * -1);
						changeFiled();
					});
					break;
				case "+", "-", "×", "÷":
					button.addActionListener((ActionEvent e) -> calculate(label));
					break;
				case ".":
					button.addActionListener((ActionEvent e) -> {
						if(value.contains(".")) return;
						value += ".";
						changeFiled();
					});
					break;
				case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
					button.addActionListener((ActionEvent e) -> {
						if(value.equals("0")) {
							value = label;
						} else {
							value += label;
						}
						changeFiled();
					});
					break;
			}
			panel.add(button);
		}
		JButton button = new JButton("=");
		button.setPreferredSize(new Dimension(72, 55));
		button.setBackground(Color.blue);
		button.setForeground(Color.white);
		button.addActionListener((ActionEvent e) -> calculate("="));
		panel.add(button);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(resultFiled);
		add(valueFiled);
		add(panel);
	}

	private void changeFiled() {
		String text = result == null ? "" : fieldFormat.format(result);
		text += operator == null ? "" : operator;
		resultFiled.setText(text);
		valueFiled.setText(value);
	}

	private void clear(ActionEvent e) {
		result = null;
		operator = "";
		value = "0";
		changeFiled();
	}

	private void clearEntry(ActionEvent e) {
		value = "0";
		changeFiled();
	}

	private void backSpace(ActionEvent e) {
		if(value.equals("0")) return;
		if (value.length() > 1) {
			value = value.substring(0, value.length() - 1);
		} else {
			value = "0";
		}
		changeFiled();
	}

	private void calculate(String operator) {
		double value = Double.parseDouble(this.value);
		if(value == 0.0) return;
		if(result != null) {
			switch(this.operator) {
				case "+":
					result += value;
					break;
				case "-":
					result -= value;
					break;
				case "×":
					result *= value;
					break;
				case "÷":
					result /= value;
					break;
			}
		} else{
			result = value;
		}
		if(!operator.equals("=")) {
			this.value = "0";
			this.operator = operator;
		} else {
			this.value = fieldFormat.format(result);
			result = null;
			this.operator = null;
		}
		changeFiled();
	}

	public static void main(String[] args){
		JFrame app = new App();
		app.setSize(350, 500);
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
