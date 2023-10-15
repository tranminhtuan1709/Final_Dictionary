package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class GoogleAPI {

/*    public static void main(String[] args) throws IOException {
//        String text = "Hello world!";
//        //Translated text: Xin chao the gioi!
//        System.out.println("Translated text: " + translateEnToVi(text));  }
*/
    private static String translate(String langFrom, String langTo, String text) throws IOException, URISyntaxException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbw1qSfs1Hvfnoi3FzGuoDWijwQW69eGcMM_iGDF7p5vu1oN_CaFqIDFmCGzBuuGCk_N/exec" +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URI uri = new URI(urlStr);
        URL url = uri.toURL();
//        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static String translateEnToVi(String text) throws IOException, URISyntaxException {
        return translate("en", "vi", text);
    }

    public static String translateViToEn(String text) throws IOException, URISyntaxException {
        return translate("vi", "en", text);
    }
}
