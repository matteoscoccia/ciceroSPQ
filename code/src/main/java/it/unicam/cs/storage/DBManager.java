package it.unicam.cs.storage;

import it.unicam.cs.esperienza.*;
import it.unicam.cs.pagamento.Pagamento;
import it.unicam.cs.utente.Associazione;
import it.unicam.cs.utente.Cicerone;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
        s.executeUpdate(" UPDATE utente SET emailAssociazione = '"+associazione.getEmail()+"' WHERE email = '"+email+"' ");
    }

    public static boolean esistenzaCicerone(String emailCicerone) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT * FROM utente WHERE email = '"+emailCicerone+"'");
        return r.next();
    }

    public static boolean controlloAssociazione(String email) throws SQLException{
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT emailAssociazione FROM utente WHERE email = '"+email+"' ");
        r.next();
        return r.getString("emailAssociazione") != null;
    }

    public static boolean controllaEsistenza(String emailDaEliminare) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT * FROM utente WHERE email = '"+emailDaEliminare+"'");
        return r.next();
    }

    public static void eliminaUtente(String email, String motivazione) throws SQLException {
        Statement s = conn.createStatement();
        s.executeUpdate("DELETE FROM utente WHERE email ='"+email+"'");
        // invio String motivazione tramite Server email
    }
    public static void eliminareEsperienza(Esperienza esperienzaDaEliminare){
        try {
            Statement s = conn.createStatement();
            s.executeUpdate("DELETE FROM esperienza_tag WHERE Esperienzaid="+esperienzaDaEliminare.getId());
            s.executeUpdate("DELETE FROM pagamento WHERE Esperienzaid = "+esperienzaDaEliminare.getId());
            s.executeUpdate("DELETE FROM tappa WHERE Esperienzaid="+esperienzaDaEliminare.getId());
            s.executeUpdate("DELETE FROM esperienza WHERE id="+esperienzaDaEliminare.getId());
            System.out.println("Esperienza eliminata");
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
                listaCiceroni.add(new Cicerone(r.getString("nome"),r.getString("cognome"),r.getString("email"),r.getString("password"),r.getString("emailAssociazione")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCiceroni;
    }

    public static void eliminareCiceroneAssociazione (String emailCicerone){
        try {
            Statement s = conn.createStatement();
            s.executeUpdate("UPDATE utente SET emailAssociazione = null WHERE email = '"+emailCicerone+"'" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Date visualizzaDisponibilitaCicerone ( String emailCicerone){
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT data FROM disponibilita WHERE emailCicerone = '"+emailCicerone+"'" );
            while(r.next()){
                System.out.println(r.getString("data"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }

    public static void modificaDisponibilita(String emailCicerone, java.util.Date nuovaData){
        //E POI PER ESEGUIRE UN UPDATE O INSERT VA USATO executeUpdate
        try {
            Statement s = conn.createStatement();
            //Converto la data nel formato giusto per MySQL
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String mysqlDate = formatter.format(nuovaData);
            s.executeUpdate("INSERT INTO disponibilita VALUES ('"+emailCicerone+"','"+mysqlDate+"')" );
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
        ArrayList<Toponimo> listaToponimo = new ArrayList<>();
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
                java.sql.Date dataSQL = r.getDate("data");
                java.util.Date dataJava = new java.util.Date(dataSQL.getTime());
                listaDate.add(dataJava);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDate;
    }

    public static ArrayList<Esperienza> ricercaConFiltri (Tag tag , Toponimo toponimo , java.util.Date data, String parolaChiave){
        ArrayList<Esperienza> risultatiRicerca = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creo l'oggetto
        try {
            //Converto la data nel formato giusto per MySQL
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            String mysqlDate = formatter.format(data);

            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM esperienza JOIN esperienza_tag  WHERE id=Esperienzaid AND titolo LIKE '%"+parolaChiave+"%' AND tag = '"+tag.getName()+"' AND toponimo = '"+toponimo.getNome()+"' AND data = '"+mysqlDate+"' ");

            if (!r.isBeforeFirst() ) {
                System.out.println("Nessuna esperienza corrispondente");
            }

            while (r.next()) {
                int id = r.getInt("id");
                String titolo = r.getString("titolo");
                String descrizione = r.getString("descrizione");
                float prezzo = r.getFloat("prezzo");
                String emailGuida = r.getString("emailGuida");
                Date dataEsperienza = r.getDate("data");
                int postiDisponibili = r.getInt("postiDisponibili");
                int postiMassimi = r.getInt("postiMassimi");
                int postiMinimi = r.getInt("postiMinimi");

                Esperienza e = new Esperienza(id, titolo, descrizione, dataEsperienza);
                e.setPostiDisponibili(postiDisponibili);
                e.setPosti(postiMinimi, postiMassimi);
                e.setPrezzo(prezzo);
                e.guida = new Cicerone("","",emailGuida,"","");
                risultatiRicerca.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return risultatiRicerca;
    }

    public static ArrayList<Esperienza> ricercaParolaChiave (String parolaChiave){
        ArrayList<Esperienza> risultatiRicerca = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM esperienza WHERE titolo LIKE '%"+parolaChiave+"%'  ");

            if (!r.isBeforeFirst() ) {
                System.out.println("Nessuna esperienza corrispondente");
            }

            while (r.next()) {
                int id = r.getInt("id");
                String titolo = r.getString("titolo");
                String descrizione = r.getString("descrizione");
                float prezzo = r.getFloat("prezzo");
                String emailGuida = r.getString("emailGuida");
                Date dataEsperienza = r.getDate("data");
                int postiDisponibili = r.getInt("postiDisponibili");
                int postiMassimi = r.getInt("postiMassimi");
                int postiMinimi = r.getInt("postiMinimi");

                Esperienza e = new Esperienza(id, titolo, descrizione, dataEsperienza);
                e.setPostiDisponibili(postiDisponibili);
                e.setPosti(postiMinimi, postiMassimi);
                e.setPrezzo(prezzo);
                e.guida = new Cicerone("","",emailGuida,"","");

                risultatiRicerca.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return risultatiRicerca;
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

    public static void registraPagamento(Pagamento pagamento) {
        try{
            Statement s = conn.createStatement();
            int id = ThreadLocalRandom.current().nextInt(10000, 500000);//Genera numero casuale
            s.executeUpdate("INSERT INTO pagamento VALUES("+id+","+pagamento.getImporto()+",'"+pagamento.getTurista().getEmail()+"'," +pagamento.getEsperienza().getId()+")");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void aggiornaPostiEsperienza(Esperienza esperienza) {
        try{
            Statement s = conn.createStatement();
            s.executeUpdate("UPDATE esperienza SET postiDisponibili="+esperienza.getPostiDisponibili()+" WHERE id="+esperienza.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void registraPartecipanti(Esperienza esperienza, ArrayList<Partecipante> partecipanti) {
        try{
            Statement s = conn.createStatement();
            for (Partecipante p:
                 partecipanti) {
                int id = ThreadLocalRandom.current().nextInt(10000, 500000);//Genera numero casuale
                s.executeUpdate("INSERT INTO partecipante VALUES("+id+",'"+p.getEmail()+"',"+esperienza.getId()+")");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void registraToponimo(String toponimoFiglio, String toponimoGenitore) throws SQLException {
        Statement s = conn.createStatement();
        s.executeUpdate("INSERT INTO toponimo VALUES("+toponimoFiglio+",'"+toponimoGenitore+"')");
    }

    public static boolean controllaEsistenzaToponimo(String nomeToponimo) throws SQLException {
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("SELECT * FROM toponimi WHERE nome = '"+nomeToponimo+"'");
        return r.next();
    }

    public static void eliminaToponimo(String toponimoDaEliminare) throws SQLException{
        Statement s = conn.createStatement();
        s.executeUpdate("DELETE FROM toponimo WHERE nome ='"+toponimoDaEliminare+"'");
    }

    public static boolean controllaEsistenzaFigli(String toponimoDaControllare) throws SQLException {
        // todo: finire implementazione query che controlla se ci sono colonne "Genitore" che contengono la variabile
        //  toponimoDaControllare nella tabella dei toponimi. Se c'è almeno una riga allora ritorna true, false altrimenti
        Statement s = conn.createStatement();
        ResultSet r = s.executeQuery("");
        return false;
    }

    public static void visualizzaDettagliEsperienza(Esperienza esperienzaScelta) {
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM esperienza WHERE id="+esperienzaScelta.getId());
            r.next();
            System.out.println("-----------------------------------------------");
            System.out.println("TITOLO: "+r.getString("titolo"));
            System.out.println("DESCRIZIONE: "+r.getString("descrizione"));
            System.out.println("DATA: "+r.getDate("data"));
            System.out.println("POSTI DISPONIBILI: "+ r.getString("postiDisponibili"));
            System.out.println("POSTI MASSIMI: "+r.getString("postiMassimi"));
            System.out.println("POSTI MINIMI: "+r.getString("postiMinimi"));
            System.out.println("PREZZO: " + r.getFloat("prezzo"));
            System.out.println("EMAIL GUIDA: " + r.getString("emailGuida"));
            System.out.println("TOPONIMO: " + r.getString("toponimo"));

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void visualizzaTagEsperienza(Esperienza esperienzaScelta) {
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM esperienza_tag WHERE Esperienzaid="+esperienzaScelta.getId());
            System.out.print("\nTAG: ");
            while(r.next()){
                System.out.print(r.getString("Tag")+" ");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void visualizzaElencoTappe(Esperienza esperienzaScelta) {
        System.out.println("\nELENCO TAPPE:");
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM tappa WHERE Esperienzaid="+esperienzaScelta.getId());
            while(r.next()){
                System.out.println(r.getString("nome")+ " - " + r.getString("indirizzo"));
                System.out.println(r.getString("descrizione"));
                System.out.println("----------------------------------------------------------------");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean controlloTag(Tag tag){
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT nome FROM tag WHERE nome = '"+tag.getName()+"' ");
            return r.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void proponiNuovoTag(Tag tag){
        try {
            Statement s = conn.createStatement();
            s.executeUpdate("INSERT INTO tagDaApprovare VALUES('"+ tag.getName()+"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Tag> controlloTagApprovare(){
        ArrayList<Tag> tag = new ArrayList<>();
        try{
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM tagDaApprovare");
            while(r.next()){
                tag.add(new Tag(r.getString("nome")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tag;
    }
    public static void aggiungiTag(Tag tag){
        try {
            Statement s = conn.createStatement();
            s.executeUpdate("INSERT INTO tag VALUES('"+ tag.getName()+"')");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
