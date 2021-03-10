import java.awt.*;  
import javax.swing.*;    
import java.sql.*;
public class Rozstawienie{
	public void startRozstawienieFrame(Connection c, boolean m_k) throws SQLException{
        String title;
        String query;
        if(m_k){
            title = "Rozstawienie kwalifikacji mężczyzn";
            query = "SELECT mata, stanowisko, nazwisko, imie, klub FROM zawody.rozstawienie_mezczyzn";
        }
        else{
            title = "Rozstawienie kwalifikacji kobiet";
            query = "SELECT mata, stanowisko, nazwisko, imie, klub FROM zawody.rozstawienie_kobiet";
        }

        JFrame publicF=new JFrame(title);
	publicF.setLocationRelativeTo(null);
		Statement st = c.createStatement();

        ResultSet rs = st.executeQuery(query);
        
        String data[][]= new String[300][5];    
		String column[]={"mata","stanowisko","nazwisko","imie","klub"};                     
        int i =0;
        while (rs.next())  {
            data[i][0] = rs.getString("mata");
            data[i][1] = rs.getString("stanowisko");
            data[i][2] = rs.getString("nazwisko");
            data[i][3] = rs.getString("imie");
            data[i][4] = rs.getString("klub");
        		i++;
        }
        rs.close();
        st.close(); 

        String tdata[][]= new String[i][5];
        for(int k=0; k<i; k++)
        	for(int j=0; j<5; j++)
        		tdata[k][j] = data[k][j];

        JTable jt=new JTable(tdata,column);   
		jt.setBounds(50,250,700,300);
		int biggerSize = 10;
		jt.setFont(new Font("Arial", Font.BOLD, 16 + biggerSize/10));
		jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16 + biggerSize/10));
		jt.setRowHeight(jt.getRowHeight() + 10 + biggerSize/10);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jt.getColumnModel().getColumn(0).setPreferredWidth(50 + biggerSize);
		jt.getColumnModel().getColumn(1).setPreferredWidth(120 + biggerSize);
		jt.getColumnModel().getColumn(2).setPreferredWidth(150 + biggerSize);
		jt.getColumnModel().getColumn(3).setPreferredWidth(130 + biggerSize);
		jt.getColumnModel().getColumn(4).setPreferredWidth(300 + biggerSize);
		publicF.add(new JScrollPane(jt));
        publicF.setSize(1200 + biggerSize*3,800);
        publicF.setVisible(true);
	}
}
