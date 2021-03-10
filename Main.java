import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
public class Main{  
	public static void main(String[] args) { 
		//Connect to database
		Connection c = null;
		  try {
		        String dbaseURL = "jdbc:postgresql://localhost/u8szeliga?currentSchema=zawody";
		        String username  = "u8szeliga";
		        uSzeligaPass password = new uSzeligaPass(); 
		        c = DriverManager.getConnection (dbaseURL, username, password.pass);
		  } catch (SQLException se) {
		        System.out.println("Couldn't connect: print out a stack trace and exit.");
		        se.printStackTrace();
		        System.exit(1);
		  }
		if (c != null) {
		    System.out.println("Connected to the database!");
		    try {
		 		DatabaseMetaData md; // metadane
		            // nalezy polaczyc sie z baza danych
		        md = c.getMetaData();
                md.supportsAlterTableWithAddColumn();
                md.supportsAlterTableWithDropColumn();
                md.supportsBatchUpdates();
                md.supportsPositionedDelete();
                md.supportsPositionedUpdate();
                md.supportsTransactions();
                md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
                md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);

                //start app
                MenuFrame menuF = new MenuFrame(c);
             	menuF.setSize(600,600);
                menuF.setVisible( true );
				//ListaStartowaFrame listFrame_k = new ListaStartowaFrame(c,false);

		    }
		        catch(SQLException e)  {
		            System.out.println("Blad podczas przetwarzania danych:"+e) ;  }      
		        }
		        else
		            System.out.println("You should never get here.");
	}
}