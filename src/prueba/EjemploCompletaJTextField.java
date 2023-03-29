package prueba;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EjemploCompletaJTextField {
	
    public static void main(String[] args) {
        JFrame frame1 = new JFrame("JFrame1");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(300, 100);
        frame1.setLayout(new FlowLayout());

        final JTextField textField = new JTextField(20);
        frame1.add(textField);

        JButton button = new JButton("Seleccionar opciones");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame("JFrame2");
                frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame2.setSize(300, 100);
                frame2.setLayout(new FlowLayout());

                JCheckBox checkBox1 = new JCheckBox("Sergio");
                JCheckBox checkBox2 = new JCheckBox("Pirin");
                JCheckBox checkBox3 = new JCheckBox("Vladimir");
                JPanel panel = new JPanel();
                panel.add(checkBox1);
                panel.add(checkBox2);
                panel.add(checkBox3);
                frame2.add(panel);

                JButton button2 = new JButton("Aceptar");
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String text = "";
                        if (checkBox1.isSelected()) {
                            text += checkBox1.getText() + ", ";
                        }
                        if (checkBox2.isSelected()) {
                            text += checkBox2.getText() + ",";
                        }
                        if (checkBox3.isSelected()) {
                            text += checkBox3.getText() + ",";
                        }
                        textField.setText(text.trim());
                        //frame1.dispose();
                        frame2.dispose();
                    }
                });
                frame2.add(button2);

                frame2.setVisible(true);
            }
        });
        frame1.add(button);

        frame1.setVisible(true);
    }
}

