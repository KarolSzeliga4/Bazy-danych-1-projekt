import java.awt.event.*; 
import java.awt.*;  
import javax.swing.*;
import java.sql.*;
public class MenuFrame extends JFrame{
	Connection c;
	DodajKTFrame dodajKTFrame;
	ListaStartowaFrame listFrame_k;
	ListaStartowaFrame listFrame_m;
	Rozstawienie rozstawienieK = new Rozstawienie();
	Rozstawienie rozstawienieM = new Rozstawienie();
	Wyniki wynikiK = new Wyniki();
	Wyniki wynikiM = new Wyniki();
	Wyniki wynikiDruK = new Wyniki();
	Wyniki wynikiDruM = new Wyniki();
	Wyniki wynikiMikst = new Wyniki();
	Wyniki wynikiKlub = new Wyniki();
	Wyniki wynikiWojew = new Wyniki();
	ZapisSeriiFrame zapisKobiet;
	ZapisSeriiFrame zapisMezczyzn;
	MenuFrame(Connection c_from_main){
		c = c_from_main;
		setTitle("Menu zawodów łuczniczych");
		setLocationRelativeTo(null);
     	JPanel menuP = new JPanel();
     	menuP.setLayout( null );
     	menuP.setMinimumSize( new Dimension( 500, 500 ) );

     	JButton dodajKTButton = new JButton("Dodaj klub lub trenera");
     	dodajKTButton.setBounds(10,10,270,40);
		menuP.add(dodajKTButton);
		dodajKTButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	dodajKTFrame = new DodajKTFrame(c);
	        		dodajKTFrame.setSize(1200,600);
	        		dodajKTFrame.setVisible( true );
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

     	JButton listaKButton = new JButton("Otwórz liste startową kobiet");
     	listaKButton.setBounds(10,120,270,40);
		menuP.add(listaKButton);
		listaKButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	listFrame_k = new ListaStartowaFrame(c,false);
	        		listFrame_k.setSize(1170,700);
	        		listFrame_k.setVisible( true );
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton listaMButton = new JButton("Otwórz liste startową mężczyzn");
     	listaMButton.setBounds(10,170,270,40);
		menuP.add(listaMButton);
		listaMButton.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){
	        	try{
	            	listFrame_m = new ListaStartowaFrame(c,true);
	        		listFrame_m.setSize(1170,700);
	        		listFrame_m.setVisible( true );
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton startKwalMButton = new JButton("Rozpocznij kwalifikacje mężczyzn");
     	startKwalMButton.setBounds(290,170,270,40);
		menuP.add(startKwalMButton);
		startKwalMButton.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){
	        	try{
	        		CallableStatement cst = c.prepareCall( "{call zawody.generujRozstawienieKwal(?,?)}" );
	        		cst.setBoolean(1,true);
	        		cst.setInt(2,6);
                    ResultSet rs ;
                    rs = cst.executeQuery();
                    rs.next();
                    rs.close();
	        		rozstawienieM.startRozstawienieFrame(c,true);
	        		zapisMezczyzn = new ZapisSeriiFrame(c,true);
	        		zapisMezczyzn.setSize(1200,600);
	        		zapisMezczyzn.setVisible( true );
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton startKwalKButton = new JButton("Rozpocznij kwalifikacje kobiet");
     	startKwalKButton.setBounds(290,120,270,40);
		menuP.add(startKwalKButton);
		startKwalKButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	        		CallableStatement cst = c.prepareCall( "{call zawody.generujRozstawienieKwal(?,?)}" );
	        		cst.setBoolean(1,false);
	        		cst.setInt(2,6);
                    ResultSet rs ;
                    rs = cst.executeQuery();
                    rs.next();
                    rs.close();
	        		rozstawienieK.startRozstawienieFrame(c,false);
	        		zapisKobiet = new ZapisSeriiFrame(c,false);
	        		zapisKobiet.setSize(1200,600);
	        		zapisKobiet.setVisible( true );
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikMButton = new JButton("Ranking mężczyzn indywidualnie");
     	generujWynikMButton.setBounds(10,280,270,40);
		menuP.add(generujWynikMButton);
		generujWynikMButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiM.startWynikiFrame(c,true,1);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikKButton = new JButton("Ranking kobiet indywidualnie");
     	generujWynikKButton.setBounds(290,280,270,40);
		menuP.add(generujWynikKButton);
		generujWynikKButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiM.startWynikiFrame(c,false,1);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikDruMButton = new JButton("Ranking drużyn damskich");
     	generujWynikDruMButton.setBounds(290,330,270,40);
		menuP.add(generujWynikDruMButton);
		generujWynikDruMButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiDruM.startWynikiFrame(c,false,2);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikDruKButton = new JButton("Ranking drużyn męskich");
     	generujWynikDruKButton.setBounds(10,330,270,40);
		menuP.add(generujWynikDruKButton);
		generujWynikDruKButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiDruK.startWynikiFrame(c,true,2);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikMikstButton = new JButton("Ranking mikstów");
     	generujWynikMikstButton.setBounds(150,380,270,40);
		menuP.add(generujWynikMikstButton);
		generujWynikMikstButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiMikst.startWynikiFrame(c,true,3);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikKlubButton = new JButton("Ranking klubów");
     	generujWynikKlubButton.setBounds(150,450,270,40);
		menuP.add(generujWynikKlubButton);
		generujWynikKlubButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiKlub.startWynikiFrame(c,true,4);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

		JButton generujWynikWojewButton = new JButton("Ranking województw");
     	generujWynikWojewButton.setBounds(150,500,270,40);
		menuP.add(generujWynikWojewButton);
		generujWynikWojewButton.addActionListener(new ActionListener(){  
        	public void actionPerformed(ActionEvent e){
	        	try{
	            	wynikiWojew.startWynikiFrame(c,true,5);
	        	}
	        	catch(SQLException err){
	            	System.out.println("Blad podczas przetwarzania danych:"+err) ;  }	
    		}
		});

     	getContentPane().add(menuP);
	}
}
