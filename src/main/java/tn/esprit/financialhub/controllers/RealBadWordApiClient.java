package tn.esprit.financialhub.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RealBadWordApiClient {

    private final String apiKey;

    public RealBadWordApiClient(String apiKey, String endpoint, String key) {
        this.apiKey = apiKey;
    }

    public boolean containsBadWord(String text) throws IOException {
        String encodedText = URLEncoder.encode(text, "UTF-8");
        String urlString = "https://api.badwordservice.com/check?text=" + encodedText + "&api_key=" + apiKey;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parse the JSON response
                return response.toString().contains("\"containsBadWord\":true");
            } else {
                System.err.println("Error response code: " + responseCode);
                return false;
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
