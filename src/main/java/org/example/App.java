package org.example;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    private static final String URL_BASE = "http://94.198.50.185:7081/api/users";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        String sessionID = getSessionID();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionID);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> postRequest = postUser(headers);
        System.out.print(postRequest.getBody());

        ResponseEntity<String> putUserResponse = putUser(headers);
        System.out.print(putUserResponse.getBody());

        ResponseEntity<String> deleteUserResponse = deleteUser(headers);
        System.out.print(deleteUserResponse.getBody());


    }

    private static String getSessionID() {
        ResponseEntity<String> response = App.restTemplate.getForEntity(App.URL_BASE, String.class);
        return response.getHeaders().get("Set-Cookie").get(0);
    }

    private static ResponseEntity<String> postUser(HttpHeaders headers) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", 3);
        map.put("name", "James");
        map.put("lastName", "Brown");
        map.put("age", 21);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        return App.restTemplate.postForEntity(App.URL_BASE, entity, String.class);
    }

    private static ResponseEntity<String> putUser(HttpHeaders headers) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("id", 3);
        map.put("name", "Tomas");
        map.put("lastName", "Shelby");
        map.put("age", 21);

        HttpEntity<Map<String, Object>> postEntity = new HttpEntity<>(map, headers);

        return App.restTemplate.exchange(
                URL_BASE,
                HttpMethod.PUT,
                postEntity,
                String.class
        );
    }

    private static ResponseEntity<String> deleteUser(HttpHeaders headers) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        return App.restTemplate.exchange(
                App.URL_BASE + "/3",
                HttpMethod.DELETE,
                entity,
                String.class
        );
    }
}


//public class App {
//    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
//    private static RestTemplate restTemplate = new RestTemplate();
//    private static String sessionId;
//
//    public static void main(String[] args) {
//        try {
//            // Получить список всех пользователей и сохранить sessionId
//            getSessionId();
//
//            // Добавить пользователя
//            String part1 = addUser();
//
//            // Изменить пользователя
//            String part2 = updateUser();
//
//            // Удалить пользователя
//            String part3 = deleteUser();
//
//            // Получить итоговый код
//            String finalCode = part1 + part2 + part3;
//            System.out.println("Final Code: " + finalCode);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void getSessionId() {
//        ResponseEntity<String> response = restTemplate.exchange(
//                BASE_URL,
//                HttpMethod.GET,
//                null,
//                String.class
//        );
//        // Получение sessionId из заголовков ответа
//        sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
//        System.out.println("Session ID: " + sessionId);
//    }
//
//    private static String addUser() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.COOKIE, sessionId);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> user = new HashMap<>();
//        user.put("id", 3);
//        user.put("name", "James");
//        user.put("lastName", "Brown");
//        user.put("age", 21);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(user, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(BASE_URL, requestEntity, String.class);
//
//        // Проверка успешности добавления и получение первой части кода
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return extractCodeFromResponse(response.getBody());
//        }
//        throw new RuntimeException("Failed to add user.");
//    }
//
//    private static String updateUser() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.COOKIE, sessionId);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> user = new HashMap<>();
//        user.put("id", 3);
//        user.put("name", "Thomas");
//        user.put("lastName", "Shelby");
//        user.put("age", 21);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(user, headers);
//        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestEntity, String.class);
//
//        // Проверка успешности обновления и получение второй части кода
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return extractCodeFromResponse(response.getBody());
//        }
//        throw new RuntimeException("Failed to update user.");
//    }
//
//    private static String deleteUser() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.COOKIE, sessionId);
//
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/3", HttpMethod.DELETE, requestEntity, String.class);
//
//        // Проверка успешности удаления и получение третьей части кода
//        if (response.getStatusCode() == HttpStatus.OK) {
//            return extractCodeFromResponse(response.getBody());
//        }
//        throw new RuntimeException("Failed to delete user.");
//    }
//
//    private static String extractCodeFromResponse(String responseBody) {
//        // Реализуйте логику извлечения кода из тела ответа
//        // Это зависит от того, как API возвращает код. Замените на соответствующую логику.
//        return responseBody; // Предполагаем, что весь ответ — это код. Подкорректируйте при необходимости.
//    }
//}