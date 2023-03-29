package ppal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controlller.controller;

public class edicionForm extends JFrame {

	String pallet,nroSerie;
	
	JPanel panel,panelHeader;
	JLabel jblPallet,jblNroSerie;
	JLabel lModelo,lSerie,lEstado,lPallet,lPiso,lResponsables,lFecha;
	
	
	JTextField txtPallet,txtNroSerie;
	JTextField tModelo,tSerie,tEstado,tPallet,tPiso,tResponsables,tFecha;
	
	JButton btnConsulta, btnActualizar;
	
	Color bgPanel = Color.decode("#4A4C65");
	Color bgPanelHeader = Color.decode("#14111C");
	Color fntLbl =Color.decode("#FBFBFA");
	Color bgResponse = Color.decode("#332B47");
	Color fntResponse= Color.decode("#FBFBFA");
	
	String auxid="";
	String auxserie="";
	String auxmodelo="";
	String auxestado="";
	String auxpallet="";
	String auxpiso="";
	String auxresponsable="";
	String auxfecha="";
	String aux="";
	String[] splitString={"",""};
	
	public edicionForm() {
		setSize(800,600);
		setTitle("SIE - Edicion de registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
	
		inicializar();
	
	}
	
	private void inicializar() {
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0,0,800,600);
		panel.setBackground(bgPanel);
		
		panelHeader=new JPanel();
		panelHeader.setBackground(bgPanelHeader);
		panelHeader.setBounds(0,0,800,50);
		panelHeader.setLayout(null);
		
		jblNroSerie = new JLabel("SERIE: ");
		jblNroSerie.setFont(new Font("Garamond",Font.BOLD,20));
		jblNroSerie.setHorizontalAlignment(SwingConstants.CENTER);
		jblNroSerie.setForeground(fntLbl);
		jblNroSerie.setBounds(10, 10, 80, 30);

		txtNroSerie= new JTextField();
		txtNroSerie.setBounds(135,10,200,30);
		
		lModelo = new JLabel("Modelo: ");
		lModelo.setBounds(10, 100, 100, 30);
		lModelo.setFont(new Font("Garamond",Font.BOLD,20));
		lModelo.setHorizontalAlignment(SwingConstants.CENTER);
		lModelo.setForeground(fntLbl);		
		tModelo = new JTextField();
		tModelo.setBounds(140,100,200,30);
			
		lSerie = new JLabel("Serie: ");
		lSerie.setBounds(10, 140, 100, 30);
		lSerie.setFont(new Font("Garamond",Font.BOLD,20));
		lSerie.setHorizontalAlignment(SwingConstants.CENTER);
		lSerie.setForeground(fntLbl);
		tSerie = new JTextField();
		tSerie.setBounds(140, 140, 200, 30);
		
		lEstado = new JLabel("Estado: ");
		lEstado.setBounds(10, 180, 100, 30);
		lEstado.setFont(new Font("Garamond",Font.BOLD,20));
		lEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lEstado.setForeground(fntLbl);
		tEstado = new JTextField();
		tEstado.setBounds(140, 180, 200, 30);
		
		lPallet = new JLabel("Pallet: ");
		lPallet.setBounds(10, 220, 100, 30);
		lPallet.setFont(new Font("Garamond",Font.BOLD,20));
		lPallet.setHorizontalAlignment(SwingConstants.CENTER);
		lPallet.setForeground(fntLbl);
		tPallet= new JTextField();
		tPallet.setBounds(140, 220, 200, 30);
		
		lPiso = new JLabel("Piso: ");
		lPiso.setBounds(10, 260, 100, 30);
		lPiso.setFont(new Font("Garamond",Font.BOLD,20));
		lPiso.setHorizontalAlignment(SwingConstants.CENTER);
		lPiso.setForeground(fntLbl);
		tPiso= new JTextField();
		tPiso.setBounds(140, 260, 200, 30);
		
		lResponsables = new JLabel("Responsables: ");
		lResponsables.setBounds(10, 300, 100, 30);
		lResponsables.setFont(new Font("Garamond",Font.BOLD,16));
		lResponsables.setHorizontalAlignment(SwingConstants.CENTER);
		lResponsables.setForeground(fntLbl);
		tResponsables = new JTextField();
		tResponsables.setBounds(140, 300, 200, 30);
		
		btnConsulta=new JButton("Buscar");
		btnConsulta.setBounds(368, 10, 150, 30);
		//btnConsulta.setBackground(bgContador);
		//btnConsulta.setForeground(fntContador);
		btnConsulta.setFont(new Font("Garamond", Font.BOLD,20));
		btnConsulta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nroSerie = txtNroSerie.getText();
				
				if(nroSerie.isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo serie");
					return;
				}
				
				try {
					
					String respuesta = controller.getInstancia().getByNumeroSerie(nroSerie);
					if(respuesta.isEmpty()) {	
						JOptionPane.showMessageDialog(null,"No se encontro nro de Serie");
						return;
					}
					
					String[] datos=respuesta.split(",");
					System.out.println("datos encontrados a updatear:"+datos.toString());
					// id
					aux=String.valueOf(datos[0]);
					splitString=aux.split(":");
					auxid=String.valueOf(splitString[1].replaceAll("\"", ""));
					
					//modelo
					aux=String.valueOf(datos[1]);
					splitString=aux.split(":");
					auxmodelo=String.valueOf(splitString[1].replaceAll("\"", ""));
					tModelo.setText(auxmodelo);
					// serie
					aux=String.valueOf(datos[2]);
					splitString=aux.split(":");
					auxserie=String.valueOf(splitString[1].replaceAll("\"", ""));
					tSerie.setText(auxserie);
					// estado
					aux=String.valueOf(datos[3]);
					splitString=aux.split(":");
					auxestado=String.valueOf(splitString[1].replaceAll("\"", ""));
					tEstado.setText(auxestado);
					// pallet
					aux=String.valueOf(datos[4]);
					splitString=aux.split(":");
					auxpallet=String.valueOf(splitString[1].replaceAll("\"", ""));
					tPallet.setText(auxpallet);
					// piso
					aux=String.valueOf(datos[5]);
					splitString=aux.split(":");
					auxpiso=String.valueOf(splitString[1]);
					tPiso.setText(auxpiso);
					// responsables
					aux=String.valueOf(datos[6]);	
					splitString=aux.split(":");	
					auxresponsable=String.valueOf(splitString[1].replaceAll("\"", ""));
					tResponsables.setText(auxresponsable);
					// fecha
					//aux=String.valueOf(datos[7]);
					//splitString=aux.split(":");
					//auxfecha=String.valueOf(splitString[1]+":"+splitString[2]+":"+splitString[3]+"\"");

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//btnActualizar
		btnActualizar=new JButton("Actualizar");
		btnActualizar.setBounds(300, 400, 200, 30);
		//btnConsulta.setBackground(bgContador);
		//btnConsulta.setForeground(fntContador);
		btnActualizar.setFont(new Font("Verdana", Font.BOLD,20));
		btnActualizar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String respuesta="";
				
				// construye el payload
				
				// verifico que algunos campos claves no actualicen vacio
				if(tSerie.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo Serie");
					return;
				}
				if(tPallet.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo Pallet");
					return;
				}
				if(tPiso.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo Piso");
					return;
				}
						
				String[] datos = {auxid,tModelo.getText(),tSerie.getText().replaceAll("\\s", ""),tEstado.getText(),tPallet.getText(),tPiso.getText(),tResponsables.getText().replaceAll(",", " ")};
				
				try {
					 respuesta = controller.getInstancia().actualizarRegistro(datos);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, respuesta, "Status Edicion",JOptionPane.INFORMATION_MESSAGE);
				System.out.println("respuesta edicion "+ respuesta);
				cleanForm();
				
			}
			
		});
		
		panelHeader.add(jblNroSerie);
		panelHeader.add(txtNroSerie);
		panelHeader.add(btnConsulta);
		
		panel.add(lModelo);
		panel.add(tModelo);
		panel.add(lSerie);
		panel.add(tSerie);
		panel.add(lEstado);
		panel.add(tEstado);
		panel.add(lPallet);
		panel.add(tPallet);
		panel.add(lPiso);
		panel.add(tPiso);
		panel.add(lResponsables);
		panel.add(tResponsables);
		panel.add(btnActualizar);
	
		//panelByNroSerie.add(jblEtiquetaResponse);
		//panelByNroSerie.add(jblResponse);
		this.add(panelHeader);
		this.add(panel);
	}
	
	private void cleanForm() {
		
		auxid="";
		tModelo.setText("");
		tSerie.setText("");
		tEstado.setText("");
		tPallet.setText("");
		tPiso.setText("");
		tResponsables.setText("");
		
		tSerie.requestFocusInWindow();
		tSerie.selectAll();
	}

}
