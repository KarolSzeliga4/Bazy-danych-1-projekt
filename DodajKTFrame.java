import java.awt.event.*; 
import java.awt.*;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class DodajKTFrame extends JFrame{
    Connection c;
    JSplitPane splitPaneUpD;
    JSplitPane splitPaneLeftR;
    JPanel panelButtons;
    JPanel panelTableK;
    JPanel panelTableT;

    JButton dodajKlubButton;
    JTextField tdodajK;
    JComboBox<String> listaWoj;
    JLabel ldodajK[];
    JButton usunKlubButton;
    JTextField tusunK;
    JLabel lusunK;

    JButton dodajZawButton;
    JTextField tdodajZ[];
    JLabel ldodajZ[];
    JComboBox<String> listaKluby;
    JLabel liczbaZaw;
    int lZaw;
    JButton usunZawButton;
    JTextField tusunZ;
    JLabel lusunZ;

    JTable jt;
    JScrollPane sp;
    static boolean first = true;
    String data[][] = new String[100][3];
    String dataT[][] = new String[400][5];

    DodajKTFrame(Connection c_from_main) throws SQLException{
        c = c_from_main;
        setTitle("Lista klubów i trenerów");
	setLocationRelativeTo(null);
        setBackground(Color.gray);
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );

        createPanelButtons();
        createPanelList();

        splitPaneLeftR = new JSplitPane(SwingConstants.VERTICAL, panelTableK, panelTableT); 
        splitPaneLeftR.setOrientation(SwingConstants.VERTICAL);

        splitPaneUpD = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        topPanel.add( splitPaneUpD, BorderLayout.CENTER );
        splitPaneUpD.setLeftComponent( panelButtons );
        splitPaneUpD.setRightComponent( splitPaneLeftR );
    }

    public void createPanelButtons() throws SQLException{
        panelButtons = new JPanel();
        panelButtons.setLayout( null );
        panelButtons.setMinimumSize( new Dimension( 1000, 280 ) );

        //dodawanie klubu
        String labelText1[] = {"Nazwa klubu:","Województwo:"};
        ldodajK = new JLabel[2];
        ldodajK[0] = new JLabel(labelText1[0]);
        ldodajK[0].setBounds(10,5,110,30);
        panelButtons.add(ldodajK[0]);
        ldodajK[1] = new JLabel(labelText1[1]);
        ldodajK[1].setBounds(10,70,110,30);
        panelButtons.add(ldodajK[1]);
        
        tdodajK=new JTextField(30);
        tdodajK.setBounds(10,35, 300,30);
        panelButtons.add(tdodajK);
        listaWoj = new JComboBox<String>(getListaWoj());
        listaWoj.setBounds(10,100, 150,30);
        panelButtons.add(listaWoj);    
        
        dodajKlubButton =new JButton("dodaj klub");
        dodajKlubButton.setBounds(10,135,160,30);
        panelButtons.add(dodajKlubButton);

        dodajKlubButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if( !tdodajK.getText().equals("") ){
                        CallableStatement cst = c.prepareCall( "{call zawody.dodajKlub(?,?)}" );
                        cst.setString(1,tdodajK.getText());
                        cst.setString(2,listaWoj.getSelectedItem().toString());
                        ResultSet rs ;
                        rs = cst.executeQuery();
                        rs.next();
                        if(rs.getString(1).equals("1")){
                            panelTableK.removeAll();
                            panelTableK.revalidate();
                            panelTableK.repaint();
                            ListaKluby();
                            tdodajK.setText("");
                        }
                        else{
                            tdodajK.setText("nazwa musi byc unikalna");
                        }
                    }
                    else{
                        tdodajK.setText("klub musi mieć nazwę!");
                    }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tdodajK.setText("błąd");
                }
            }
        });

        usunKlubButton =new JButton("usun klub");
        usunKlubButton.setBounds(10,245,180,30);
        panelButtons.add(usunKlubButton);

        lusunK=new JLabel("Nr. klubu:");  
        lusunK.setBounds(10,210, 110,30);
        panelButtons.add(lusunK);
        tusunK=new JTextField(30);  
        tusunK.setBounds(120,210,30,30);
        panelButtons.add(tusunK);

        usunKlubButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if(Integer.parseInt(tusunK.getText())>0 && Integer.parseInt(tusunK.getText())<400){
                        CallableStatement cst = c.prepareCall( "{call zawody.usunKlub(?)}" );
                        cst.setString(1,data[ Integer.parseInt(tusunK.getText())-1 ][1]);
                        cst.executeQuery();
                        panelTableK.removeAll();
                        panelTableK.revalidate();
                        panelTableK.repaint();
                        ListaKluby();
                        tusunK.setText("");
                    }
                    else
                        tusunK.setText("błąd");
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tusunK.setText("błąd");
                }
            }
        });

        //dodawanie trenera
        String labelText[] = {"Imie:","Nazwisko:","Klub:"};
        ldodajZ = new JLabel[3];
        tdodajZ = new JTextField[2];
        for(int i=0; i<2; i++){
            ldodajZ[i] = new JLabel(labelText[i]);
            ldodajZ[i].setBounds(510,5+35*i, 75,30);
            panelButtons.add(ldodajZ[i]);
            tdodajZ[i]=new JTextField(30);
            tdodajZ[i].setBounds(590,5+35*i, 150,30);
            panelButtons.add(tdodajZ[i]);
        }
        ldodajZ[2] = new JLabel(labelText[2]);
        ldodajZ[2].setBounds(510,5+35*2, 75,30);
        panelButtons.add(ldodajZ[2]);
        listaKluby = new JComboBox<String>(getListaKluby());
        listaKluby.setBounds(590,5+35*2, 150,30);
        panelButtons.add(listaKluby);

        dodajZawButton =new JButton("dodaj trenera");
        dodajZawButton.setBounds(510,115,160,30);
        panelButtons.add(dodajZawButton);

        dodajZawButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if( !(tdodajZ[0].getText().equals("") || tdodajZ[1].getText().equals("")) ){  
                        CallableStatement cst = c.prepareCall( "{call zawody.dodajTrenera(?,?,?)}" );
                        cst.setString(1,tdodajZ[0].getText());
                        cst.setString(2,tdodajZ[1].getText());
                        cst.setString(3,listaKluby.getSelectedItem().toString());
                        cst.executeQuery();
                        panelTableT.removeAll();
                        panelTableT.revalidate();
                        panelTableT.repaint();
                        ListaTrenerzy();
                        tdodajZ[0].setText("");
                        tdodajZ[1].setText("");
                    }
                    else{
                        tdodajZ[0].setText("podaj imie i nazwisko");
                    }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tdodajZ[0].setText("błąd");
                }
            }
        });

        //usuwanie trenera
        usunZawButton =new JButton("usun trenera");
        usunZawButton.setBounds(510,245,160,30);
        panelButtons.add(usunZawButton);

        lusunZ=new JLabel("Nr. trenera:");  
        lusunZ.setBounds(510,210, 120,30);
        panelButtons.add(lusunZ);
        tusunZ=new JTextField(30);  
        tusunZ.setBounds(620,210,30,30);
        panelButtons.add(tusunZ);

        usunZawButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if(Integer.parseInt(tusunZ.getText())>0 && Integer.parseInt(tusunZ.getText())<299){ 
                        CallableStatement cst = c.prepareCall( "{call zawody.usunTrenera(?)}" );
                        cst.setInt(1,Integer.parseInt(dataT[ Integer.parseInt(tusunZ.getText())-1 ][4]));
                        cst.executeQuery();
                        panelTableT.removeAll();
                        panelTableT.revalidate();
                        panelTableT.repaint();
                        ListaTrenerzy();
                        tusunZ.setText("");
                    }
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                    tusunZ.setText("błąd");
                }
            }
        });

    }

    public void createPanelList() throws SQLException{
        panelTableK = new JPanel();
        panelTableK.setLayout( new BorderLayout() );
        panelTableK.setMinimumSize( new Dimension( 500, 200 ) );
        ListaKluby();

        panelTableT = new JPanel();
        panelTableT.setLayout( new BorderLayout() );
        panelTableT.setMinimumSize( new Dimension( 500, 200 ) );
        ListaTrenerzy();
    }

    public void ListaKluby() throws SQLException{
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT klub.nazwa as k, wojewodztwo.nazwa as w from zawody.klub JOIN zawody.wojewodztwo USING(id_wojewodztwo) ORDER BY klub.nazwa");

        int i =0;
        while (rs.next())  {
            data[i][0] = i+1+".";
            data[i][1] = rs.getString("k");
            data[i][2] = rs.getString("w");
            i++;
        }
        rs.close();
        st.close();
        String retKdata[][]= new String[i][3];
        for(int j = 0;j<i;j++){
            retKdata[j][0]=data[j][0];
            retKdata[j][1]=data[j][1];
            retKdata[j][2]=data[j][2];
        }

        String column[]={"Nr.","nazwa klubu", "województwo"};
        jt=new JTable(retKdata, column);
        jt.setFont(new Font("Arial", Font.BOLD, 14));
        jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        jt.setRowHeight(jt.getRowHeight() + 10);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.getColumnModel().getColumn(0).setPreferredWidth(50);
        jt.getColumnModel().getColumn(1).setPreferredWidth(230);
        jt.getColumnModel().getColumn(2).setPreferredWidth(170);

        sp = new JScrollPane(jt);
        panelTableK.add(sp);
        panelTableK.setVisible(true);

        System.out.println("pobieranie listy klubów z bazy");
    }

    public void ListaTrenerzy() throws SQLException{
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_trener, imie, nazwisko, nazwa from zawody.trener JOIN zawody.klub USING(id_klub) ORDER BY nazwisko");

        int i =0;
        while (rs.next())  {
            dataT[i][0] = i+1+".";
            dataT[i][1] = rs.getString("imie");
            dataT[i][2] = rs.getString("nazwisko");
            dataT[i][3] = rs.getString("nazwa");
            dataT[i][4] = rs.getString("id_trener");
            i++;
        }
        rs.close();
        st.close();
        String retKdata[][]= new String[i][4];
        for(int j = 0;j<i;j++){
            retKdata[j][0]=dataT[j][0];
            retKdata[j][1]=dataT[j][1];
            retKdata[j][2]=dataT[j][2];
            retKdata[j][3]=dataT[j][3];
        }

        String column[]={"Nr.","imie", "nazwisko","klub"};
        jt=new JTable(retKdata, column);
        jt.setFont(new Font("Arial", Font.BOLD, 14));
        jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        jt.setRowHeight(jt.getRowHeight() + 10);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.getColumnModel().getColumn(0).setPreferredWidth(50);
        jt.getColumnModel().getColumn(1).setPreferredWidth(170);
        jt.getColumnModel().getColumn(2).setPreferredWidth(170);
        jt.getColumnModel().getColumn(3).setPreferredWidth(230);

        sp = new JScrollPane(jt);
        panelTableT.add(sp);
        panelTableT.setVisible(true);

        System.out.println("pobieranie listy trenerów z bazy");
    }

    public String[] getListaWoj() throws SQLException{
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT nazwa from zawody.wojewodztwo ORDER BY nazwa");

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
