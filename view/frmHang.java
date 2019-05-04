package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.CDATASection;

import dao.daoDungChungHang;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmHang extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmHang frame = new frmHang();
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
	
	daoDungChungHang dc = new daoDungChungHang();
	DefaultTableModel dfTab;
	void napBang() {
		try {
			dc.ketNoi();
			dfTab = new DefaultTableModel();
			dfTab.addColumn("maHang");
			dfTab.addColumn("tenHang");
			dfTab.addColumn("ngayNhap");
			dfTab.addColumn("gia");
			dfTab.addColumn("tenNhaCungCap");
			ResultSet rs = dc.getBang("Hang");
			Object []tam = new Object[5];
			while(rs.next()) {
				tam[0] = rs.getString(1);
				tam[1] = rs.getString(2);
				tam[2] = rs.getString(3);
				tam[3] = rs.getString(4);
				tam[4] = rs.getString(5);

				dfTab.addRow(tam);
			}
			rs.close();
			table.setModel(dfTab);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void napBang(String dk) {
		try {
			dc.ketNoi();
			dfTab = new DefaultTableModel();
			dfTab.addColumn("maHang");
			dfTab.addColumn("tenHang");
			dfTab.addColumn("ngayNhap");
			dfTab.addColumn("gia");
			dfTab.addColumn("tenNhaCungCap");
			ResultSet rs = dc.search("Hang", dk);
			Object []tam = new Object[5];
			while(rs.next()) {
				tam[0] = rs.getString(1);
				tam[1] = rs.getString(2);
				tam[2] = rs.getString(3);
				tam[3] = rs.getString(4);
				tam[4] = rs.getString(5);

				dfTab.addRow(tam);
			}
			rs.close();
			table.setModel(dfTab);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public frmHang() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				napBang();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 138, 753, 289);
		contentPane.add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Hang", null, scrollPane, null);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnTimkiem = new JButton("Search");
		btnTimkiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				String tim = JOptionPane.showInputDialog(null, "Nhap ten", "Search", JOptionPane.INFORMATION_MESSAGE);
				try {
					napBang(tim);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		btnTimkiem.setBounds(68, 73, 122, 54);
		contentPane.add(btnTimkiem);
		
		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader f = new FileReader("hang.txt");
					BufferedReader in = new BufferedReader(f);
					while (true) {
						String st = in.readLine();
						if(st==""||st==null) {
							break;
						}
						//tach du lieu
						String []t = st.split("[;]");
						SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
						java.util.Date ngayNhap = formatter.parse(t[2]);
						int kt =dc.Them(t[0], t[1], ngayNhap, Double.parseDouble(t[3]), t[4]);
						if (kt==0) {
							JOptionPane.showMessageDialog(null, t[0]+ " da co");
						}
						napBang();
					}
					in.close();
					f.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		btnImport.setBounds(266, 73, 122, 54);
		contentPane.add(btnImport);
	}
}
