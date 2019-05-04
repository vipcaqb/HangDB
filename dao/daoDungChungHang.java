package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class daoDungChungHang {

	public static Connection cn;
	public void ketNoi() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Da xac dinh hqtcsdl");
			cn=DriverManager.getConnection("jdbc:sqlserver://DESKTOP-J4H20NA\\MSSQLSV:1433;databaseName=HoangLoc;user=sa;password=123");
			System.out.println("Da ket noi!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ResultSet getBang(String tenBang) {
		try {
			String sql= "select * from " + tenBang;
			PreparedStatement cmd = cn.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
//	try {
//		String sql ="insert into nhanVien(maNv,tenNv,gioiTinh,ngaySinh,hsl,maDv) values(?,?,?,?,?,? )";
//		//tao cau len
//		PreparedStatement cmd = daoDungChung.cn.prepareStatement(sql);
//		cmd.setString(1, maNv);
//		cmd.setString(2, tenNv);
//		cmd.setBoolean(3, gt);
//		//doi nga util sang sql
//		cmd.setDate(4, new java.sql.Date(ngaysinh.getTime()));
//		cmd.setDouble(5, hsl);
//		cmd.setString(6, madv);
//		return cmd.executeUpdate();
//	} catch (Exception e) {
//		// TODO: handle exception
//		e.printStackTrace();
//		return 0;
//	}
	public int Them(String maHang,String tenHang, Date ngayNhap,Double gia,String tenNCC) {
		try {
			String sql = "insert into Hang(maHang,tenHang,ngayNhap,gia,tenNCC) values(?,?,?,?,?)";
			PreparedStatement cmd = daoDungChungHang.cn.prepareStatement(sql);
			cmd.setString(1, maHang);
			cmd.setString(2, tenHang);
			//doi ngay util sang sql
			cmd.setDate(3, new java.sql.Date(ngayNhap.getTime()));
			cmd.setDouble(4, gia);
			cmd.setString(5, tenNCC);
			return cmd.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	public ResultSet search(String tenBang , String dk) {
		try {
			String sql = "select * from "+ tenBang +" where tenHang like '%" + dk +"%'";
			PreparedStatement cmd = cn.prepareStatement(sql);
			ResultSet rs = cmd.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
