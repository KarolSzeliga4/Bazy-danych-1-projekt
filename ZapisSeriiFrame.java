import java.awt.event.*; 
import java.awt.*;  
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.sql.*;
public class ZapisSeriiFrame extends JFrame{
    Connection c;
    boolean m_k;
    JSplitPane splitPaneUpD;
    JSplitPane splitPaneLeftR;
    JPanel panelButtons;
    JPanel panelMetryczka;
    JPanel panelRozstawienie;

    Integer ileZawodnikow;
    Integer aktualZawDod;
    Integer ileSeriiMaZaw;

    JButton dodajSerieButton;
    JLabel punktyDodLabel;
    JTextField punktyDodField;

    JButton poprawSerieButton;
    JLabel punktyPopLabel;
    JLabel nrSeriiLabel;
    JTextField punktyPopField;
    JTextField nrSeriiField;

    JButton nextZawButton;
    JTable jt;
    JScrollPane sp;
    static boolean first = true;
    String daneMetryczki[][] = new String[99][7];
    String listaRozstawienia[][] = new String[400][6];

    ZapisSeriiFrame(Connection c_from_main, boolean m_k_from_main) throws SQLException{
        c = c_from_main;
        m_k = m_k_from_main;
        String title;
        if(m_k)
            title = "Uzupełnianie serii kwalifikacji mężczyzn";
        else
            title = "Uzupełnianie serii kwalifikacji kobiet";

        setTitle(title);
	setLocationRelativeTo(null);
        setBackground(Color.gray);
        JPanel topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );

        createPanelList();
        createPanelButtons();

        splitPaneLeftR = new JSplitPane(SwingConstants.VERTICAL, panelMetryczka,panelRozstawienie ); 
        splitPaneLeftR.setOrientation(SwingConstants.VERTICAL);

        splitPaneUpD = new JSplitPane( JSplitPane.VERTICAL_SPLIT );
        topPanel.add( splitPaneUpD, BorderLayout.CENTER );
        splitPaneUpD.setLeftComponent(splitPaneLeftR);
        splitPaneUpD.setRightComponent(panelButtons);

    }

    public void createPanelButtons() throws SQLException{
        panelButtons = new JPanel();
        panelButtons.setLayout( null );
        panelButtons.setMinimumSize( new Dimension( 1000, 180 ) );

        //dodawanie serii
        punktyDodLabel = new JLabel("Punkty:");
        punktyDodLabel.setBounds(10,5,70,30);
        panelButtons.add(punktyDodLabel);
        
        punktyDodField=new JTextField(30);
        punktyDodField.setBounds(80,5, 70,30);
        panelButtons.add(punktyDodField);

        dodajSerieButton =new JButton("dodaj Serie");
        dodajSerieButton.setBounds(10,35,140,30);
        panelButtons.add(dodajSerieButton);

        dodajSerieButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if( czyPoprawna(punktyDodField.getText()) ){
                        CallableStatement cst = c.prepareCall( "{call zawody.dodajSerie(?,?)}" );
                        cst.setInt(1, Integer.parseInt(listaRozstawienia[aktualZawDod][5]) );
                        cst.setString(2,punktyDodField.getText());
                        cst.executeQuery();
                        panelMetryczka.removeAll();
                        panelMetryczka.revalidate();
                        panelMetryczka.repaint();
                        punktyDodField.setText("");
                        ListaKluby(Integer.parseInt(listaRozstawienia[aktualZawDod][5]));
                    }
                    else
                        punktyDodField.setText("dane niepoprawne");
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                }
            }
        });

        //poprawianie serii
        punktyPopLabel = new JLabel("Punkty:");
        punktyPopLabel.setBounds(410,5,70,30);
        panelButtons.add(punktyPopLabel);
        nrSeriiLabel = new JLabel("Nr. serii:");
        nrSeriiLabel.setBounds(410,35,70,30);
        panelButtons.add(nrSeriiLabel);
        
        punktyPopField=new JTextField(30);
        punktyPopField.setBounds(480,5, 70,30);
        panelButtons.add(punktyPopField);
        nrSeriiField=new JTextField(30);
        nrSeriiField.setBounds(480,35, 30,30);
        panelButtons.add(nrSeriiField);

        poprawSerieButton =new JButton("popraw Serie");
        poprawSerieButton.setBounds(410,70,140,30);
        panelButtons.add(poprawSerieButton);

        poprawSerieButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                    if( czyPoprawna(punktyPopField.getText()) ){
                        if(Integer.parseInt(nrSeriiField.getText())>0 && Integer.parseInt(nrSeriiField.getText())<=ileSeriiMaZaw){
                            CallableStatement cst = c.prepareCall( "{call zawody.poprawSerie(?,?,?)}" );
                            cst.setInt(1, Integer.parseInt(listaRozstawienia[aktualZawDod][5]) );
                            cst.setInt(2,Integer.parseInt(nrSeriiField.getText()));
                            cst.setString(3,punktyPopField.getText());
                            cst.executeQuery();
                            panelMetryczka.removeAll();
                            panelMetryczka.revalidate();
                            panelMetryczka.repaint();
                            punktyPopField.setText("");
                            nrSeriiField.setText(" ");
                            ListaKluby(Integer.parseInt(listaRozstawienia[aktualZawDod][5]));
                        }
                        else
                            nrSeriiField.setText(ileSeriiMaZaw.toString());
                    }
                    else
                        punktyPopField.setText("dane niepoprawne");
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                }
            }
        });

        nextZawButton =new JButton("Przejdź do następnego zawodnika");
        nextZawButton.setBounds(10,75,280,30);
        panelButtons.add(nextZawButton);

        nextZawButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                try{
                        panelMetryczka.removeAll();
                        panelMetryczka.revalidate();
                        panelMetryczka.repaint();
                        if(ileZawodnikow.equals(aktualZawDod))
                            aktualZawDod = 0;
                        else
                            aktualZawDod++;

                        ListaKluby(Integer.parseInt(listaRozstawienia[aktualZawDod][5]));
                }
                catch(SQLException err)  {
                    System.out.println("Blad podczas przetwarzania danych:"+err);
                }
            }
        });

    }

    public void createPanelList() throws SQLException{
        panelRozstawienie = new JPanel();
        panelRozstawienie.setLayout( new BorderLayout() );
        panelRozstawienie.setMinimumSize( new Dimension( 500, 110 ) );
        panelRozstawienie.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),"Rozstawienie",TitledBorder.LEFT,TitledBorder.TOP,new Font("SansSerif", Font.BOLD, 16)));
        ListaTrenerzy();

        panelMetryczka = new JPanel();
        panelMetryczka.setLayout( new BorderLayout() );
        panelMetryczka.setMinimumSize( new Dimension( 500, 110 ) );
        aktualZawDod = 0;
        ListaKluby(Integer.parseInt(listaRozstawienia[aktualZawDod][5]));
    }

    public void ListaKluby(int id_zaw) throws SQLException{

        CallableStatement cst = c.prepareCall( "{call zawody.metryczka(?)}" );
        cst.setInt(1,id_zaw);
        ResultSet rs ;
        rs = cst.executeQuery();

        int i=0;
        while (rs.next())  {
            daneMetryczki[i][0] = rs.getString("Nr_serii");
            daneMetryczki[i][1] = rs.getString("_1");
            daneMetryczki[i][2] = rs.getString("_2");
            daneMetryczki[i][3] = rs.getString("_3");
            daneMetryczki[i][4] = rs.getString("sum3");
            daneMetryczki[i][5] = rs.getString("sum6");
            daneMetryczki[i][6] = rs.getString("sum_max");
            i++;
        }
        ileSeriiMaZaw = i/2;
        rs.close();
        String retKdaneMetryczki[][]= new String[i][7];
        for(int j = 0;j<i;j++)
            for(int p = 0; p<7;p++)
                retKdaneMetryczki[j][p]=daneMetryczki[j][p];

        String column[]={"Nr. serii","_1", "_2","_3", "sum3","sum6", "sumMax"};
        jt=new JTable(retKdaneMetryczki, column);
        jt.setFont(new Font("Arial", Font.BOLD, 14));
        jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        jt.setRowHeight(jt.getRowHeight() + 6);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.getColumnModel().getColumn(0).setPreferredWidth(100);
        jt.getColumnModel().getColumn(1).setPreferredWidth(50);
        jt.getColumnModel().getColumn(2).setPreferredWidth(50);
        jt.getColumnModel().getColumn(3).setPreferredWidth(50);
        jt.getColumnModel().getColumn(4).setPreferredWidth(50);
        jt.getColumnModel().getColumn(5).setPreferredWidth(50);
        jt.getColumnModel().getColumn(6).setPreferredWidth(80);

        sp = new JScrollPane(jt);
        panelMetryczka.add(sp);
        panelMetryczka.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder ()
                                , "Zapis metryczki: "+listaRozstawienia[aktualZawDod][4]+" "+listaRozstawienia[aktualZawDod][3]+"   Stanow.: "+listaRozstawienia[aktualZawDod][1]+listaRozstawienia[aktualZawDod][2]
                                , TitledBorder.LEFT,TitledBorder.TOP,new Font("SansSerif", Font.BOLD, 16)));
        panelMetryczka.setVisible(true);

        System.out.println("pobieranie metryczki z bazy");
    }

    public void ListaTrenerzy() throws SQLException{
        Statement st = c.createStatement();
        String query;
        if(m_k)
            query = "SELECT mata, stanowisko, nazwisko, imie, id_zawodnik FROM zawody.rozstawienie_mezczyzn";
        else
            query = "SELECT mata, stanowisko, nazwisko, imie, id_zawodnik FROM zawody.rozstawienie_kobiet";

        ResultSet rs = st.executeQuery(query);

        int i =0;
        while (rs.next())  {
            listaRozstawienia[i][0] = i+1+".";
            listaRozstawienia[i][1] = rs.getString("mata");
            listaRozstawienia[i][2] = rs.getString("stanowisko");
            listaRozstawienia[i][3] = rs.getString("nazwisko");
            listaRozstawienia[i][4] = rs.getString("imie");
            listaRozstawienia[i][5] = rs.getString("id_zawodnik");
            i++;
        }
        ileZawodnikow = i-1;
        rs.close();
        st.close();
        String c_listaRozstawienia[][]= new String[i][4];
        for(int j = 0;j<i;j++){
            c_listaRozstawienia[j][0]=listaRozstawienia[j][0];
            c_listaRozstawienia[j][1]=listaRozstawienia[j][1];
            c_listaRozstawienia[j][2]=listaRozstawienia[j][2];
            c_listaRozstawienia[j][3]=listaRozstawienia[j][3];
        }

        String column[]={"Nr.","mata", "stanowisko","nazwisko"};
        jt=new JTable(c_listaRozstawienia, column);
        jt.setFont(new Font("Arial", Font.BOLD, 14));
        jt.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        jt.setRowHeight(jt.getRowHeight() + 10);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jt.getColumnModel().getColumn(0).setPreferredWidth(50);
        jt.getColumnModel().getColumn(1).setPreferredWidth(90);
        jt.getColumnModel().getColumn(2).setPreferredWidth(90);
        jt.getColumnModel().getColumn(3).setPreferredWidth(230);

        sp = new JScrollPane(jt);
        panelRozstawienie.add(sp);
        panelRozstawienie.setVisible(true);

        System.out.println("pobieranie rozstawienia z bazy");
    }

    public String[] getListaWoj() throws SQLException{
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT nazwa from zawody.wojewodztwo ORDER BY nazwa");

        String kdaneMetryczki[] = new String[100];                     
        int i =0;
        while (rs.next())  {
            kdaneMetryczki[i] = rs.getString("nazwa");
            i++;
        }
        rs.close();
        st.close();
        String retKdaneMetryczki[]= new String[i];
        for(int j = 0;j<i;j++)
            retKdaneMetryczki[j]=kdaneMetryczki[j];

        return retKdaneMetryczki;
    }

    public String[] getListaKluby() throws SQLException{
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT nazwa from zawody.klub ORDER BY nazwa");

        String kdaneMetryczki[] = new String[100];                     
        int i =0;
        while (rs.next())  {
            kdaneMetryczki[i] = rs.getString("nazwa");
            i++;
        }
        rs.close();
        st.close();
        String retKdaneMetryczki[]= new String[i];
        for(int j = 0;j<i;j++)
            retKdaneMetryczki[j]=kdaneMetryczki[j];

        return retKdaneMetryczki;
    }

    public static boolean czyPoprawna(String punkty){
        if(punkty.length() != 6)
            return false;
        else{
            boolean inny = true;
            char[] mozliweDane = {'x','d','9','8','7','6','5','4','3','2','1','0'};
            for(int i=0; i<6; i++){
                for(int j=0; j<12; j++){
                    if(punkty.charAt(i) == mozliweDane[j])
                        inny = false;
                }
                if(inny)
                    return false;
            }
            return true;
        }

    }


}
