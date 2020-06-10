import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class TelegramMessage {
    public void sendTelegramMessage(String message) {
        HttpURLConnection connection;
        int timeout = 5000; 
        try {
            URL url = new URL("https://api.telegram.org/bot1116154429:AAEfHnMDeiZmYDT99qMklM-YyEap4bd_OGg/sendMessage?chat_id=-447226957&text=" + message);

            //настройка подключения через proxy сервер

            Authenticator.setDefault(new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("8WrLok", "gc5dJx".toCharArray());
                }
            });
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("104.227.102.165", 9579));
            connection = (HttpURLConnection) url.openConnection(proxy);

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            StringBuilder sb = new StringBuilder();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "cp1251"));
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                System.out.println(sb.toString());
            } else {
                System.out.println("fail: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }
        } catch (Exception e) {
            System.out.println(("connection init error " + e.getMessage()));
        }
    }
}
