package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.HuespedController;
import controller.ReservaController;
import modelo.Huesped;
import modelo.Reserva;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.crypto.spec.OAEPParameterSpec;
import javax.management.RuntimeErrorException;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	private ReservaController reservaController;
	private HuespedController huespedController;
	private ReservasView reservasView;
	String reserva;
	String huesped;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		
		this.reservasView = new ReservasView();
		this.reservaController = new ReservaController();
		this.huespedController = new HuespedController();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		listarReservasEnTabla();
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		listarHuespedesEnTabla();
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( !txtBuscar.getText().equals("")) {
					String id = txtBuscar.getText();
					//Para evitar que busque en todos los registros
					if(panel.getSelectedIndex() == 0) {
						((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
						listarReservaPorIdEnTabla(id);
					}
					if(panel.getSelectedIndex() == 1) {
						((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
						listarHuespedPorIdEnTabla(id);
					}
					txtBuscar.setText("");
				}else {
					limpiarTablas();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaSeleccionada = tbReservas.getSelectedRow();
				if(filaSeleccionada >= 0) {
					ActulizarReserva();
					limpiarTablas();
					listarTablas();
				}
				int filaHuespedSelccionado = tbHuespedes.getSelectedRow();
				if(filaHuespedSelccionado >= 0) {
					actualizarHuesped();
					limpiarTablas();
					listarTablas();
				}
			}
		});
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservaSeleccionada = tbReservas.getSelectedRow();
				int filaHuespedSelccionada = tbHuespedes.getSelectedRow();
				if(filaReservaSeleccionada >= 0) {
					reserva = tbReservas.getValueAt(filaReservaSeleccionada, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la reserva seleccionada?");
					if( confirmar == JOptionPane.YES_OPTION) {
						String id = tbReservas.getValueAt(filaReservaSeleccionada, 0).toString();
						reservaController.eliminar(Integer.parseInt(id));	
						JOptionPane.showMessageDialog(contentPane, "Se eliminó la reserva correctamente");
						limpiarTablas();
						listarTablas();						
					}
				}
				if(filaHuespedSelccionada >= 0) {
					huesped = tbHuespedes.getValueAt(filaHuespedSelccionada, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea eliminar al huesped seleccionado?");
					if( confirmar == JOptionPane.YES_OPTION) {
						String id = tbHuespedes.getValueAt(filaHuespedSelccionada, 0).toString();
						huespedController.eliminar(Integer.parseInt(id));	
						JOptionPane.showMessageDialog(contentPane, "Se eliminó al huesped correctamente");
						limpiarTablas();
						listarTablas();
					}
				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	
	private List<Huesped> mostrarHuespedes() {
		return this.huespedController.listar();
	}
	
	private List<Reserva> mostrarReservas() {
		return this.reservaController.listar();
	}

	private Huesped buscarHuespedPorId(String id) {
		return this.huespedController.buscarPorId(id);
	}
	
	private Reserva buscarReservaPorId(String id) {
		return this.reservaController.buscarPorId(id);
	}
	
	private void listarHuespedesEnTabla() {
		List<Huesped> huespedes = this.mostrarHuespedes();
		modeloHuesped.setRowCount(0);
		try {
			for(Huesped huesped : huespedes) {
				modeloHuesped.addRow(new Object[] {
						huesped.getId(),
						huesped.getNombre(),
						huesped.getApellido(),
						huesped.getFechaNacimineto(),
						huesped.getNacionalidad(),
						huesped.getTelefono(),
						huesped.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void listarReservasEnTabla() {
		List<Reserva> reservas = this.mostrarReservas();
		modelo.setRowCount(0);
		try {
			for(Reserva reserva : reservas) {
				modelo.addRow(new Object[] {
						reserva.getId(),
						reserva.getFechaIngreso(),
						reserva.getFechaEgreso(),
						reserva.getValor(),
						reserva.getFormaPago()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void listarHuespedPorIdEnTabla(String id) {
		Huesped huesped = this.buscarHuespedPorId(id);
		if(huesped != null) {
			try {
				modeloHuesped.addRow(new Object[] {
						huesped.getId(),
						huesped.getNombre(),
						huesped.getApellido(),
						huesped.getFechaNacimineto(),
						huesped.getNacionalidad(),
						huesped.getTelefono(),
						huesped.getIdReserva()
				});				
			} catch (Exception e) {
				throw e;
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se encontró la reserva con el id: "+id,"Registro no encontrado",JOptionPane.ERROR_MESSAGE);
			listarHuespedesEnTabla();
		}
	}
	
	private void listarReservaPorIdEnTabla(String id) {
		Reserva reserva = this.buscarReservaPorId(id);
		if(reserva != null) {
			modelo.setRowCount(0);
			try {
				modelo.addRow(new Object[] {
						reserva.getId(),
						reserva.getFechaIngreso(),
						reserva.getFechaEgreso(),
						reserva.getValor(),
						reserva.getFormaPago()
				});
				
			} catch (Exception e) {
				throw e;
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se encontró la reserva con el id: "+id,"Registro no encontrado",JOptionPane.ERROR_MESSAGE);
			listarReservasEnTabla();
		}
	}
	
	private void ActulizarReserva() {
		Optional
		.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
		.ifPresent(row ->{
			LocalDate fechaIngreso;
			LocalDate fechaSalida;
			
			try {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaIngreso = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(),1).toString(), dateFormatter);
				fechaSalida = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(),2).toString(), dateFormatter);
			}catch (DateTimeException e) {
				throw new RuntimeException(e);
			}
			this.reservasView.limpiarValor();
			String valor = calcularValorReserva(fechaIngreso, fechaSalida);
			String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
			Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
			if( tbReservas.getSelectedColumn() == 0 ) {
				JOptionPane.showMessageDialog(this, "No hay ninguna reserva seleccionada");
			}else {
				this.reservaController.actualizar(id, fechaIngreso, fechaSalida, valor, formaPago);
				JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
			}
			
		});	
	}
	
	private void actualizarHuesped() {
		Optional
		.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
		.ifPresent(row ->{
			LocalDate fechaNacimiento;			
			try {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),3).toString(), dateFormatter);
			}catch (DateTimeException e) {
				throw new RuntimeException(e);
			}
			Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
			String nombre = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),1).toString();
			String apellido = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),2).toString();
			String nacionalidad = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),4).toString();
			String telefono = modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(),5).toString();
			Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
			if( tbReservas.getSelectedColumn() == 0 ) {
				JOptionPane.showMessageDialog(this, "No hay ningun huesped seleccionado");
			}else {
				this.huespedController.actualizar(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
			}
			
		});	
	}
	
	private String calcularValorReserva(LocalDate fechaIngreso, LocalDate fechaSalida) {
		if(fechaIngreso != null && fechaSalida != null) {
			if(fechaIngreso.isAfter(fechaSalida) ) {
				JOptionPane.showMessageDialog(this, "La afecha de entrada de ser posterior a la fecha de salida","Error en fechas",JOptionPane.ERROR_MESSAGE);
				return "";
			}
			int dias = (int) ChronoUnit.DAYS.between(fechaIngreso, fechaSalida);
			int noche = 500;
			int valor = dias*noche;
			return "$ "+Integer.toString(valor);
		}else {
			return "";
		}
	}

	private void limpiarTablas() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	
	private void listarTablas() {
		listarHuespedesEnTabla();
		listarReservasEnTabla();
	}

	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
