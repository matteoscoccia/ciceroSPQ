package it.unicam.cs;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestoreAccount {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public void iscrizione(){
        //TODO implementare il corpo del metodo??
    }

    public static boolean controllaFormato(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

}
