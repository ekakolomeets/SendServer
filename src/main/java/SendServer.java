import com.sun.net.httpserver.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class SendServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(SendServer::handleRequest);
        server.start();
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        printRequestInfo(exchange);
        String response = "Sent the message: " + requestURI;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private static void printRequestInfo(HttpExchange exchange)  {
        System.out.println("-- headers --");
        Headers requestHeaders =  exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach( System.out::println);

        System.out.println("-- principle --");
        HttpPrincipal principal =  exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod =  exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println( "-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);

        if (requestURI.toString().contains("telegram")) {
            TelegramMessage telegramMessage = new TelegramMessage();
            telegramMessage.sendTelegramMessage(query);
        }
        if (requestURI.toString().contains(("mail")))  {
            MailMessage mailMessage = new MailMessage();
            try {
                mailMessage.sendMailMessage(query);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

}