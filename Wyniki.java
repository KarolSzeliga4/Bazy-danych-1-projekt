import java.awt.*;  
import javax.swing.*;    
import java.sql.*;
public class Wyniki{
	public void startWynikiFrame(Connection c, boolean m_k,int typ) throws SQLException{
        //typ 1=indywidualne, 2=drużyny, 3=miksty,4=kluby,5=województw

        if(typ == 1){
            String title;
            String query;
            if(m_k){
                title = "Aktualne wyniki mężczyzn";
                query = "SELECT * FROM zawody.kwal_mezczyzn";
            }
            else{
                title = "Aktualne wyniki kobiet";
                query = "SELECT * FROM zawody.kwal_kobiet";
            }

            JFrame publicF=new JFrame(title);
		publicF.setLocationRelativeTo(null);
    		Statement st = c.createStatement();

            ResultSet rs = st.executeQuery(query);
            
            String data[][]= new String[300][5];    
    		String column[]={"pozycja","Nazwisko_imie","klub","suma","x/10"};                     
            int i =0;
            while (rs.next())  {
                data[i][0] = rs.getString("pozycja");
                data[i][1] = rs.getString("nazwisko_imie");
                data[i][2] = rs.getString("klub");
                data[i][3] = rs.getString("suma");
                data[i][4] = rs.getString("x_10");
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
    		jt.getColumnModel().getColumn(1).setPreferredWidth(300 + biggerSize);
    		jt.getColumnModel().getColumn(2).setPreferredWidth(300 + biggerSize);
    		jt.getColumnModel().getColumn(3).setPreferredWidth(50 + biggerSize);
    		jt.getColumnModel().getColumn(4).setPreferredWidth(50 + biggerSize);
    		publicF.add(new JScrollPane(jt));
            publicF.setSize(1200 + biggerSize*3,800);
            publicF.setVisible(true);
        }
        else if(typ == 2){

            String title;
            String query;
            if(m_k){
                title = "Aktualne wyniki drużyn mężczyzn";
                query = "SELECT * FROM zawody.aktual_wyniki_kwal_druzyn(TRUE)";
            }
            else{
                title = "Aktualne wyniki drużyn kobiet";
                query = "SELECT * FROM zawody.aktual_wyniki_kwal_druzyn(FALSE)";
            }

            JFrame publicF=new JFrame(title); 
		publicF.setLocationRelativeTo(null);
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery(query);
            
            String data[][]= new String[300][4];    
            String column[]={"pozycja","drużyna","suma","x/10"};                     
            int i =0;
            while (rs.next())  {
                data[i][0] = rs.getString("pozycja");
                data[i][1] = rs.getString("drużyna");
                data[i][2] = rs.getString("suma");
                data[i][3] = rs.getString("x_10");
                    i++;
            }
            rs.close();
            st.close(); 

            String tdata[][]= new String[i][4];
            for(int k=0; k<i; k++)
                for(int j=0; j<4; j++)
                    tdata[k][j] = data[k][j];

            JTable jt=new JTable(tdata,column);   
            jt.setBounds(50,250,700,300);
            int biggerSize = 10;
            jt.setFont(new Font("Arial", Font.BOLD, 16 + biggerSize/10));
            jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16 + biggerSize/10));
            jt.setRowHeight(jt.getRowHeight() + 10 + biggerSize/10);
            jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jt.getColumnModel().getColumn(0).setPreferredWidth(50 + biggerSize);
            jt.getColumnModel().getColumn(1).setPreferredWidth(300 + biggerSize);
            jt.getColumnModel().getColumn(2).setPreferredWidth(50 + biggerSize);
            jt.getColumnModel().getColumn(3).setPreferredWidth(50 + biggerSize);
            publicF.add(new JScrollPane(jt));
            publicF.setSize(1200 + biggerSize*3,800);
            publicF.setVisible(true);
        }
        else if(typ == 3){

            String title;
            String query;
            title = "Aktualne wyniki mikstów";
            query = "SELECT * FROM zawody.aktual_wyniki_kwal_mikst()";

            JFrame publicF=new JFrame(title); 
		publicF.setLocationRelativeTo(null);
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery(query);
            
            String data[][]= new String[300][4];    
            String column[]={"pozycja","mikst","suma","x/10"};                     
            int i =0;
            while (rs.next())  {
                data[i][0] = rs.getString("pozycja");
                data[i][1] = rs.getString("mikst");
                data[i][2] = rs.getString("suma");
                data[i][3] = rs.getString("x_10");
                    i++;
            }
            rs.close();
            st.close(); 

            String tdata[][]= new String[i][4];
            for(int k=0; k<i; k++)
                for(int j=0; j<4; j++)
                    tdata[k][j] = data[k][j];

            JTable jt=new JTable(tdata,column);   
            jt.setBounds(50,250,700,300);
            int biggerSize = 10;
            jt.setFont(new Font("Arial", Font.BOLD, 16 + biggerSize/10));
            jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16 + biggerSize/10));
            jt.setRowHeight(jt.getRowHeight() + 10 + biggerSize/10);
            jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jt.getColumnModel().getColumn(0).setPreferredWidth(50 + biggerSize);
            jt.getColumnModel().getColumn(1).setPreferredWidth(300 + biggerSize);
            jt.getColumnModel().getColumn(2).setPreferredWidth(50 + biggerSize);
            jt.getColumnModel().getColumn(3).setPreferredWidth(50 + biggerSize);
            publicF.add(new JScrollPane(jt));
            publicF.setSize(1200 + biggerSize*3,800);
            publicF.setVisible(true);
        }
        else if(typ == 4){

            String title;
            String query;
            title = "Aktualne wyniki klubów";
            query = "SELECT * FROM zawody.aktual_wyniki_kwal_klubow()";

            JFrame publicF=new JFrame(title); 
		publicF.setLocationRelativeTo(null);
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery(query);
            
            String data[][]= new String[300][3];    
            String column[]={"pozycja","klub","suma"};                     
            int i =0;
            while (rs.next())  {
                data[i][0] = rs.getString("pozycja");
                data[i][1] = rs.getString("klub");
                data[i][2] = rs.getString("suma");
                    i++;
            }
            rs.close();
            st.close(); 

            String tdata[][]= new String[i][3];
            for(int k=0; k<i; k++)
                for(int j=0; j<3; j++)
                    tdata[k][j] = data[k][j];

            JTable jt=new JTable(tdata,column);   
            jt.setBounds(50,250,700,300);
            int biggerSize = 10;
            jt.setFont(new Font("Arial", Font.BOLD, 16 + biggerSize/10));
            jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16 + biggerSize/10));
            jt.setRowHeight(jt.getRowHeight() + 10 + biggerSize/10);
            jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jt.getColumnModel().getColumn(0).setPreferredWidth(50 + biggerSize);
            jt.getColumnModel().getColumn(1).setPreferredWidth(300 + biggerSize);
            jt.getColumnModel().getColumn(2).setPreferredWidth(50 + biggerSize);
            publicF.add(new JScrollPane(jt));
            publicF.setSize(1200 + biggerSize*3,800);
            publicF.setVisible(true);
        }
        else{
            String title;
            String query;
            title = "Aktualne wyniki województw";
            query = "SELECT * FROM zawody.aktual_wyniki_kwal_wojewodztw()";

            JFrame publicF=new JFrame(title);
		publicF.setLocationRelativeTo(null);
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery(query);
            
            String data[][]= new String[300][3];    
            String column[]={"pozycja","wojewodztwo","suma"};                     
            int i =0;
            while (rs.next())  {
                data[i][0] = rs.getString("pozycja");
                data[i][1] = rs.getString("wojewodztwo");
                data[i][2] = rs.getString("suma");
                    i++;
            }
            rs.close();
            st.close(); 

            String tdata[][]= new String[i][3];
            for(int k=0; k<i; k++)
                for(int j=0; j<3; j++)
                    tdata[k][j] = data[k][j];

            JTable jt=new JTable(tdata,column);   
            jt.setBounds(50,250,700,300);
            int biggerSize = 10;
            jt.setFont(new Font("Arial", Font.BOLD, 16 + biggerSize/10));
            jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16 + biggerSize/10));
            jt.setRowHeight(jt.getRowHeight() + 10 + biggerSize/10);
            jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            jt.getColumnModel().getColumn(0).setPreferredWidth(50 + biggerSize);
            jt.getColumnModel().getColumn(1).setPreferredWidth(300 + biggerSize);
            jt.getColumnModel().getColumn(2).setPreferredWidth(50 + biggerSize);
            publicF.add(new JScrollPane(jt));
            publicF.setSize(1200 + biggerSize*3,800);
            publicF.setVisible(true);
        }
    }
}
