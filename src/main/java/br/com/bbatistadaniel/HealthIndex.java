package br.com.bbatistadaniel;

import javax.swing.*;
import java.awt.*;

public class HealthIndex extends JFrame {

    public static void main (String[] args) {
        new HealthIndex();
    }

    private final JSpinner weightSpinner = new JSpinner(new SpinnerNumberModel(70, 1, 999, 0.1));
    private final JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(170, 1, 999, 0.1));
    private final JTextField bmiTextField = new JTextField();
    private final JTextField classificationTextField = new JTextField();

    public HealthIndex () {

        GridBagConstraints gbc = new GridBagConstraints();

        setTitle("Health Index");
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HealthIndex.EXIT_ON_CLOSE);
        gbc.insets = new Insets(3, 3, 3, 3);

        JComponent[] fields = {weightSpinner, heightSpinner, bmiTextField, classificationTextField};
        for (JComponent field : fields) {
            field.setPreferredSize(new Dimension(100, 20));
        }
        bmiTextField.setEditable(false);
        classificationTextField.setEditable(false);
        JButton calculateBmiButton = new JButton("Calculate");
        calculateBmiButton.addActionListener(_ -> {
            double kg = ((Number) weightSpinner.getValue()).doubleValue();
            double cm = ((Number) heightSpinner.getValue()).doubleValue();
            double bmi = (kg / Math.pow(cm / 100, 2));
            if (bmi < 5 || bmi > 70) {
                bmiTextField.setText("Out of bounds");
                classificationTextField.setText("Error");
            } else {
                bmiTextField.setText(String.format("%.3f", bmi));
                classificationTextField.setText(getBmiClassification(bmi));
            }
        });

        JButton clearFieldsButton = new JButton("Clear");
        clearFieldsButton.addActionListener(_ -> {
            bmiTextField.setText("");
            classificationTextField.setText("");
        });

        JLabel weightLabel = new JLabel("Weight (kg):");
        addGridBagComponent(weightLabel, gbc, 0, 0, 1);
        addGridBagComponent(weightSpinner, gbc, 1, 0, 1);
        JLabel heightLabel = new JLabel("Height (cm):");
        addGridBagComponent(heightLabel, gbc, 0, 1, 1);
        addGridBagComponent(heightSpinner, gbc, 1, 1, 1);
        addGridBagComponent(calculateBmiButton, gbc, 0, 2, 1);
        addGridBagComponent(clearFieldsButton, gbc, 1, 2, 1);
        JLabel resultLabel = new JLabel("Result:");
        addGridBagComponent(resultLabel, gbc, 0, 3, 2);
        JLabel bmiLabel = new JLabel("BMI:");
        addGridBagComponent(bmiLabel, gbc, 0, 4, 1);
        addGridBagComponent(bmiTextField, gbc, 1, 4, 1);
        JLabel classLabel = new JLabel("Classification:");
        addGridBagComponent(classLabel, gbc, 0, 5, 1);
        addGridBagComponent(classificationTextField, gbc, 1, 5, 1);

        pack();
        SwingUtilities.invokeLater(() -> {
            int oldWidth = getSize().width;
            int oldHeight = getSize().height;
            setSize(new Dimension(oldWidth + 50, oldHeight + 20));
            setResizable(false);
        });
        setUIManager(this);
        setVisible(true);
    }

    private static void setUIManager (Component c) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(c);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void addGridBagComponent (Component component, GridBagConstraints gbc, int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = 1;
        add(component, gbc);
    }

    private static String getBmiClassification (double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.9 && bmi <= 24.9) {
            return "Normal Weight";
        } else if (bmi >= 25 && bmi <= 29.9) {
            return "Overweight";
        } else {
            return "Obesity";
        }
    }

}
