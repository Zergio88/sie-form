package prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckboxExample extends JFrame {
    private JTextField textField;

    public CheckboxExample() {
        super("Selecciona elementos");
        setLayout(new FlowLayout());

        JCheckBox checkbox1 = new JCheckBox("Opción 1");
        JCheckBox checkbox2 = new JCheckBox("Opción 2");
        JCheckBox checkbox3 = new JCheckBox("Opción 3");
        JButton button = new JButton("Aceptar");
        textField = new JTextField(20);

        add(checkbox1);
        add(checkbox2);
        add(checkbox3);
        add(button);
        add(textField);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selected = "";
                if (checkbox1.isSelected()) {
                    selected += checkbox1.getText() + " ";
                }
                if (checkbox2.isSelected()) {
                    selected += checkbox2.getText() + " ";
                }
                if (checkbox3.isSelected()) {
                    selected += checkbox3.getText();
                }

                if (!selected.isEmpty()) {
                    textField.setText(selected);
                } else {
                    JOptionPane.showMessageDialog(CheckboxExample.this,
                            "Selecciona al menos una opción", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setSize(300, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CheckboxExample();
    }
}
