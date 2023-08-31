package ppal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controlller.controller;

public class sieform {

	private static sieform instance = null;
	
	JFrame ventana;

	JPanel homePanel,panel,panelHeader,panelPisos;
	JMenuBar barraMenu;
	JMenu inventario, edicion, informes, config;
	JMenuItem nuevoItem, retomarItem, editarRegistroItem, cantByPalletItem, bySerieItem, cantPisoByPallet, byPallet, ListaPallets, configServer;
	
	JLabel jblSerie,jblModelo,jblEstado,jblPallet,jblPiso,jblResponsables,jblContTexto,jblContador,jblDelContadorPorPiso,jblContadorPorPiso,jblStatusSrv,jblCantPiso;
		
	JTextField txtSerie,txtModelo,txtPallet,txtPiso,txtResponsables,txtCantPiso;
	
	JTextArea txtAreaEstado;
	//JComboBox listaEstados;
	
	ButtonGroup groupRadioBtn;
	JRadioButton radiobtnFijo;
	JRadioButton radiobtnVariable;
	
	JButton btnCargar,btnFinalPiso,btnCerrarForm;
		
	/* imagen de fondo */
	ImageIcon imagenFondo = new ImageIcon(new ImageIcon(getClass().getResource("/image/conectarigualdad.png")).getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH));
	JLabel labelFondo = new JLabel(imagenFondo);
	JLabel jblBienvenido;
	
	// creo clip para el alerta
	Clip clip = null;
	
	/* propiedades del monitor */	
	Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	
	String titulo = "SIE -Sistema de Inventario - Educar SE";

	Color bgPanel = Color.decode("#4A4C65"); // gris-violeta oscuro
	Color bgPanelHeader = Color.decode("#14111C");
	Color fntLbl =Color.decode("#FBFBFA");
	Color bgTxt = Color.decode("#FBFBFA");
	Color bgContador = Color.decode("#332B47");
	Color fntContador= Color.decode("#FBFBFA");
	int contadorPisoVariable=0;
	
	private sieform() {	
		ventana=new JFrame();
		ventana.setBackground(Color.DARK_GRAY);
		ventana.setTitle(titulo);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Inicializar();
	}
	
	public static sieform getInstance() {
		if(instance==null) {
			instance = new sieform();
		}
		return instance;
	}

	
	public JFrame getFrame() {
		return ventana;
	}
	
	private void Inicializar() {
		
		/* inicia panel home */
		placeHomePanel();
				
		ventana.setResizable(false);
		ventana.setSize(800,600);
		ventana.setLayout(null);
		// Calcular la posición del JFrame
		int posX = (pantalla.width - ventana.getWidth()) / 2;
		int posY = (pantalla.height - ventana.getHeight()) / 2;;
		// Establecer la posición del JFrame
		ventana.setLocation(posX, posY);
		
		ventana.setVisible(true);
	}
	
	/* functions for Iniciar el panel Home */
	
	private void placeHomePanel() {
				
		homePanel = new JPanel();
		homePanel.setLayout(new GridLayout(1, 0));
		homePanel.setLayout(null);
		homePanel.setBackground(bgPanel);
		homePanel.setBounds(0,0,800,600);
		Border borde = BorderFactory.createEtchedBorder(Color.BLACK, Color.BLACK);
		labelFondo.setBorder(borde);
		labelFondo.setBounds(90, 70, 600, 400);
		
		jblBienvenido=new JLabel("Bienvenido a Inventario 2023 - Educar SE");
		jblBienvenido.setFont(new Font("Arial",Font.ITALIC,30));
		jblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		jblBienvenido.setOpaque(true);
		jblBienvenido.setBackground(bgPanelHeader);
		jblBienvenido.setForeground(fntLbl);
		jblBienvenido.setBounds(90, 20, 600, 30);
		homePanel.add(jblBienvenido);
		homePanel.add(labelFondo);
	
		/* menu */
		barraMenu=new JMenuBar();
		inventario = new JMenu("Inventario");
		edicion= new JMenu("Edicion");
		informes= new JMenu("Informes");
		config= new JMenu("Config");

		nuevoItem = new JMenuItem("Nuevo");
		nuevoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Oculta el panel de home y habilita el panel que contiene el form
				homePanel.setVisible(false);
				sieform.this.placeForm();
			}		
		});
		
		retomarItem = new JMenuItem("Retomar Pallet");
		retomarItem.setEnabled(false);
		
		editarRegistroItem = new JMenuItem("Editar un registro");
		editarRegistroItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				edicionForm ventanaEdicion = new edicionForm();
				ventanaEdicion.setVisible(true);
			}
			
		});
		
		cantByPalletItem = new JMenuItem("Cantidad por pallet");
		cantByPalletItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaForm ventanaReportes = new consultaForm(1);
				ventanaReportes.setVisible(true);
			}
			
		});
		
		bySerieItem = new JMenuItem("Consulta por un nro serie");
		bySerieItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaForm ventanaReportes = new consultaForm(2);
				ventanaReportes.setVisible(true);
			}
		});
		
		
		cantPisoByPallet = new JMenuItem("Cantidad por piso de un pallet");
		cantPisoByPallet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaForm ventanaReportes = new consultaForm(3);
				ventanaReportes.setVisible(true);
			}
		});
		
		byPallet = new JMenuItem("registros por pallet");
		byPallet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaForm ventanaReportes = new consultaForm(4);
				ventanaReportes.setVisible(true);
			}
		});
		
		ListaPallets = new JMenuItem("Listado de pallets");
		ListaPallets.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consultaForm ventanaReportes = new consultaForm(5);
				ventanaReportes.setVisible(true);
			}
		});
		
		configServer = new JMenuItem("Configurar IP");
		configServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configForm ventanaConfig=new configForm();
				ventanaConfig.setVisible(true);
			}
		});
		
		inventario.add(nuevoItem);
		inventario.add(retomarItem);
		edicion.add(editarRegistroItem);
		informes.add(cantByPalletItem);
		informes.add(bySerieItem);
		informes.add(cantPisoByPallet);
		informes.add(byPallet);
		informes.add(ListaPallets);
		config.add(configServer);
		
		barraMenu.add(inventario);
		barraMenu.add(edicion);
		barraMenu.add(informes);
		barraMenu.add(config);
		
		barraMenu.setVisible(true);
		ventana.setJMenuBar(barraMenu);

		ventana.add(homePanel);

	}
	
	private void placeForm() {
		
		panel=new JPanel();
		panel.setBackground(bgPanel);
		panel.setBounds(0, 50, 800, 550);
		panel.setLayout(null);
		
		/* agrego al panel el evento de capturar el ENTER cuando tenga el foco */
		addEventEnterKeyToPanel();
		
		panelHeader=new JPanel();
		panelHeader.setBackground(bgPanelHeader);
		panelHeader.setBounds(0,0,800,50);
		panelHeader.setLayout(null);
		
		panelPisos=new JPanel();
		panelPisos.setBackground(bgTxt);
		panelPisos.setBounds(368, 130, 150, 140); //(368, 30, 150, 30);
		panelPisos.setLayout(null);
		panelPisos.setFocusable(false);
        
		/*coloca los Labels*/
		placeJlabel();
		
		/* coloca los TextField */
		placeJTextFields();
		
		/* coloca TextArea */
		placeJTextArea();
		
		/* coloca los botones */
		placeButtons();	
		
		/* coloca radiobuttons */
		placeRadioButton();

		/* coloca comboBox */
		//placeComboBoxes();
		
		ventana.add(panelPisos);
		ventana.add(panelHeader);
		ventana.add(panel);
	}
	
	private void placeJlabel() {
		
		jblSerie=new JLabel("Nro de serie");
		jblSerie.setBounds(30, 30, 200, 30);
		jblSerie.setForeground(fntLbl);
		
		jblModelo=new JLabel("Modelo");
		jblModelo.setBounds(30, 80, 200, 30);
		jblModelo.setForeground(fntLbl);
		
		jblEstado=new JLabel("Estado");
		jblEstado.setBounds(30, 130, 200, 30);
		jblEstado.setForeground(fntLbl);
		
		jblPallet=new JLabel("Pallet");
		jblPallet.setBounds(30, 230, 200, 30);
		jblPallet.setForeground(fntLbl);
		
		jblPiso=new JLabel("Piso");
		jblPiso.setBounds(30,280, 200, 30);
		jblPiso.setForeground(fntLbl);
		
		jblResponsables=new JLabel("Responsables");
		jblResponsables.setBounds(30, 330, 200, 30);
		jblResponsables.setForeground(fntLbl);
		
		jblStatusSrv=new JLabel("");
		jblStatusSrv.setBounds(30, 478, 700, 12);
		jblStatusSrv.setOpaque(true);
		jblStatusSrv.setForeground(fntLbl);
		jblStatusSrv.setFont(new Font("verdana",Font.ITALIC,10));
		jblStatusSrv.setBackground(bgContador);
		
		jblCantPiso=new JLabel("¿cantidad del piso?");
		jblCantPiso.setBounds(10, 0, 150, 30);//(368, 130, 150, 140
		jblCantPiso.setForeground(bgContador);
		
		jblContTexto=new JLabel("Total del pallet");
		jblContTexto.setBounds(0, 0, 300, 50);
		jblContTexto.setOpaque(true); //habilitar cambiar color bgLabel
		jblContTexto.setHorizontalAlignment(SwingConstants.CENTER); // align horizontal
		jblContTexto.setFont(new Font("Garamond", Font.BOLD,30));
		jblContTexto.setBackground(bgPanelHeader);
		jblContTexto.setForeground(fntContador);
		
		jblContador=new JLabel("0");
		jblContador.setBounds(310, 0,200 , 50);
		jblContador.setOpaque(true); //habilitar cambiar color bgLabel
		jblContador.setHorizontalAlignment(SwingConstants.LEFT); // align horizontal
		jblContador.setFont(new Font("Comic Sans MS", Font.BOLD,35));
		jblContador.setBackground(bgPanelHeader);
		jblContador.setForeground(fntContador);
		
		jblDelContadorPorPiso = new JLabel("Cantidad del piso");
		jblDelContadorPorPiso.setBounds(550,80,200, 40);
		jblDelContadorPorPiso.setOpaque(true); //habilitar cambiar color bgLabel
		jblDelContadorPorPiso.setHorizontalAlignment(SwingConstants.CENTER); // align horizontal
		jblDelContadorPorPiso.setFont(new Font("Comic Sans MS", Font.BOLD,20));
		jblDelContadorPorPiso.setBackground(bgContador);
		jblDelContadorPorPiso.setForeground(fntContador);
		
		jblContadorPorPiso=new JLabel("0");
		jblContadorPorPiso.setBounds(550, 120,200,100);
		jblContadorPorPiso.setOpaque(true); //habilitar cambiar color bgLabel
		jblContadorPorPiso.setHorizontalAlignment(SwingConstants.CENTER); // align horizontal
		jblContadorPorPiso.setFont(new Font("Comic Sans MS", Font.BOLD,50));
		jblContadorPorPiso.setBackground(bgContador);
		jblContadorPorPiso.setForeground(fntContador);
	
		panelHeader.add(jblContTexto);
		panelHeader.add(jblContador);
		panel.add(jblSerie);
		panel.add(jblModelo);
		panel.add(jblEstado);
		panel.add(jblPallet);
		panel.add(jblPiso);
		panel.add(jblResponsables);
		panel.add(jblDelContadorPorPiso);
		panel.add(jblContadorPorPiso);
		panel.add(jblStatusSrv);
		
		panelPisos.add(jblCantPiso);
	}
	
	private void placeJTextFields() {
		
		txtSerie=new JTextField();
		txtSerie.setBounds(135,30,200,30);
		txtSerie.addKeyListener(getEventoEnter());
		txtSerie.setBackground(bgTxt);
		
		txtModelo=new JTextField();
		txtModelo.setBounds(135,80,200,30);
		txtModelo.setBackground(bgTxt);
		txtModelo.addKeyListener(getEventoEnter());
		txtPallet=new JTextField();
		txtPallet.setBounds(135,230,200,30);
		txtPallet.setBackground(bgTxt);
		txtPallet.addKeyListener(getEventoEnter());
		
		txtPiso=new JTextField();
		txtPiso.setBounds(135,280,200,30);
		txtPiso.setBackground(bgTxt);
		
		txtResponsables=new JTextField();
		txtResponsables.setBounds(135,330,200,30);
		txtResponsables.setBackground(bgTxt);
		txtResponsables.addKeyListener(getEventoEnter());
		
		txtCantPiso=new JTextField();
		txtCantPiso.setBounds(10,100,130,30);
		txtCantPiso.setVisible(false);
	
		panel.add(txtSerie);
		panel.add(txtModelo);
		panel.add(txtPallet);
		panel.add(txtPiso);
		panel.add(txtResponsables);
		
		panelPisos.add(txtCantPiso);
	}
	
	private void placeButtons() {
		
		btnCargar=new JButton("Guardar");
		btnCargar.setBounds(368, 30, 150, 30);
		btnCargar.setBackground(bgContador);
		btnCargar.setForeground(fntContador);
		btnCargar.setFont(new Font("Garamond", Font.BOLD,20));
		
		btnFinalPiso=new JButton("Siguiente Piso");
		btnFinalPiso.setBounds(10, 100, 130, 30);//0,100,100,30
		
		btnCerrarForm=new JButton("Cerrar formulario");
		btnCerrarForm.setBounds(550, 420, 200, 30); //550, 30,200 , 100
		btnCerrarForm.setBackground(bgContador);
		btnCerrarForm.setForeground(fntContador);
		btnCerrarForm.setFont(new Font("Garamond", Font.BOLD,20));
		
		/*IActionListener e imp ActionPerform*/
		ActionListener accionCargar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String archivoWav = "/soundtrack/LYNC_howler.wav";
				String archivoWav = "/soundtrack/alerta-por-subnormal.wav";

		        try {
		        	clip = AudioSystem.getClip();
		            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(archivoWav)));
		           
		        } catch (Exception e1) {
		            System.err.println(e1.getMessage());
		        }
						
				String respuesta="";
			
				/* numero de Serie */
				String inputSerie = txtSerie.getText().replaceAll("\\s", ""); // elimina espacios en blanco, tab, fin de linea

				if ((inputSerie.isEmpty()) || (inputSerie.length()<8) || (inputSerie.length() > 25)) {
					clip.start();
					JOptionPane.showMessageDialog(null, "verifique campo Serie. Longitud de 8-25 caracteres");
					return;
				}
				
				/* Modelo */
				String modelo = txtModelo.getText();
				if(modelo.isEmpty()) {
					clip.start();
					JOptionPane.showMessageDialog(null,"verifique campo modelo");
					return;
				}
	
				/* valido Estado de acuerdo a campo en base de datos*/
				String EstadoValidoLong = txtAreaEstado.getText();
				if(EstadoValidoLong.isEmpty()) {
					clip.start();
					JOptionPane.showMessageDialog(null, "verifique campo Estado");
					return;
				}
				
				if (EstadoValidoLong.length() > 150) {
				    EstadoValidoLong = EstadoValidoLong.substring(0, 150);
				}
				
				String pallet = txtPallet.getText();
				if(pallet.isEmpty()) {
					clip.start();
					JOptionPane.showMessageDialog(null, "verifique campo pallet");
					return;
				}
				
				String piso = txtPiso.getText();
				
				if(!radiobtnVariable.isSelected() && !radiobtnFijo.isSelected()) {
					clip.start();
					JOptionPane.showMessageDialog(null, "verifique cantidad por piso");
					return;
				}
				
				if(radiobtnVariable.isSelected()) {
					
					if(piso.isEmpty()) {
						clip.start();
						JOptionPane.showMessageDialog(null, "verifique campo piso");
						return;
					}
				}
				
				if(radiobtnFijo.isSelected()) {	
					/* calculo el piso*/
					try {
						piso = calculaPiso(pallet, txtCantPiso.getText());
						txtPiso.setText(piso);
						txtPiso.setEditable(false);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				
				String responsables = txtResponsables.getText().replaceAll(",", " ");
				
				// construye el payload		
				String[] datos = {modelo,inputSerie,EstadoValidoLong,pallet,piso,responsables};			
				
				try {
					respuesta = controller.getInstancia().altaBien(datos);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
								
				if(respuesta.contains("P-500")) {
					// Mostrar la alerta con el icono de warning
				
					// Reproducir el sonido
		        //if (clip != null) {
		            clip.start();
		        //}
			        JOptionPane.showMessageDialog(null, "El numero de serie ya fue ingresado", "Alerta", JOptionPane.WARNING_MESSAGE);
			  
				} else {
					try {
						updateContador(txtPallet.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
							
				cleanForm();
				
				jblStatusSrv.setText(respuesta);
				
				if(radiobtnFijo.isSelected()) {
					txtCantPiso.setEditable(false);
				}
			}
		};
		
		// Listener Cerrar Formulario
		ActionListener accionCerrarForm = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Borro los paneles de la ventana
				ventana.remove(panel);
				ventana.remove(panelHeader);
				ventana.remove(panelPisos);
				panel=null;
				panelHeader=null;
				panelPisos=null;
				
				homePanel.setVisible(true);
			}			
		};
		
		// Listener Terminar piso variable
		ActionListener TerminarPisoVariable = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String pisoActual=txtPiso.getText();
				if(pisoActual.isEmpty()) {
					JOptionPane.showMessageDialog(null, "verifique campo piso");
					return;
				}
				contadorPisoVariable=0;
				int pisoActualInteger = Integer.parseInt(pisoActual);
				pisoActualInteger++;
				pisoActual=Integer.toString(pisoActualInteger);
				txtPiso.setText(pisoActual);
				jblContadorPorPiso.setText("0");
			}		
		};
		
		btnCargar.addActionListener(accionCargar);
		btnCargar.setFocusable(false);
		panel.add(btnCargar);
		
		btnCerrarForm.addActionListener(accionCerrarForm);
		btnCerrarForm.setFocusable(false);
		panel.add(btnCerrarForm);
		
		btnFinalPiso.addActionListener(TerminarPisoVariable);
		btnFinalPiso.setVisible(false);
		panelPisos.add(btnFinalPiso);
		
	}
	
	private void placeJTextArea() {
		txtAreaEstado=new JTextArea();	
		txtAreaEstado.setBounds(135,130,200,90);
		txtAreaEstado.setBackground(bgTxt);
		txtAreaEstado.addKeyListener(getEventoEnter());
		panel.add(txtAreaEstado);
	}
	
	private void placeComboBoxes() {
		String [] estados = {"OK","Bloqueada","fallas varias","Alla"};
 		JComboBox listaEstados = new JComboBox(estados);
 		
 		listaEstados.setBounds(135, 320, 100, 30);
 		panel.add(listaEstados);
	}
	
	private void placeRadioButton() {
		radiobtnFijo = new JRadioButton("fija",false);
		radiobtnVariable = new JRadioButton("variable",false);
		
		radiobtnFijo.setBounds(10, 30, 100, 30);
		radiobtnFijo.addActionListener(evntOcultarComponentes(radiobtnVariable));
		
		radiobtnVariable.setBounds(10, 60, 100, 30);
		radiobtnVariable.addActionListener(evntOcultarComponentes(radiobtnFijo));
			
		groupRadioBtn = new ButtonGroup();
		groupRadioBtn.add(radiobtnFijo);
		groupRadioBtn.add(radiobtnVariable);
		
		panelPisos.add(radiobtnFijo);
		panelPisos.add(radiobtnVariable);	
	}
	
	
	/* declaration of aux functions */
	
	private void showAdv() {
		
		// Guarda una referencia al componente que tiene el foco actualmente
        final Component focusedComponent = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        
		JFrame advFrame = new JFrame("Advertencia");
		advFrame.setSize(400,300);
		JLabel advLabel = new JLabel("Verfique todos los campos");
		JButton okButton = new JButton("ACEPTAR");
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				advFrame.dispose();
			}
		});

		JPanel advPanel = new JPanel();
	
		advPanel.add(advLabel);
		advPanel.add(okButton);
		advPanel.setFocusable(true);
		advFrame.add(advPanel);
		advFrame.pack();		
	    advPanel.addKeyListener(new KeyAdapter() {
	    	@Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    advFrame.dispose();
                }
            }
        });
	
		// Calcular la posición del JFrame
		int posX = (pantalla.width - advFrame.getWidth()) / 2;
		int posY = (pantalla.height - advFrame.getHeight()) / 2;

		// Establecer la posición del JFrame
		advFrame.setLocation(posX, posY);

		// Mostrar el JFrame
		advFrame.setVisible(true);
        // Devuelve el foco al componente original
        if (focusedComponent != null) {
            focusedComponent.requestFocusInWindow();
        }
	}
		
	private KeyListener getEventoEnter() {
		KeyAdapter accionEventoEnter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					btnCargar.doClick();
				}
			}
		};
		return accionEventoEnter;
	}
	
	private ActionListener evntOcultarComponentes(JRadioButton rb) {
		ActionListener eventoOcultar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==radiobtnFijo) {
					btnFinalPiso.setVisible(false);
					txtCantPiso.setVisible(true);
					rb.setEnabled(false);;
					
				}else if (e.getSource()==radiobtnVariable) {
					txtCantPiso.setVisible(false);
					btnFinalPiso.setVisible(true);
					rb.setEnabled(false);
				}
			}			
		};
		return eventoOcultar;
	}
 
	private void addEventEnterKeyToPanel() {
		
		KeyListener eventoTeclaEnter = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					btnCargar.doClick();
				}
			}
		};
		panel.setFocusable(true);
		panel.addKeyListener(eventoTeclaEnter);	
	}
	
	private void updateContador(String pallet) throws IOException {
		// cantidad total
		String cantidadPallet=controller.getInstancia().updateContador(pallet);
		
		// cantidad por piso en caso de piso con cantidad fija
		if(radiobtnFijo.isSelected()) {
			String cantidadPorPiso=txtCantPiso.getText();
			int cantidadPalletInteger=Integer.parseInt(cantidadPallet);
			int cantidadPorPisoInteger=Integer.parseInt(cantidadPorPiso);
			// calculo la cantidad que deberia haber en el piso
			int cantidadActualPiso=cantidadPalletInteger%cantidadPorPisoInteger;
			String cantActualPiso=Integer.toString(cantidadActualPiso);
			
			jblContadorPorPiso.setText(cantActualPiso);
		}
		
		// cantidad en caso de piso variable
		if(radiobtnVariable.isSelected()) {
			contadorPisoVariable++;
			jblContadorPorPiso.setText(Integer.toString(contadorPisoVariable));
		}		
		jblContador.setText(cantidadPallet);		
	}
	
	private String calculaPiso(String pallet, String cantPiso) throws IOException {
		String resultado;
		String cantidadPallet = controller.getInstancia().updateContador(pallet);
		
		int cantidadPalletInteger = Integer.parseInt(cantidadPallet);	
		int cantByPiso = Integer.parseInt(cantPiso);
		
		// si el equipo es el primero de un pallet nuevo, hay que setear cantidad en 1
		if(cantidadPalletInteger == 0)
			return resultado="1";
		
		System.out.println("division: "+cantidadPalletInteger+" / "+cantByPiso);
		float cociente = (float) cantidadPalletInteger/cantByPiso;
		// ceil redondea al proximo valor entero. Si la division da exacta, devuelve resultado sin redondear
		int resultadoRedondeado = (int) Math.ceil(cociente);

		if(cantidadPalletInteger%cantByPiso==0)
			resultadoRedondeado=resultadoRedondeado+1;
		
		resultado = Integer.toString(resultadoRedondeado);
		System.out.println("resultado division: "+resultado);
		// dividir por la cantidad del piso fijo
		return resultado;
	} 
	
	private void cleanForm() {
		txtSerie.setText("");
		txtSerie.requestFocusInWindow();
		txtSerie.selectAll();
	}
	
	/* main function */
	 public static void main(String[] args) {
		/*** start the program ***/	
		 sieform.getInstance();	
	}
}
