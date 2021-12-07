package web.resttemplate;

import web.resttemplate.model.User;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Communication {


    static final String URL = "http://91.241.64.178:7081/api/users";
    static String resultHeader = "";
    static RestTemplate restTemplate = new RestTemplate();
    static HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {
        String cookie = getAllUsers();
        resultHeader += saveUser(cookie) + "-";
        System.out.println(resultHeader);
        resultHeader += updateUser(cookie) + "-";
        System.out.println(resultHeader);
        resultHeader += deleteUser(cookie);
        System.out.println(resultHeader);
    }

    public static String getAllUsers() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        String cookie = response.getHeaders().getFirst("Set-Cookie");
        return cookie;
    }


    public static String saveUser(String cookie) {
        User user = new User(3L, "James", "Brown", (byte) 25);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();
    }

    public static String updateUser(String cookie) {
        User updUser = new User(3L, "Thomas", "Shelby", (byte) 27);
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<User> entity = new HttpEntity<>(updUser, headers);
        return restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
    }

    public static String deleteUser(String cookie) {
        headers.set(HttpHeaders.COOKIE, cookie);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class).getBody();
    }
}
