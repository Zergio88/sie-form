package ppal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controlller.controller;

public class configForm extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String ipServer;
	JPanel panelConfigServer, panelHeader;
	JLabel jblServidor, jblServidorActual;
	JTextField txtServidor,txtServidorActual;
	JButton btnGuardar;
	
	Color bgPanel = Color.decode("#4A4C65");
	Color bgPanelHeader = Color.decode("#14111C");
	Color fntLbl =Color.decode("#FBFBFA");
	Color bgResponse = Color.decode("#332B47");
	Color fntResponse= Color.decode("#FBFBFA");
	Color bgContador = Color.decode("#332B47");
	Color fntContador= Color.decode("#FBFBFA");
	
	public configForm() {
		setSize(400,200);
		setTitle("SIE - Configuracion de IP");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
		placePanelConfigServer();
	}
	
	private void placePanelConfigServer() {
		panelConfigServer = new JPanel();
		panelConfigServer.setLayout(null);
		panelConfigServer.setBounds(0,0,400,200);
		panelConfigServer.setBackground(bgPanel);


		jblServidorActual = new JLabel("ip actual: ");
		jblServidorActual.setFont(new Font("Garamond",Font.ITALIC,25));
		jblServidorActual.setHorizontalAlignment(SwingConstants.CENTER);
		jblServidorActual.setForeground(fntLbl);
		jblServidorActual.setBounds(10,10, 150, 30);

		
		jblServidor = new JLabel("nueva ip: ");
		jblServidor.setFont(new Font("Garamond",Font.BOLD,20));
		jblServidor.setHorizontalAlignment(SwingConstants.CENTER);
		jblServidor.setForeground(fntLbl);
		jblServidor.setBounds(10,50, 150, 30);

		// agregar txtbox para el pallet
		txtServidor= new JTextField();
		txtServidor.setBounds(200,50,150,30);
		
		txtServidorActual=new JTextField();
		txtServidorActual.setBounds(200,10,150,30);
		txtServidorActual.setText(controller.getInstancia().getIp());
		txtServidorActual.setEditable(false);
		
		btnGuardar=new JButton("Guardar");
		btnGuardar.setBounds(40, 90, 140, 30);
		btnGuardar.setBackground(bgContador);
		btnGuardar.setForeground(fntContador);
		btnGuardar.setFont(new Font("Garamond", Font.BOLD,20));
		btnGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ipnueva=txtServidor.getText();
				controller.getInstancia().setIp(ipnueva);
				dispose(); //cierro la ventana actual
			}
		});
		
		panelConfigServer.add(jblServidorActual);
		panelConfigServer.add(txtServidorActual);
		panelConfigServer.add(jblServidor);
		panelConfigServer.add(txtServidor);
		panelConfigServer.add(btnGuardar);
		
		this.add(panelConfigServer);
		
	}
}