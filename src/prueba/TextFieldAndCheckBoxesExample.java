package prueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextFieldAndCheckBoxesExample extends JFrame {

    public TextFieldAndCheckBoxesExample() {
        setTitle("TextField and CheckBoxes Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        // Crear un panel principal y agregar un BorderLayout.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Crear un JTextField y agregarlo en el norte del panel principal.
        JTextField textField = new JTextField();
        mainPanel.add(textField, BorderLayout.CENTER);

        // Crear un botón y agregarlo en el norte del panel principal.
        JButton button = new JButton("Open CheckBoxes Frame");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crear un nuevo JFrame para los checkboxes.
                JFrame checkBoxFrame = new JFrame("CheckBoxes Frame");
                checkBoxFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                checkBoxFrame.setSize(200, 150);
                checkBoxFrame.setLocationRelativeTo(null);

                // Crear un JPanel y agregar un FlowLayout.
                JPanel checkBoxPanel = new JPanel();
                checkBoxPanel.setLayout(new FlowLayout());

                // Crear algunos JCheckBox y agregarlos en el panel de checkboxes.
                JCheckBox checkBox1 = new JCheckBox("CheckBox 1");
                checkBoxPanel.add(checkBox1);

                JCheckBox checkBox2 = new JCheckBox("CheckBox 2");
                checkBoxPanel.add(checkBox2);

                JCheckBox checkBox3 = new JCheckBox("CheckBox 3");
                checkBoxPanel.add(checkBox3);

                // Agregar el panel de checkboxes en el JFrame de checkboxes.
                checkBoxFrame.add(checkBoxPanel);

                // Mostrar el JFrame de checkboxes.
                checkBoxFrame.setVisible(true);
            }
        });
        mainPanel.add(button, BorderLayout.SOUTH);

        // Agregar el panel principal en el JFrame.
        add(mainPanel);

        // Mostrar el JFrame.
        setVisible(true);
    }

    public static void main(String[] args) {
        new TextFieldAndCheckBoxesExample();
    }
}
