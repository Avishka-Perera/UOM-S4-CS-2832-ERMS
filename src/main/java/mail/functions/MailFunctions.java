package mail.functions;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailFunctions {

    final static String username = "mail.ermsadiministration";
    final static String password = "ermsadmin123!";
    public static boolean sendMail(String email, String msg) {
        System.out.println("Sending the message\n"+msg+"\n\nto: "+email);
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("msd.190498n@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject("");
            message.setText(msg);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
