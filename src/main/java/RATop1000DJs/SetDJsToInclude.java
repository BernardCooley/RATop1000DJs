package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;

public class SetDJsToInclude {

	public static void setDJs() {
		CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		String sqlSelect = "SELECT * FROM RATop1000DJs";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(13) != null) {
					String likes = rs.getString(13);
					if (!likes.equals("N/A")) {
						if (Integer.parseInt(likes) > 900) {
							String sqlUpdate = "UPDATE RATop1000DJs SET Include_in_promotion='Yes' WHERE Name=?";
							pst = con.prepareStatement(sqlUpdate);
							pst.setString(1, rs.getString(1));
							pst.executeUpdate();
						} else {
							String sqlUpdate = "UPDATE RATop1000DJs SET Include_in_promotion='No' WHERE Name=?";
							pst = con.prepareStatement(sqlUpdate);
							pst.setString(1, rs.getString(1));
							pst.executeUpdate();
						}
					}
					if(likes.equals("N/A")) {
						String sqlUpdate = "UPDATE RATop1000DJs SET Include_in_promotion='No' WHERE Name=?";
						pst = con.prepareStatement(sqlUpdate);
						pst.setString(1, rs.getString(1));
						pst.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
