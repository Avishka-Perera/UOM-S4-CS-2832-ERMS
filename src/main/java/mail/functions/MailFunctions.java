package mail.functions;

public class MailFunctions {
    public static boolean sendMail(String email, String message) {
        System.out.println("Sending the message\n"+message+"\n\nto: "+email);
        return true;
    }
}
