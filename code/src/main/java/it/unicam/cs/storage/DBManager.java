package it.unicam.cs.storage;

import it.unicam.cs.esperienza.Esperienza;
import it.unicam.cs.esperienza.Tag;
import it.unicam.cs.esperienza.Tappa;
import it.unicam.cs.esperienza.Toponimo;
import it.unicam.cs.utente.Associazione;
import it.unicam.cs.utente.Cicerone;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DBManager {

    private static DBManager instance;
    private String url;
    private String user;
    private String pwd;
    private static Connection conn = null;



    public void setDBManager(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver? " + "Include in your library path!");
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("Database connected, ready to go!");
        } catch (SQLException e) {
            System.out.println("Problems in opening a connection to the DB");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problems in closing the connection to the DB");
            e.printStackTrace();
        }
    }

    public boolean DBtest() {
        Boolean result = true;
        try {
            if (conn == null || !conn.isClosed()) {
                connect();
                result = false;
            }
            DatabaseMetaData data = conn.getMetaData();
           System.out.println("Details on DBMS - " + data.getDatabaseProductName() + "\n" + "  version:  "
                    + data.getDriverMajorVersion() + "\n" //+ "  catalogs: " + data.getCatalogs().getCursorName() + "\n"
                   + "  schemas:  " + data.getSchemas().getRow() + "\n");
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getTableCount(String table) {
        int count = -1;
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT COUNT(*) AS rowcount FROM " + table);
            r.next();
            count = r.getInt("rowcount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("MyTable has " + count + " row(s).");

        return count;
    }

    public static ArrayList<String> trovaCiceroniAssociati(String emailAssociazione) {
        ArrayList<String> ciceroni = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT email FROM utente WHERE emailAssociazione = '" + emailAssociazione+"'");
            while(r.next()){ciceroni.add(r.getString("email"));};
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ciceroni;
    }

    public static void addCiceroneTo(String email, Associazione associazione) throws SQLException {
        associazione.getEmailCiceroniAssociati().add(email);
        Statement s = conn.createStatement();
        s.executeQuery(" UPDATE Utente set emailAssociazione = '"+associazione.getEmail()+"' WHERE email = '"+email+"' ");
    }

    public static boolean esistenzaCicerone(String emailCicerone) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT Utente WHERE email = '"+emailCicerone+"'");
        return r.next();
    }

    public static boolean controlloAssociazione(String email) throws SQLException{
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT Utente WHERE emailAssociazione = '"+email+"' ");
        return r.next();
    }

    public static boolean controllaEsistenza(String emailDaEliminare) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT Utente WHERE email = '"+emailDaEliminare+"' ");
        return r.next();
    }

    public static void eliminaUtente(String email, String motivazione) throws SQLException {
        Statement s = conn.createStatement();
        s.executeQuery("DELETE FROM Utente WHERE email ='"+email+"';");
        // invio String motivazione tramite Server email
    }
    public static void eliminareEsperienza(Esperienza esperienzaDaEliminare){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("DELETE FROM esperienza WHERE titolo='"+esperienzaDaEliminare.getTitolo()+"' AND descrizione = '"+esperienzaDaEliminare.getDescrizione()+"' " );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cicerone> listaCiceroni (){
        ArrayList<Cicerone> listaCiceroni = new ArrayList<>();

        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM utente WHERE tipo = 'c' " );
            while(r.next()){
                //TODO CONTROLLARE NOMI TABELLE NEL DB
                listaCiceroni.add(new Cicerone(r.getString("nome"),r.getString("cognome"),r.getString("email"),r.getString("password"),r.getString("emailassociazione")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCiceroni;
    }

    public static ArrayList<Cicerone> listaCiceroniAssociati(String emailAssociazione){
        ArrayList<Cicerone> listaCiceroni = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM utente WHERE emailAssociazione = '"+emailAssociazione+"' " );
            while(r.next()){
                //TODO CONTROLLARE NOMI TABELLE NEL DB
                listaCiceroni.add(new Cicerone(r.getString("nome"),r.getString("cognome"),r.getString("email"),r.getString("password"),r.getString("emailAssociazione")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCiceroni;
    }

    public static void eliminareCiceroneAssociazione (String emailCicerone){
        //TODO PER FARE UN UPDATE BISOGNA USARE executeUpdate
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("UPDATE utente SET emailAssociazione = NULL WHERE email = '"+emailCicerone+"'" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Date visualizzaDisponibilitaCicerone ( String emailCicerone){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT disponibilità WHERE emailCicerone = '"+emailCicerone+"'" );
            return r.getDate("data");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }

    public static void modoficaDisponibilita ( String emailCicerone, Date nuovaData){
        //TODO RIVEDERE: LA DATA VA AGGIUNTA, NON AGGIORNATA
        //E POI PER ESEGUIRE UN UPDATE O INSERT VA USATO executeUpdate
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("UPDATE disponibilita SET data = '"+nuovaData+"' WHERE email = '"+emailCicerone+"'" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> selezionaToponimiFigli(String genitore) {
        ArrayList<String> elencoToponimi = new ArrayList<>();
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT nome FROM toponimo WHERE Genitore = '"+genitore+"'");
            while(r.next()){
                elencoToponimi.add(r.getString("nome"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return elencoToponimi;
    }

    public static void registraEsperienza(Esperienza nuovaEsperienza) {
        try{
            Statement s = conn.createStatement();
            int id = nuovaEsperienza.getId();
            String titolo = nuovaEsperienza.getTitolo();
            String descrizione = nuovaEsperienza.getDescrizione();
            //Converto la data nel formato giusto per MySQL
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String mysqlDate = formatter.format(nuovaEsperienza.getData());
            int postiDisponibili = nuovaEsperienza.getPostiDisponibili();
            int postiMassimi = nuovaEsperienza.getPostiMassimi();
            int postiMinimi = nuovaEsperienza.getPostiMinimi();
            float prezzo = nuovaEsperienza.getPrezzo();
            String toponimo = nuovaEsperienza.getToponimo().getNome();
            int r = s.executeUpdate("INSERT INTO esperienza VALUES ("+id+",'"+titolo+"','"+descrizione+"','"+mysqlDate+"',"+postiDisponibili+","+ postiMassimi + "," + postiMinimi + "," + prezzo + ",' ',"+"'" + toponimo + "')");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR CODE:"+e.getErrorCode()+e.getMessage());

        }
    }

    public static void registraTappe(Esperienza nuovaEsperienza, ArrayList<Tappa> tappeEsperienza) {
        try{
            Statement s = conn.createStatement();
            int r;
            for (Tappa t:
                 tappeEsperienza) {
                String nomeTappa = t.getNome();
                String descrizioneTappa = t.getDescrizione();
                String indirizzoTappa = t.getIndirizzo();
                int idEsperienza = nuovaEsperienza.getId();
                r = s.executeUpdate("INSERT INTO tappa VALUES ('"+nomeTappa+"','"+ descrizioneTappa+ "','" + indirizzoTappa + "'," +idEsperienza + ")");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void registraTag(Esperienza nuovaEsperienza, ArrayList<Tag> tagEsperienza) {
        try{
            Statement s = conn.createStatement();
            int r;
            for (Tag t:
                    tagEsperienza) {
                String nomeTag = t.getName();
                int idEsperienza = nuovaEsperienza.getId();
                r = s.executeUpdate("INSERT INTO esperienza_tag VALUES ("+idEsperienza+",'"+ nomeTag + "')");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Tag> listaTag (){
        ArrayList<Tag> listaTag = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM tag" );
            while(r.next()){
                listaTag.add(new Tag (r.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaTag;
    }

    public static ArrayList<Toponimo> listaToponimo(){
        ArrayList<Toponimo> listaToponimo = new ArrayList<Toponimo>();
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM toponimo");
            while (r.next()) {
                listaToponimo.add(new Toponimo(r.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaToponimo;
    }

    public static ArrayList<java.util.Date> listaDate(){
        ArrayList<java.util.Date> listaDate = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT DISTINCT data FROM esperienza");
            while (r.next()) {
                listaDate.add(new Date(r.getDate("data").getYear(),r.getDate("data").getMonth(),r.getDate("data").getDay())   );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDate;
    }

    public static void ricercaConFiltri (Tag tag ,Toponimo toponimo , Date data, String parolaChiave){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM esperienza WHERE titolo LIKE '"+parolaChiave+"' AND tag = '"+tag+"' AND toponimo = '"+toponimo+"' AND data = '"+data+"' ");
            while (r.next()) {
                System.out.print("TITOLO" +r.getString("titolo"));
                System.out.print("DESCRIZIONE" +r.getString("descrizione"));
                System.out.print("DATA" +r.getString("data"));
                System.out.print("PREZZO" +r.getString("prezzo"));
                System.out.print("EMAIL GUIDA" +r.getString("emailGuida"));
                System.out.println("-------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ricercaParolaChiave ( String parolaChiave){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM esperienza WHERE titolo LIKE '"+parolaChiave+"'  ");
            while (r.next()) {
                System.out.print("TITOLO" +r.getString("titolo"));
                System.out.print("DESCRIZIONE" +r.getString("descrizione"));
                System.out.print("DATA" +r.getString("data"));
                System.out.print("PREZZO" +r.getString("prezzo"));
                System.out.print("EMAIL GUIDA" +r.getString("emailGuida"));
                System.out.println("-------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void registraGuidaEsperienza(Cicerone ciceroneDaAssociare, int idEsperienza) {
        try{
            Statement s = conn.createStatement();
            int r = s.executeUpdate("UPDATE esperienza SET emailGuida = '"+ciceroneDaAssociare.getEmail()+"' WHERE id="+idEsperienza);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Tag> leggiTagRegistrati() {
        ArrayList<Tag> tagRegistrati = new ArrayList<>();
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM tag");
            while(r.next()){
                tagRegistrati.add(new Tag(r.getString("nome")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return tagRegistrati;
    }

    public static ArrayList<String> selezionaCiceroniDisponibili(String emailAssociazione, java.util.Date dataEsperienza) {
        ArrayList<String> emailCiceroniDisponibili = new ArrayList<>();

        //Converto la data nel formato giusto per MySQL
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDate = formatter.format(dataEsperienza);

        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM utente JOIN disponibilita WHERE email = emailCicerone AND emailAssociazione='"+emailAssociazione+"' AND data='"+mysqlDate+"'");
            while(r.next()){
                emailCiceroniDisponibili.add(r.getString("email"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return emailCiceroniDisponibili;
    }
}
