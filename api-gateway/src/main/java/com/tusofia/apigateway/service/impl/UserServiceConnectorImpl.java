package com.tusofia.apigateway.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tusofia.apigateway.config.UserServiceCommunicationProperties;
import com.tusofia.apigateway.dto.RoleAddRequest;
import com.tusofia.apigateway.dto.UserDTO;
import com.tusofia.apigateway.dto.UserRegisterRequest;
import com.tusofia.apigateway.service.UserServiceConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceConnectorImpl implements UserServiceConnector {
    private final ModelMapper modelMapper;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final UserServiceCommunicationProperties userServiceCommunicationProperties;

    @Override
    public UserDTO getUserByUsername(String username) {
        UserDTO user = new UserDTO();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(userServiceCommunicationProperties.getUrl() + "/get-user-by-username")
                    .queryParam("username", "{username}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("username", username);

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class,
                    params).getBody();

            user = objectMapper.readValue(responseBody, UserDTO.class);

        } catch (Exception e) {
            log.error("Could not get response from User Service!");
        }

        return user;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserDTO user = new UserDTO();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(userServiceCommunicationProperties.getUrl() + "/get-user-by-email")
                    .queryParam("email", "{email}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("email", email);

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class,
                    params).getBody();


            user = modelMapper.map(responseBody, UserDTO.class);

        } catch (Exception e) {
            log.error("Could not get response from User Service!");
        }

        return user;
    }

    @Override
    public List<String> getAllUsersUsernames() {
        List<String> usernames = new ArrayList<>();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(userServiceCommunicationProperties.getUrl() + "/get-all-users-usernames").toUriString();

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class).getBody();

            usernames = Arrays.stream(responseBody.split(",")).toList();

        } catch (Exception e) {
            log.error("Could not get response from User Service!");
        }

        return usernames;
    }

    @Override
    public List<String> getAllRolesNames() {
        List<String> roleNames = new ArrayList<>();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(userServiceCommunicationProperties.getUrl() + "/get-all-roles-names").toUriString();

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class).getBody();

            roleNames = Arrays.stream(responseBody.split(",")).toList();

        } catch (Exception e) {
            log.error("Could not get response from User Service!");
        }

        return roleNames;
    }

    @Override
    public void registerUser(UserRegisterRequest userRegisterRequest) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(userServiceCommunicationProperties.getUrl() + "/register").toUriString();

            HttpEntity<UserRegisterRequest> requestEntity =
                    new HttpEntity<>(userRegisterRequest, setHttpHeaders());

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class);

        } catch (Exception e) {
            log.error("Could not get response from User Service!");
        }
    }

    @Override
    public void changeRole(RoleAddRequest roleAddRequest) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(userServiceCommunicationProperties.getUrl() + "/change-role")
                    .queryParam("username", "{username}")
                    .queryParam("role", "{role}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("username", roleAddRequest.getUsername());
            params.put("role", roleAddRequest.getRole());

            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params);

        } catch (Exception e) {
            log.error("Could not get response from User Service!");
        }
    }

    private HttpHeaders setHttpHeaders() {
        return new HttpHeaders() {{
            setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            setContentType(MediaType.APPLICATION_JSON);
        }
        };
    }
}
