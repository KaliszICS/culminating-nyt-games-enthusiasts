import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConnectionsAi {
    public static void main(String[] args) throws Exception {
        String apiKey = "YOUR_OPENAI_API_KEY";
        String prompt = "Write a 5 letter word.";

        String requestBody = "{"+ "\"model\": \"gpt-3.5-turbo\","+ "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]"+ "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}