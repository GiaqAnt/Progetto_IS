package utilities;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validation {

    // Validazione Nome
    public static void validateNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Il nome non può essere null.");
        }
        if (nome.length() > 45) {
            throw new IllegalArgumentException("Il nome non può superare i 45 caratteri.");
        }
        if (!nome.matches("^[a-zA-ZàèéìòùÀÈÉÌÒÙ\\s]+$")) {
            throw new IllegalArgumentException("Il nome contiene caratteri non validi.");
        }
    }

    // Validazione Cognome (solo lettere, max 75 caratteri)
    public static void validateCognome(String cognome) {
        if (cognome == null) {
            throw new IllegalArgumentException("Il cognome non può essere null.");
        }
        if (cognome.length() > 75) {
            throw new IllegalArgumentException("Il cognome non può superare i 75 caratteri.");
        }
        if (!cognome.matches("^[a-zA-ZàèéìòùÀÈÉÌÒÙ\\s]+$")) {
            throw new IllegalArgumentException("Il cognome contiene caratteri non validi.");
        }
    }

    // Validazione Email (formato email, max 100 caratteri)
    public static void validateEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("L'email non può essere null.");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("L'email non può superare i 100 caratteri.");
        }
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new IllegalArgumentException("Il formato dell'email non è valido.");
        }
    }
    
    public static void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La password non può essere nulla o vuota.");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("La password deve essere lunga almeno 8 caratteri.");
        }

        if (!Pattern.compile(".*\\d.*").matcher(password).matches()) {
            throw new IllegalArgumentException("La password deve contenere almeno una cifra.");
        }

        if (!Pattern.compile(".*[A-Z].*").matcher(password).matches()) {
            throw new IllegalArgumentException("La password deve contenere almeno una lettera maiuscola.");
        }

        if (!Pattern.compile(".*[a-z].*").matcher(password).matches()) {
            throw new IllegalArgumentException("La password deve contenere almeno una lettera minuscola.");
        }

        if (!Pattern.compile(".*[@#$%^&+=].*").matcher(password).matches()) {
            throw new IllegalArgumentException("La password deve contenere almeno un carattere speciale (@, #, $, %, ^, &, +, =).");
        }
    }

    // Validazione Nome Classe
    public static void validateClassName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Il nome della classe non può essere null.");
        }
        if (name.length() > 45) {
            throw new IllegalArgumentException("Il nome della classe non può superare i 45 caratteri.");
        }
        if (!name.matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Il nome della classe può contenere solo lettere e spazi.");
        }
    }

    // Validazione Titolo Task
    public static void validateTaskTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Il titolo del task non può essere null.");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("Il titolo del task non può superare i 50 caratteri.");
        }
        if (!title.matches("[a-zA-Z\\s]+")) {
            throw new IllegalArgumentException("Il titolo del task può contenere solo lettere e spazi.");
        }
    }

    // Validazione Descrizione Task
    public static void validateTaskDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("La descrizione del task non può essere null.");
        }
        if (description.length() > 256) {
            throw new IllegalArgumentException("La descrizione del task non può superare i 256 caratteri.");
        }
    }

    // Validazione Data di Scadenza
    public static void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("La data di scadenza non può essere null.");
        }
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data di scadenza non può essere nel passato.");
        }
    }

    // Validazione Punteggio Massimo
    public static void validateMaxScore(Object scoreObj) {
        if (!(scoreObj instanceof Integer score)) {
            throw new IllegalArgumentException("Il punteggio massimo deve essere un numero intero.");
        }
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Il punteggio massimo deve essere compreso tra 0 e 100.");
        }
    }
}
