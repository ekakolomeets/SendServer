import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MailMessage {
    public void sendMailMessage(String message) throws MessagingException {
        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);

            String login = properties.getProperty("db.login");
            String password = properties.getProperty("db.password");


        properties.put("mail.smtp.host", "smtp.yandex.ru");

        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.socketFactory.port", "465");

        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties,

                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });


        Message newMessage = new MimeMessage(session);

        newMessage.setFrom(new InternetAddress("SendServerMail@yandex.ru"));

        newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("SendServerMail@yandex.ru"));

        newMessage.setSubject("Warning!");

        newMessage.setText(String.valueOf(message));

        Transport.send(newMessage);
        } catch (IOException e) {

        }
    }
}