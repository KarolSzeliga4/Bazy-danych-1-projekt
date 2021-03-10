import java.awt.event.*; 
import java.awt.*;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class ListaStartowaFrame extends JFrame{
	Connection c;
	boolean m_k;
    JSplitPane splitPane;
    JPanel panelButtons;
    JPanel panelTable;

	JButton dodajZawButton;
    JTextField tdodajZ[];
    JLabel ldodajZ[];
    JComboBox<String> listaKluby;
	JLabel liczbaZaw;
    int lZaw;
    JButton usunZawButton;
    JTextField tusunZ;
    JLabel lusunZ;

    JButton dodajDruButton;
    JTextField tdodajD[];
    JLabel ldodajD[];
    JButton usunDruButton;
    JTextField tusunD;
    JLabel lusunD;

    JButton dodajMikButton;
    JTextField tdodajM[];
    JLabel ldodajM[];
    JButton usunMikButton;
    JTextField tusunM;
    JLabel lusunM;

	JTable jt;
    JScrollPane sp;
    static boolean first = true;
    String data[][] = new String[300][7];

	ListaStartowaFrame(Connection c_from_main,boolean m_k_from_main) throws SQLException{
		c = c_from_main;
		m_k = m_k_from_main;
        String title;
        if(m_k)
            title = "Lista startowa mężczyzn";
        else
            title = "Lista startowa kobiet";
        setTitle(title);
	setLocationRelativeTo(null);
        setBackground(Color.gray);
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );

        // Create the panels
        createPanelButtons();
        createPanelList();

        splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        topPanel.add( splitPane, BorderLayout.CENTER );
        splitPane.setLeftComponent( panelButtons );
        splitPane.setRightComponent( panelTable );
	}

    public void createPanelButtons() throws SQLException{
        panelButtons = new JPanel();
        panelButtons.setLayout( null );
        panelButtons.setMinimumSize( new Dimension( 1170, 280 ) );

        //dodawanie zawodnika
        String labelText[] = {"Imie:","Nazwisko:","Klub:"};
        ldodajZ = new JLabel[3];
        tdodajZ = new JTextField[2];
        for(int i=0; i<2; i++){
            ldodajZ[i] = new JLabel(labelText[i]);
            ldodajZ[i].setBounds(10,5+35*i, 75,30);
            panelButtons.add(ldodajZ[i]);
            tdodajZ[i]=new JTextField(30);
            tdodajZ[i].setBounds(90,5+35*i, 150,30);
            panelButtons.add(tdodajZ[i]);
        }
        ldodajZ[2] = new JLabel(labelText[2]);
        ldodajZ[2].setBounds(10,5+35*2, 75,30);
        panelButtons.add(ldodajZ[2]);
        listaKluby = new JComboBox<String>(getListaKluby());
        listaKluby.setBounds(90,5+35*2, 150,30);
        panelButtons.add(listaKluby);

        dodajZawButton =new JButton("dodaj zawodnika");
        dodajZawButton.setBounds(10,115,160,30);
        panelButtons.add(dodajZawButton);

        liczbaZaw=new JLabel("Aktualna liczba zawodników: ");  
        liczbaZaw.setBounds(10,150, 400,30);
        panelButtons.add(liczbaZaw);

        dodajZawButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                	if( !(tdodajZ[0].getText().equals("") || tdodajZ[1].getText().equals("")) ){  
	                    CallableStatement cst = c.prepareCall( "{call zawody.dodajZawodnika(?,?,?,?)}" );
	                    cst.setString(1,tdodajZ[0].getText());
	                    cst.setString(2,tdodajZ[1].getText());
	                    cst.setString(3,listaKluby.getSelectedItem().toString());
	                    cst.setBoolean(4,m_k);
	                    cst.executeQuery();
	                    panelTable.removeAll();
						panelTable.revalidate();
						panelTable.repaint();
	                    listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
	                    tdodajZ[0].setText("");
	                    tdodajZ[1].setText("");
	                }
	                else{
	                	tdodajZ[0].setText("podaj imie i nazwisko");
	                }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                }
            }
        });

        //usuwanie zawodnika
        usunZawButton =new JButton("usun zawodnika");
        usunZawButton.setBounds(10,245,160,30);
        panelButtons.add(usunZawButton);

        lusunZ=new JLabel("Nr. zawodnika:");  
        lusunZ.setBounds(10,210, 120,30);
        panelButtons.add(lusunZ);
        tusunZ=new JTextField(30);  
        tusunZ.setBounds(120,210,30,30);
        panelButtons.add(tusunZ);

        usunZawButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                	if(Integer.parseInt(tusunZ.getText())>0 && Integer.parseInt(tusunZ.getText())<299){	
	                    CallableStatement cst = c.prepareCall( "{call zawody.usunZawodnika(?)}" );
	                    cst.setInt(1,Integer.parseInt(data[ Integer.parseInt(tusunZ.getText())-1 ][6]));
	                    cst.executeQuery();
	                    panelTable.removeAll();
						panelTable.revalidate();
						panelTable.repaint();
	                    listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
	                    tusunZ.setText("");
	                }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                }
            }
        });

        //dodawanie druzyny
        String labelText2[] = {"Nr. zawodników:","Nazwa drużyny:"};
        ldodajD = new JLabel[2];
        tdodajD = new JTextField[6];
        for(int i=0; i<2; i++){
            ldodajD[i] = new JLabel(labelText2[i]);
            ldodajD[i].setBounds(310,5+60*i,140,30);
            panelButtons.add(ldodajD[i]);
        }
        for(int i=0; i<5; i++){
            tdodajD[i]=new JTextField(30);
            tdodajD[i].setBounds(320+35*i,35, 30,30);
            panelButtons.add(tdodajD[i]);
        }
        tdodajD[5]=new JTextField(50);
        tdodajD[5].setBounds(320,95, 150,30);
        panelButtons.add(tdodajD[5]);
     
        dodajDruButton =new JButton("dodaj drużyne");
        dodajDruButton.setBounds(310,130,160,30);
        panelButtons.add(dodajDruButton);

        dodajDruButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if( !(tdodajD[0].getText().equals("") || tdodajD[1].getText().equals("") || tdodajD[2].getText().equals("")) ){
                        if(!tdodajD[5].getText().equals("")){
                            CallableStatement cst = c.prepareCall( "{call zawody.dodajDrużyne(?,?,?,?,?,?,?)}" );
                            cst.setInt(1,Integer.parseInt(data[ Integer.parseInt(tdodajD[0].getText())-1 ][6]));
                            cst.setInt(2,Integer.parseInt(data[ Integer.parseInt(tdodajD[1].getText())-1 ][6]));
                            cst.setInt(3,Integer.parseInt(data[ Integer.parseInt(tdodajD[2].getText())-1 ][6]));
                            if(tdodajD[3].getText().equals(""))
                                cst.setInt(4,-1);
                            else    
                                cst.setInt(4,Integer.parseInt(data[ Integer.parseInt(tdodajD[3].getText())-1 ][6]));
                            if(tdodajD[4].getText().equals(""))
                                cst.setInt(5,-1);
                            else    
                                cst.setInt(5,Integer.parseInt(data[ Integer.parseInt(tdodajD[4].getText())-1 ][6]));

                            cst.setString(6,tdodajD[5].getText());
                            cst.setBoolean(7,m_k);
                            ResultSet rs ;
                            rs = cst.executeQuery();
                            rs.next();
                            if(rs.getString(1).equals("1")){
                                panelTable.removeAll();
                                panelTable.revalidate();
                                panelTable.repaint();
                                listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
                                for (int i =0;i<6 ;i++ )
                                    tdodajD[i].setText("");
                            }
                            else{
                                tdodajD[5].setText("min. 3 pierwsze boxy +osoby nie mogą mieć druzyn +z jednego klubu");
                            }
                        }
                    }
                    else{
                        tdodajD[5].setText("min. 3 pierwsze boxy +nie mogą mieć druzyn +z jednego klubu");
                    }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tdodajD[5].setText("min. 3 pierwsze boxy +nie mogą mieć druzyn +z jednego klubu");
                }
            }
        });

        //usuwanie drużyny
        usunDruButton =new JButton("usun jego druzyne");
        usunDruButton.setBounds(310,245,180,30);
        panelButtons.add(usunDruButton);

        lusunD=new JLabel("Nr. zawodnika:");  
        lusunD.setBounds(310,210, 110,30);
        panelButtons.add(lusunD);
        tusunD=new JTextField(30);  
        tusunD.setBounds(420,210,30,30);
        panelButtons.add(tusunD);

        usunDruButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if(Integer.parseInt(tusunD.getText())>0 && Integer.parseInt(tusunD.getText())<299){
                        if(!data[ Integer.parseInt(tusunD.getText())-1 ][4].equals("")){ 
                            CallableStatement cst = c.prepareCall( "{call zawody.usunDrużyne(?)}" );
                            cst.setInt(1,Integer.parseInt(data[ Integer.parseInt(tusunD.getText())-1 ][6]));
                            cst.executeQuery();
                            panelTable.removeAll();
                            panelTable.revalidate();
                            panelTable.repaint();
                            listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
                            tusunD.setText("");
                        }
                        else
                            tusunD.setText("ten zawodnik nie ma drużyny");
                    }
                    else
                        tusunD.setText("błąd");
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tusunD.setText("błąd");
                }
            }
        });

        //dodawanie miksta
        String labelText3[] = {"Nr. zawodnika:","Nazwa miksta:","Pamiętaj, aby dodać taki sam mikst","drugiemu zawodnikowi (na drugiej liście)"};
        ldodajM = new JLabel[4];
        tdodajM = new JTextField[2];
        ldodajM[0] = new JLabel(labelText3[0]);
        ldodajM[0].setBounds(600,5,110,30);
        panelButtons.add(ldodajM[0]);
        ldodajM[1] = new JLabel(labelText3[1]);
        ldodajM[1].setBounds(600,35,110,30);
        panelButtons.add(ldodajM[1]);
        ldodajM[2] = new JLabel(labelText3[2]);
        ldodajM[2].setBounds(600,120,290,30);
        panelButtons.add(ldodajM[2]);
        ldodajM[3] = new JLabel(labelText3[3]);
        ldodajM[3].setBounds(600,140,290,30);
        panelButtons.add(ldodajM[3]);
        
        tdodajM[0]=new JTextField(30);
        tdodajM[0].setBounds(710,5, 30,30);
        panelButtons.add(tdodajM[0]);
        tdodajM[1]=new JTextField(30);
        tdodajM[1].setBounds(610,60,150,30);
        panelButtons.add(tdodajM[1]);    
        
        dodajMikButton =new JButton("dodaj mikst");
        dodajMikButton.setBounds(600,95,160,30);
        panelButtons.add(dodajMikButton);

        dodajMikButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if( !(tdodajM[0].getText().equals("") || tdodajM[1].getText().equals("")) ){
                        CallableStatement cst = c.prepareCall( "{call zawody.dodajMikst(?,?)}" );
                        cst.setInt(1,Integer.parseInt(data[ Integer.parseInt(tdodajM[0].getText())-1 ][6]));
                        cst.setString(2,tdodajM[1].getText());
                        ResultSet rs ;
                        rs = cst.executeQuery();
                        rs.next();
                        if(rs.getString(1).equals("1")){
                            panelTable.removeAll();
                            panelTable.revalidate();
                            panelTable.repaint();
                            listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
                            for (int i =0;i<2 ;i++ )
                                tdodajM[i].setText("");
                        }
                        else{
                            tdodajD[1].setText("zawodnik musi nie mieć miksta");
                        }
                    }
                    else{
                        tdodajM[1].setText("Mikst musi mieć nazwę!");
                    }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tdodajD[1].setText("błąd");
                }
            }
        });

        //usuwanie miksta
        usunMikButton =new JButton("usun jego mikst");
        usunMikButton.setBounds(600,245,180,30);
        panelButtons.add(usunMikButton);

        lusunM=new JLabel("Nr. zawodnika:");  
        lusunM.setBounds(600,210, 110,30);
        panelButtons.add(lusunM);
        tusunM=new JTextField(30);  
        tusunM.setBounds(710,210,30,30);
        panelButtons.add(tusunM);

        usunMikButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if(Integer.parseInt(tusunM.getText())>0 && Integer.parseInt(tusunM.getText())<299){
                        if(!data[ Integer.parseInt(tusunM.getText())-1 ][5].equals("")){ 
                            CallableStatement cst = c.prepareCall( "{call zawody.usunMikst(?)}" );
                            cst.setInt(1,Integer.parseInt(data[ Integer.parseInt(tusunM.getText())-1 ][6]));
                            cst.executeQuery();
                            panelTable.removeAll();
                            panelTable.revalidate();
                            panelTable.repaint();
                            listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
                            tusunM.setText("");
                        }
                        else
                            tusunM.setText("ten zawodnik nie ma miksta");
                    }
                    else
                        tusunM.setText("błąd");
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tusunD.setText("błąd");
                }
            }
        });
    }

    public void createPanelList() throws SQLException{
        panelTable = new JPanel();
        panelTable.setLayout( new BorderLayout() );
        panelTable.setMinimumSize( new Dimension( 1170, 300 ) );
        listaZawodnikow(c,panelTable,jt,sp,data,liczbaZaw,lZaw,m_k);
    }

	public static void listaZawodnikow(Connection c,JPanel panelTable,JTable jt, JScrollPane sp, String data[][], JLabel liczbaZaw,int lZaw, boolean m_k) throws SQLException{
		Statement st = c.createStatement();
        String query;
        if(m_k)
            query = "SELECT * from zawody.lista_mezczyzn";
        else
            query = "SELECT * from zawody.lista_kobiet";
        ResultSet rs = st.executeQuery(query);

		String column[]={"Nr.","nazwisko", "imie", "klub", "drużyna", "mikst"};                     
        int i =0;
        while (rs.next())  {
            data[i][0] = i+1+".";
            data[i][1] = rs.getString("nazwisko");
            data[i][2] = rs.getString("imie");
            data[i][3] = rs.getString("nazwa");
            data[i][4] = rs.getString("druzyna");
            data[i][5] = rs.getString("mikst");
            data[i][6] = rs.getString("id_zawodnik");
        	i++;
        }
        rs.close();
        st.close(); 

        String tdata[][]= new String[i][6];
        for(int k=0; k<i; k++)
        	for(int j=0; j<6; j++)
        		tdata[k][j] = data[k][j];

        jt=new JTable(tdata, column);
		jt.setFont(new Font("Arial", Font.BOLD, 14));
		jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
		jt.setRowHeight(jt.getRowHeight() + 10);
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.getColumnModel().getColumn(0).setPreferredWidth(50);
		jt.getColumnModel().getColumn(1).setPreferredWidth(170);
		jt.getColumnModel().getColumn(2).setPreferredWidth(170);
		jt.getColumnModel().getColumn(3).setPreferredWidth(300);
		jt.getColumnModel().getColumn(4).setPreferredWidth(145);
		jt.getColumnModel().getColumn(5).setPreferredWidth(145);

        sp = new JScrollPane(jt);
        panelTable.add(sp);
        panelTable.setVisible(true);

        lZaw = i;
        liczbaZaw.setText("Aktualna liczba zawodników: "+i);
        System.out.println("pobieranie listy zawodników z bazy");
	}

	public String[] getListaKluby() throws SQLException{
		Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT nazwa from zawody.klub ORDER BY nazwa");

        String kdata[] = new String[100];                     
        int i =0;
        while (rs.next())  {
            kdata[i] = rs.getString("nazwa");
        	i++;
        }
        rs.close();
        st.close();
        String retKdata[]= new String[i];
        for(int j = 0;j<i;j++)
        	retKdata[j]=kdata[j];

        return retKdata;
    }
}
