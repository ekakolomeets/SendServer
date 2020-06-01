import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailMessage {
    public void sendMailMessage(String message) throws MessagingException {
        Properties properties = new Properties();
        //Хост или IP-адрес почтового сервера
        properties.put("mail.smtp.host", "smtp.yandex.ru");
        //Требуется ли аутентификация для отправки сообщения
        properties.put("mail.smtp.auth", "true");
        //Порт для установки соединения
        properties.put("mail.smtp.socketFactory.port", "465");
        //Фабрика сокетов, так как при отправке сообщения Yandex требует SSL-соединения
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //Создаем соединение для отправки почтового сообщения
        Session session = Session.getDefaultInstance(properties,
                //Аутентификатор - объект, который передает логин и пароль
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("SendServerMail@yandex.ru", "12365476s");
                    }
                });

        //Создаем новое почтовое сообщение
        Message newMessage = new MimeMessage(session);
        //От кого
        newMessage.setFrom(new InternetAddress("SendServerMail@yandex.ru"));
        //Кому
        newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("SendServerMail@yandex.ru"));
        //Тема письма
        newMessage.setSubject("Warning!");
        //Текст письма
        newMessage.setText(String.valueOf(message));
        //Поехали!!!
        Transport.send(newMessage);
    }
}