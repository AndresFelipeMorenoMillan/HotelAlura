package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controler.ControlerHuesped;
import controler.ControlerReserva;
import model.Huespedes;
import model.Reservas;

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

	private ControlerReserva reservasControl;
	private ControlerHuesped huespedControl;
	private ReservasView reservaVista;
	String reserva;
	String huespedes;
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
		
		this.reservaVista = new ReservasView();
		this.reservasControl = new ControlerReserva();
		this.huespedControl = new ControlerHuesped();
		
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
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mostrarTablaReservas();
		
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
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		mostrarTablaHuespedes();
		
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
				limpiarTabla();
				if(txtBuscar.getText().equals("")) {
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				}else {
					mostrarTablaReservasId();
					mostrarTablaHuespedesId();
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
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();
				if(filaReservas >=0) {
					actualizarReservas();
					limpiarTabla();
					mostrarTablaReservas();
					mostrarTablaHuespedes();
				}else if(filaHuespedes>=0){
					actualizarHuespedes();
					limpiarTabla();
					mostrarTablaHuespedes();
					mostrarTablaReservas();
				}
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaReserva = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();
				if(filaReserva>=0) {
					reserva = tbReservas.getValueAt(filaReserva, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la reserva?");
					if(confirmar == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(filaReserva, 0).toString();
						reservasControl.eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro eliminado con éxito");
						limpiarTabla();
						mostrarTablaReservas();
						mostrarHuesped();
					}
				}else if(filaHuespedes>=0){
					huespedes = tbHuespedes.getValueAt(filaReserva, 0).toString();
					int confirmah = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el huesped?");
					if(confirmah == JOptionPane.YES_OPTION) {
						String valor = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
						huespedControl.elimnarh(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Huesped eliminado");
						limpiarTabla();
						mostrarHuesped();
						mostrarTablaReservas();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Hubo error al eliminar");
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
	
	//Reservas CRUD
	private List<Reservas> mostrarReservas(){
		return this.reservasControl.mostrar();
	}
	
	private List<Reservas> buscarIdReservas(){
		return this.reservasControl.buscar(txtBuscar.getText());
	}
	
	private void mostrarTablaReservas() {
		List<Reservas> reservas = mostrarReservas();
		modelo.setRowCount(0);
		try {
			for(Reservas reserva: reservas) {
				modelo.addRow(new Object[] {
					reserva.getId(), reserva.getFechaE(), reserva.getFechaS(), reserva.getValor(), reserva.getFormaPago()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void mostrarTablaReservasId() {
		List<Reservas> reservas = buscarIdReservas();
		modelo.setRowCount(0);
		try {
			for(Reservas reserva: reservas) {
				modelo.addRow(new Object[] {
					reserva.getId(), reserva.getFechaE(), reserva.getFechaS(), reserva.getValor(), reserva.getFormaPago()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void actualizarReservas() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn())).ifPresent(fila ->{
			LocalDate fechaE;
			LocalDate fechaS;
			
			try {
				DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				fechaE = LocalDate.parse(modelo.getValueAt(	tbReservas.getSelectedRow() , 1).toString(),dateFormat);
				fechaS = LocalDate.parse(modelo.getValueAt(	tbReservas.getSelectedRow() , 2).toString(),dateFormat);
				System.out.println("f: entrada "+fechaE+" f: salida "+fechaS);
			} catch (DateTimeException e) {
				throw new RuntimeException();
			}
			this.reservaVista.limpiarValor();
			String valor = calcularValorReserva(fechaE, fechaS)+" COP";
			String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
			Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
			
			if(tbReservas.getSelectedColumn()==0) {
				JOptionPane.showMessageDialog(this, "No se pueden moficar los Id");
			}else{
				this.reservasControl.actulizarReserva(fechaE, fechaS, valor, formaPago, id);
				JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
				System.out.println("nuevos registros: "+fechaE+" "+fechaS+" "+valor);
			}
		} );;
	}
	
	public String calcularValorReserva(LocalDate fechaE, LocalDate fechaS) {
		if(fechaE != null && fechaS != null) {
			int rango = (int) ChronoUnit.DAYS.between(fechaE, fechaS);
			int noche = 45000;
			int dias = rango+1;
			int valor = dias*noche;
			return Integer.toString(valor);
		}else {
			return "";
		}
	}
	
	//Huespedes CRUD
	private List<Huespedes> mostrarHuesped(){
		return this.huespedControl.mostrarHuespedes();
	}
	
	private List<Huespedes> busarHuespedId(){
		return this.huespedControl.buscarHuespedes(txtBuscar.getText());
	}
	
	private void mostrarTablaHuespedes() {
		List<Huespedes> huesped = mostrarHuesped();
		modeloHuesped.setRowCount(0);
		try {
			for(Huespedes huespedes: huesped) {
				modeloHuesped.addRow(new Object[] {
					huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(), huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getTelefono(), huespedes.getIdReserva()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void mostrarTablaHuespedesId() {
		List<Huespedes> huesped = busarHuespedId();
		modeloHuesped.setRowCount(0);
		try {
			for(Huespedes huespedes: huesped) {
				modeloHuesped.addRow(new Object[] {
					huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(), huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getTelefono()
				});
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void actualizarHuespedes() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn())).ifPresentOrElse(filaHuespedes -> {
			String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
			String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
			LocalDate fechaNacimiento = LocalDate.parse(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
			String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
			String telofono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
			Integer idRserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
			Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
			
			if(tbHuespedes.getSelectedColumn() == 6) {
				JOptionPane.showMessageDialog(this, "No se pueden moficar los id");			
				}else {
					this.huespedControl.actualizarh(nombre, apellido, fechaNacimiento, nacionalidad, telofono, idRserva, id);
					JOptionPane.showMessageDialog(this, String.format("Registro modicado con éxito"));
				}
			
		}, () -> JOptionPane.showInternalInputDialog(this,"Favor verifica los datos"));
		
	}
	
	private void limpiarTabla() {
		((DefaultTableModel)tbHuespedes.getModel()).setRowCount(0);;
		((DefaultTableModel)tbReservas.getModel()).setRowCount(0);;
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
