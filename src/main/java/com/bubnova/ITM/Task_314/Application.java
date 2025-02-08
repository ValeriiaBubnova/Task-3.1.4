package com.bubnova.ITM.Task_314;

import com.bubnova.ITM.Task_314.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
        String URL = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
		String sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
		if (sessionId == null) {
			throw new RuntimeException("Session not found");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cookie", sessionId);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		//создание юзера
		User user = new User(3L,"James", "Brown", (byte) 22);
		ResponseEntity<String> postResponse = restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(user,headers),String.class);
//		System.out.println("Result: "+ postResponse.getBody());

		//обновление
		user.setName("Thomas");
		user.setLastName("Shelby");
		ResponseEntity<String> putResponse = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(user,headers),String.class);
//		System.out.println("Result: "+ putResponse.getBody());

		//удаление
		String URL2= "http://94.198.50.185:7081/api/users/3";
		ResponseEntity<String> deleteResponse = restTemplate.exchange(URL2, HttpMethod.DELETE, new HttpEntity<>(null,headers),String.class);
//		System.out.println("Result: "+ deleteResponse.getBody());
		String sumResponse = postResponse.getBody()+putResponse.getBody()+deleteResponse.getBody();
		System.out.println(sumResponse.length());





	}

}
