package ppal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controlller.controller;

public class consultaForm extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String pallet,nroSerie;
	JPanel panelCantPorPallet, panelByNroSerie, panelCantPorPiso;
	JLabel jblPallet,jblNroSerie,jblEtiquetaResponse,jblResponse;
	
	JTextField txtPallet,txtNroSerie;
	JButton btnConsulta;
	
	Color bgPanel = Color.decode("#4A4C65");
	Color fntLbl =Color.decode("#FBFBFA");
	Color bgResponse = Color.decode("#332B47");
	Color fntResponse= Color.decode("#FBFBFA");
	
	String auxserie="";
	String auxmodelo="";
	String auxestado="";
	String auxpallet="";
	String auxpiso="";
	String auxresponsable="";
	String auxfecha="";
	String aux="";
	String[] splitString={"",""};
	
	public consultaForm(int value) {
		setSize(800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
	
		if(value==1) {
			setTitle("Informes-cantidad por pallet");
			placePanelPalletPorCantidad();
		}
		
		if(value==2) {
			setTitle("Informes-por numero de serie");
			placePanelByNumeroSerie();
		}
		
		if(value==3) {
			setTitle("Informes-cantidad por piso");
			placePanelCantPorPiso();
		}
	
	}
	
	private void placePanelByNumeroSerie() {
		
		panelByNroSerie = new JPanel();
		panelByNroSerie.setLayout(null);
		panelByNroSerie.setBounds(0,0,800,600);
		panelByNroSerie.setBackground(bgPanel);
		
		jblNroSerie = new JLabel("SERIE: ");
		jblNroSerie.setFont(new Font("Garamond",Font.BOLD,20));
		jblNroSerie.setHorizontalAlignment(SwingConstants.CENTER);
		jblNroSerie.setForeground(fntLbl);
		jblNroSerie.setBounds(10, 10, 80, 30);

		// agregar txtbox para el pallet
		txtNroSerie= new JTextField();
		txtNroSerie.setBounds(135,10,200,30);
		
		btnConsulta=new JButton("Consultar");
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

					//modelo
					aux=String.valueOf(datos[1]);
					splitString=aux.split(":");
					auxmodelo=String.valueOf(splitString[1].replaceAll("\"", ""));

					// serie
					aux=String.valueOf(datos[2]);
					splitString=aux.split(":");
					auxserie=String.valueOf(splitString[1]).replaceAll("\"", "");

					// estado
					aux=String.valueOf(datos[3]);
					splitString=aux.split(":");
					auxestado=String.valueOf(splitString[1]).replaceAll("\"", "");

					// pallet
					aux=String.valueOf(datos[4]);
					splitString=aux.split(":");
					auxpallet=String.valueOf(splitString[1]).replaceAll("\"", "");

					// piso
					aux=String.valueOf(datos[5]);
					splitString=aux.split(":");
					auxpiso=String.valueOf(splitString[1]).replaceAll("\"", "");

					// responsables
					aux=String.valueOf(datos[6]);
					splitString=aux.split(":");
					auxresponsable=String.valueOf(splitString[1]).replaceAll("\"", "");

					// fecha
					aux=String.valueOf(datos[7]);
					splitString=aux.split(":");
					auxfecha=String.valueOf(splitString[1]+":"+splitString[2]+":"+splitString[3]+":"+splitString[4]+"\"").replaceAll("\"", "").replaceAll("}","");
					
					String fechaFormateada=formatearFecha(auxfecha);
								
					Object[][] data = {{auxserie, auxmodelo, auxestado,auxpallet,auxpiso,auxresponsable,fechaFormateada}};

					// Crear los encabezados de la tabla
					Object[] headers = {"serie", "modelo", "estado","pallet","piso","responsables","fecha"};

					// Crear el modelo de tabla con los datos y los encabezados
					DefaultTableModel model = new DefaultTableModel(data,headers);

					// Crear la tabla con el modelo de tabla
					JTable table = new JTable(model);
					//table.setBounds(0, 0,100, 70);

					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					// Establece el tamaño de la tabla
					table.setRowHeight(20);
					//table.setPreferredScrollableViewportSize(table.getPreferredSize());
					TableColumn lastColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
					lastColumn.setPreferredWidth(150); 


					JScrollPane scrollPane = new JScrollPane(table);

					scrollPane.setBounds(10, 70, 750, 50);

					panelByNroSerie.add(scrollPane, BorderLayout.CENTER);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		panelByNroSerie.add(jblNroSerie);
		panelByNroSerie.add(txtNroSerie);
		panelByNroSerie.add(btnConsulta);
		//panelByNroSerie.add(jblEtiquetaResponse);
		//panelByNroSerie.add(jblResponse);
		
		this.add(panelByNroSerie);
	}

	private void placePanelPalletPorCantidad() {
		
		panelCantPorPallet = new JPanel();
		panelCantPorPallet.setLayout(null);
		panelCantPorPallet.setBounds(0,0,800,600);
		panelCantPorPallet.setBackground(bgPanel);
		
		jblPallet = new JLabel("Pallet: ");
		jblPallet.setFont(new Font("Garamond",Font.BOLD,20));
		jblPallet.setHorizontalAlignment(SwingConstants.CENTER);
		jblPallet.setForeground(fntLbl);
		jblPallet.setBounds(10, 10, 100, 30);
		
		jblEtiquetaResponse = new JLabel("Cantidad: ");
		jblEtiquetaResponse.setFont(new Font("Garamond",Font.BOLD,20));
		jblEtiquetaResponse.setHorizontalAlignment(SwingConstants.CENTER);
		jblEtiquetaResponse.setForeground(fntLbl);
		jblEtiquetaResponse.setBounds(10, 50, 100, 30);
		
		jblResponse = new JLabel();
		jblResponse.setBounds(135, 50, 200, 30);
		jblResponse.setOpaque(true);
		jblResponse.setBackground(bgResponse);
		jblResponse.setForeground(fntResponse);
		jblResponse.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		jblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		// agregar txtbox para el pallet
		txtPallet = new JTextField();
		txtPallet.setBounds(135,10,200,30);
		
		
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		//btnConsulta.setBackground(bgContador);
		//btnConsulta.setForeground(fntContador);
		btnConsulta.setFont(new Font("Garamond", Font.BOLD,20));
		btnConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				pallet = txtPallet.getText();
				if(pallet.isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo pallet");
					return;
				}
				
				try {
					jblResponse.setText(controller.getInstancia().updateContador(pallet));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		panelCantPorPallet.add(jblPallet);
		panelCantPorPallet.add(txtPallet);
		panelCantPorPallet.add(btnConsulta);
		panelCantPorPallet.add(jblEtiquetaResponse);
		panelCantPorPallet.add(jblResponse);
		
		this.add(panelCantPorPallet);
		
	}
	
	private void placePanelCantPorPiso() {
			
		panelCantPorPiso = new JPanel();
		panelCantPorPiso.setLayout(null);
		panelCantPorPiso.setBounds(0,0,800,600);
		panelCantPorPiso.setBackground(bgPanel);
		
		jblPallet = new JLabel("Pallet: ");
		jblPallet.setFont(new Font("Garamond",Font.BOLD,20));
		jblPallet.setHorizontalAlignment(SwingConstants.CENTER);
		jblPallet.setForeground(fntLbl);
		jblPallet.setBounds(10, 10, 100, 30);
		
		// agregar txtbox para el pallet
		txtPallet = new JTextField();
		txtPallet.setBounds(135,10,200,30);
		
		
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		//btnConsulta.setBackground(bgContador);
		//btnConsulta.setForeground(fntContador);
		btnConsulta.setFont(new Font("Garamond", Font.BOLD,20));
		btnConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String respuesta="";
				
				pallet = txtPallet.getText();
				if(pallet.isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo pallet");
					return;
				}
				
				try {
					respuesta = controller.getInstancia().cantidadPorPiso(pallet);
					System.out.println("cantidades por piso : "+respuesta);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(respuesta.isEmpty()) {	
					JOptionPane.showMessageDialog(null,"No se encontro el pallet");
					return;
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					//@SuppressWarnings("unchecked")
					Object[] array = objectMapper.readValue(respuesta, Object[].class);
				    
					Object[][] data = new Object[array.length][2];
					for(int i=0;i<array.length;i++) {
						Map<String, String> map = (Map<String, String>) array[i];
						String piso = map.keySet().iterator().next();
						String cantidad = map.get(piso);
						data[i][0] = piso;
						data[i][1] = cantidad;
					}
					
					System.out.println("contenido del data");
					for (int i = 0; i < data.length; i++) {
					    for (int j = 0; j < data[i].length; j++) {
					        System.out.print(data[i][j] + " ");
					    }
					    System.out.println("");
					}
					// Crear los encabezados de la tabla
					Object[] headers = {"piso", "cantidad"};

					// Crear el modelo de tabla con los datos y los encabezados
					DefaultTableModel model = new DefaultTableModel(data,headers);

					// Crear la tabla con el modelo de tabla
					JTable table = new JTable(model);
					//table.setBounds(0, 0,100, 70);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					// Establece el tamaño de la tabla
					table.setRowHeight(20);

					JScrollPane scrollPane = new JScrollPane(table);

					scrollPane.setBounds(20, 70, 500, 250);

					panelCantPorPiso.add(scrollPane, BorderLayout.CENTER);
					
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		panelCantPorPiso.add(jblPallet);
		panelCantPorPiso.add(txtPallet);
		panelCantPorPiso.add(btnConsulta);
		
		this.add(panelCantPorPiso);
		
	}
	
	private String formatearFecha(String fechasinFormat) {
		String fechaFormateada=String.valueOf(fechasinFormat);
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			Date date = inputFormat.parse(fechasinFormat);
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			fechaFormateada= outputFormat.format(date);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return fechaFormateada;
	}
	
	// placePanel produccion por fecha
	
	// placePanel para cargar ultimos 10 ingresado de un pallet

}
