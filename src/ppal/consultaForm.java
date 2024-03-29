package ppal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import controlller.controller;

public class consultaForm extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	String pallet,nroSerie;
	JPanel panelCantPorPallet, panelByNroSerie, panelCantPorPiso,panelByPallet, panelListaPallet, panelByPatron;
	JLabel jblPallet,jblNroSerie,jblEtiquetaResponse,jblResponse,jblCheck;
	
	JCheckBox ultimosRegistros;
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
		
		if(value==4) {
			setTitle("Informes-dispositivos por pallet");
			placePanelByPallet();
		}
		
		if(value==5) {
			setTitle("Informes-Listado de pallets");
			placePanelListadoDePallets();
		}
		
		if(value==6) {
			setTitle("Informes-Consulta por patron");
			placePanelPorPatron();
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
		txtNroSerie.addKeyListener(getEventoEnter());
		
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		btnConsulta.setBackground(bgResponse);
		btnConsulta.setForeground(fntResponse);
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

					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					// Establece el tamaño de la tabla
					table.setRowHeight(20);
					//table.setPreferredScrollableViewportSize(table.getPreferredSize());
					TableColumn lastColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
					lastColumn.setPreferredWidth(150); 

					JScrollPane scrollPane = new JScrollPane(table);

					scrollPane.setBounds(10, 70, 750, 50);

					/* borro la tabla anterior */
					for(Component component : panelByNroSerie.getComponents()){
						if(component instanceof JScrollPane){
							panelByNroSerie.remove(component);
							break;
						}
					}
				
					panelByNroSerie.add(scrollPane, BorderLayout.CENTER);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		panelByNroSerie.add(jblNroSerie);
		panelByNroSerie.add(txtNroSerie);
		panelByNroSerie.add(btnConsulta);
		
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
		txtPallet.addKeyListener(getEventoEnter());
		
		
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		btnConsulta.setBackground(bgResponse);
		btnConsulta.setForeground(fntResponse);
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
		txtPallet.addKeyListener(getEventoEnter());
		
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		btnConsulta.setBackground(bgResponse);
		btnConsulta.setForeground(fntResponse);
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

					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					// Establece el tamaño de la tabla
					table.setRowHeight(20);

					JScrollPane scrollPane = new JScrollPane(table);

					scrollPane.setBounds(20, 70, 500, 250);

				/* borro la tabla anterior */
				for(Component component : panelCantPorPiso.getComponents()){
					if(component instanceof JScrollPane){
						panelCantPorPiso.remove(component);
						break;
					}
				}

					panelCantPorPiso.add(scrollPane, BorderLayout.CENTER);
					
				} catch (JsonMappingException e1) {
					e1.printStackTrace();
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}			
			}
		});
		
		panelCantPorPiso.add(jblPallet);
		panelCantPorPiso.add(txtPallet);
		panelCantPorPiso.add(btnConsulta);
		
		this.add(panelCantPorPiso);		
	}
	
	private void placePanelByPallet() {
				
		panelByPallet = new JPanel();
		panelByPallet.setLayout(null);
		panelByPallet.setBounds(0,0,800,600);
		panelByPallet.setBackground(bgPanel);
		
		jblPallet = new JLabel("Pallet: ");
		jblPallet.setFont(new Font("Garamond",Font.BOLD,20));
		jblPallet.setHorizontalAlignment(SwingConstants.CENTER);
		jblPallet.setForeground(fntLbl);
		jblPallet.setBounds(10, 10, 100, 30);
				
		/* Etiquetas para mostrar Total del pallet */
		jblEtiquetaResponse = new JLabel("Cantidad: ");
		jblEtiquetaResponse.setFont(new Font("Garamond",Font.BOLD,20));
		jblEtiquetaResponse.setHorizontalAlignment(SwingConstants.CENTER);
		jblEtiquetaResponse.setForeground(fntLbl);
		jblEtiquetaResponse.setBounds(10, 500, 100, 30);
		
		jblResponse = new JLabel();
		jblResponse.setBounds(135, 500, 200, 30);
		jblResponse.setOpaque(true);
		jblResponse.setBackground(bgResponse);
		jblResponse.setForeground(fntResponse);
		jblResponse.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		jblResponse.setHorizontalAlignment(SwingConstants.CENTER);
		
		// checkbox para ultimos registros
		ultimosRegistros = new JCheckBox("CHKUltimos");
		ultimosRegistros.setBounds(135, 45, 20, 20);
		
		jblCheck = new JLabel("Ultimos 20");
		jblCheck.setBounds(160, 45, 100, 20);
		jblCheck.setForeground(fntLbl);
		
		
		// agregar txtbox para el pallet
		txtPallet = new JTextField();
		txtPallet.setBounds(135,10,200,30);
		txtPallet.addKeyListener(getEventoEnter());
				
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		btnConsulta.setBackground(bgResponse);
		btnConsulta.setForeground(fntResponse);
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
				
				if(ultimosRegistros.isSelected()) {
					try {
						respuesta = controller.getInstancia().ultimosRegistros(pallet);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						respuesta = controller.getInstancia().registrosPorPallet(pallet);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			
				
				if(respuesta.isEmpty()) {	
					JOptionPane.showMessageDialog(null,"No se encontro el pallet");
					return;
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				// Crear un modelo de tabla
		        DefaultTableModel tableModel = new DefaultTableModel();

		        // Definir las columnas
		        tableModel.addColumn("ID");
		        tableModel.addColumn("Modelo");
		        tableModel.addColumn("Serie");
		        tableModel.addColumn("Estado");
		        tableModel.addColumn("Pallet");
		        tableModel.addColumn("Piso");
		        tableModel.addColumn("Responsables");
		        tableModel.addColumn("Fecha y Hora");

		        try {
		            // Parsear el JSON
		            ArrayNode jsonArray = objectMapper.readValue(respuesta, ArrayNode.class);

		            // Llenar el modelo de tabla
		            for (JsonNode jsonNode : jsonArray) {
		                if (jsonNode instanceof ObjectNode) {
		                    ObjectNode jsonObject = (ObjectNode) jsonNode;
		                    String fechaFormateada=formatearFecha(jsonObject.get("fechayhora").asText());
		                    Object[] rowData = {
		                            jsonObject.get("id").asInt(),
		                            jsonObject.get("modelo").asText(),
		                            jsonObject.get("serie").asText(),
		                            jsonObject.get("estado").asText(),
		                            jsonObject.get("pallet").asText(),
		                            jsonObject.get("piso").asInt(),
		                            jsonObject.get("responsables").asText(),
		                            fechaFormateada
		                    };
		                    tableModel.addRow(rowData);
		                    //System.out.println("RowData: "+rowData[0].toString() + "-" + rowData[1].toString() + "-" +rowData[2].toString() + "-" +rowData[3].toString() + "-" +rowData[4].toString() + "-" +rowData[5].toString() + "-" +rowData[6].toString()+ "-" +rowData[7].toString());
		                }
		            }
		            
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }

		        // Crear la tabla
		        JTable table = new JTable(tableModel);
				// Establece el tamaño de la tabla
				table.setRowHeight(20);

		        // Crear un JScrollPane para la tabla si es necesario
		        JScrollPane scrollPane = new JScrollPane(table);
		        
		        scrollPane.setBounds(20, 70, 750, 400);
		        	
		        /* borro la tabla anterior */
				for(Component component : panelByPallet.getComponents()){
					if(component instanceof JScrollPane){
						panelByPallet.remove(component);
						break;
					}
				}							
		        
		    	panelByPallet.add(scrollPane, BorderLayout.CENTER);
		    	
		    	try {
					jblResponse.setText(controller.getInstancia().updateContador(pallet));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
				
		panelByPallet.add(jblPallet);
		panelByPallet.add(jblEtiquetaResponse);
		panelByPallet.add(jblResponse);
		panelByPallet.add(txtPallet);
		panelByPallet.add(ultimosRegistros);
		panelByPallet.add(jblCheck);
		panelByPallet.add(btnConsulta);
		
		this.add(panelByPallet);
	}
	
	private void placePanelListadoDePallets() {
		
		panelListaPallet = new JPanel();
		panelListaPallet.setLayout(null);
		panelListaPallet.setBounds(0,0,800,600);
		panelListaPallet.setBackground(bgPanel);
		
		jblPallet = new JLabel("Listado de Pallets");
		jblPallet.setFont(new Font("Garamond",Font.BOLD,25));
		jblPallet.setHorizontalAlignment(SwingConstants.CENTER);
		jblPallet.setForeground(fntLbl);
		jblPallet.setBounds(10, 10, 200, 30);
				
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(250, 10, 150, 30);
		btnConsulta.setBackground(bgResponse);
		btnConsulta.setForeground(fntResponse);
		btnConsulta.setFont(new Font("Garamond", Font.BOLD,20));
		btnConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String respuesta="";
								
				try {
					respuesta = controller.getInstancia().ListaPallet();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				if(respuesta.isEmpty()) {	
					JOptionPane.showMessageDialog(null,"No se encontro el pallet");
					return;
				}
				
				// Crear el modelo de tabla con los datos y los encabezados
				DefaultTableModel model = new DefaultTableModel();
				model.addColumn("INDICE");
				model.addColumn("PALLET");
				
				String cadena = String.valueOf(respuesta.replaceAll("\"","").replaceAll("\\[","").replaceAll("\\]", ""));
				String[] elementos = cadena.split(",");
				
			      for (int i = 0; i < elementos.length; i++) {
			            String numero = Integer.toString(i + 1); // Número de fila
			            String pallet = elementos[i]; // Valor en la columna "Pallet"
			            model.addRow(new String[]{numero, pallet});
			      }

				// Crear la tabla con el modelo de tabla
				JTable table = new JTable(model);

				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				// Establece el tamaño de la tabla
				table.setRowHeight(20);

				JScrollPane scrollPane = new JScrollPane(table);

				scrollPane.setBounds(20, 70, 500, 250);

				panelListaPallet.add(scrollPane, BorderLayout.CENTER);
							
			}
		});
		
		panelListaPallet.add(jblPallet);
		panelListaPallet.add(btnConsulta);
		
		this.add(panelListaPallet);		
		
	}
	
	private void placePanelPorPatron() {
				
		panelByPatron = new JPanel();
		panelByPatron.setLayout(null);
		panelByPatron.setBounds(0,0,800,600);
		panelByPatron.setBackground(bgPanel);
		
		jblPallet = new JLabel("Patron: ");
		jblPallet.setFont(new Font("Garamond",Font.BOLD,20));
		jblPallet.setHorizontalAlignment(SwingConstants.CENTER);
		jblPallet.setForeground(fntLbl);
		jblPallet.setBounds(10, 10, 100, 30);
			
		/* Etiquetas para mostrar Total del pallet */
		jblEtiquetaResponse = new JLabel("Cantidad: ");
		jblEtiquetaResponse.setFont(new Font("Garamond",Font.BOLD,20));
		jblEtiquetaResponse.setHorizontalAlignment(SwingConstants.CENTER);
		jblEtiquetaResponse.setForeground(fntLbl);
		jblEtiquetaResponse.setBounds(10, 500, 100, 30);
		
		jblResponse = new JLabel();
		jblResponse.setBounds(135, 500, 200, 30);
		jblResponse.setOpaque(true);
		jblResponse.setBackground(bgResponse);
		jblResponse.setForeground(fntResponse);
		jblResponse.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		jblResponse.setHorizontalAlignment(SwingConstants.CENTER);
			
		// agregar txtbox para el pallet
		txtPallet = new JTextField();
		txtPallet.setBounds(135,10,200,30);
		txtPallet.addKeyListener(getEventoEnter());
				
		btnConsulta=new JButton("Consultar");
		btnConsulta.setBounds(368, 10, 150, 30);
		btnConsulta.setBackground(bgResponse);
		btnConsulta.setForeground(fntResponse);
		btnConsulta.setFont(new Font("Garamond", Font.BOLD,20));
		
		btnConsulta.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String respuesta="", tamanio="";
				
				pallet = txtPallet.getText();
				if(pallet.isEmpty()) {
					JOptionPane.showMessageDialog(null,"verifique campo pallet");
					return;
				}
					
				try {
					respuesta = controller.getInstancia().getByPatron(pallet);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	
			
				if(respuesta.isEmpty()) {	
					JOptionPane.showMessageDialog(null,"No se encontro el pallet");
					return;
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				// Crear un modelo de tabla
		        DefaultTableModel tableModel = new DefaultTableModel();

		        // Definir las columnas
		        tableModel.addColumn("ID");
		        tableModel.addColumn("Modelo");
		        tableModel.addColumn("Serie");
		        tableModel.addColumn("Estado");
		        tableModel.addColumn("Pallet");
		        tableModel.addColumn("Piso");
		        tableModel.addColumn("Responsables");
		        tableModel.addColumn("Fecha y Hora");

		        try {
		            // Parsear el JSON
		            ArrayNode jsonArray = objectMapper.readValue(respuesta, ArrayNode.class);
		            tamanio=Integer.toString(jsonArray.size());
		            // Llenar el modelo de tabla
		            for (JsonNode jsonNode : jsonArray) {
		                if (jsonNode instanceof ObjectNode) {
		                    ObjectNode jsonObject = (ObjectNode) jsonNode;
		                    String fechaFormateada=formatearFecha(jsonObject.get("fechayhora").asText());
		                    Object[] rowData = {
		                            jsonObject.get("id").asInt(),
		                            jsonObject.get("modelo").asText(),
		                            jsonObject.get("serie").asText(),
		                            jsonObject.get("estado").asText(),
		                            jsonObject.get("pallet").asText(),
		                            jsonObject.get("piso").asInt(),
		                            jsonObject.get("responsables").asText(),
		                            fechaFormateada
		                    };
		                    tableModel.addRow(rowData);
		                    //System.out.println("RowData: "+rowData[0].toString() + "-" + rowData[1].toString() + "-" +rowData[2].toString() + "-" +rowData[3].toString() + "-" +rowData[4].toString() + "-" +rowData[5].toString() + "-" +rowData[6].toString()+ "-" +rowData[7].toString());
		                }
		            }
		            
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }

		        // Crear la tabla
		        JTable table = new JTable(tableModel);
				// Establece el tamaño de la tabla
				table.setRowHeight(20);

		        // Crear un JScrollPane para la tabla si es necesario
		        JScrollPane scrollPane = new JScrollPane(table);
		        
		        scrollPane.setBounds(20, 70, 750, 400);
		        
		        /* borro la tabla anterior */
				for(Component component : panelByPatron.getComponents()){
					if(component instanceof JScrollPane){
						panelByPatron.remove(component);
						break;
					}
				}
		        
		    	panelByPatron.add(scrollPane, BorderLayout.CENTER);
		    	
				jblResponse.setText(tamanio);
	
			}
		});
				
		panelByPatron.add(jblPallet);
		panelByPatron.add(jblEtiquetaResponse);
		panelByPatron.add(jblResponse);
		panelByPatron.add(txtPallet);
		panelByPatron.add(btnConsulta);
		
		this.add(panelByPatron);
		
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
	
	private KeyListener getEventoEnter() {
		KeyAdapter accionEventoEnter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					btnConsulta.doClick();
				}
			}
		};
		return accionEventoEnter;
	}
	
	// placePanel produccion por fecha
	
	// placePanel para cargar ultimos 10 ingresado de un pallet

}
