package com.tusofia.apigateway.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tusofia.apigateway.config.ShoppingCartServiceCommunicationProperties;
import com.tusofia.apigateway.dto.*;
import com.tusofia.apigateway.service.ShoppingCartServiceConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceConnectorImpl implements ShoppingCartServiceConnector {
    private final ShoppingCartServiceCommunicationProperties shoppingCartServiceCommunicationProperties;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final ProductServiceConnectorImpl productServiceConnector;

    @Override
    public ShoppingCartDTO findCartByUsername(String username) {
        ShoppingCartDTO shoppingCart = new ShoppingCartDTO();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceCommunicationProperties.getUrl() + "/get-cart-by-username")
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

            shoppingCart = objectMapper.readValue(responseBody, ShoppingCartDTO.class);

        } catch (Exception e) {
            log.error("Could not get response from ShoppingCart Service!");
        }

        return shoppingCart;
    }

    @Override
    public List<ShoppingCartProductViewDTO> findAllShoppingCartProducts(String shoppingCartId) {
        List<ShoppingCartProductViewDTO> shoppingCartProducts = new ArrayList<>();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceCommunicationProperties.getUrl() + "/find-all-cart-products")
                    .queryParam("shoppingCartId", "{shoppingCartId}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("shoppingCartId", shoppingCartId);

            String[] responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String[].class,
                    params).getBody();

            Arrays.stream(responseBody).forEach(product -> {
                try {
                    ShoppingCartProductViewDTO shoppingCartProduct = objectMapper.readValue(product, ShoppingCartProductViewDTO.class);
                    shoppingCartProducts.add(shoppingCartProduct);
                } catch (JsonProcessingException e) {
                    log.error("Could not get response from ShoppingCart Service!");
                }
            });
        } catch (Exception e) {
            log.error("Could not get response from ShoppingCart Service!");
        }

        return shoppingCartProducts;
    }

    @Override
    public BigDecimal total(String shoppingCartId) {
        BigDecimal total = new BigDecimal(0);

        try {
            String url = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceCommunicationProperties.getUrl() + "/total")
                    .queryParam("shoppingCartId", "{shoppingCartId}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("shoppingCartId", shoppingCartId);

            BigDecimal responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    BigDecimal.class,
                    params).getBody();

            total = responseBody;

        } catch (Exception e) {
            log.error("Could not get response from ShoppingCart Service!");
        }

        return total;
    }

    @Override
    public void remove(String shoppingCartProductId) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceCommunicationProperties.getUrl() + "/remove")
                    .queryParam("id", "{id}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("id", shoppingCartProductId);

            restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params).getBody();

        } catch (Exception e) {
            log.error("Could not get response from ShoppingCart Service!");
        }
    }

    @Override
    public void removeAll(String shoppingCartId) {
        UpdateProductQuantityDTO updateProductQuantity = new UpdateProductQuantityDTO();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceCommunicationProperties.getUrl() + "/removeAll")
                    .queryParam("shoppingCartId", "{shoppingCartId}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("shoppingCartId", shoppingCartId);

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class,
                    params).getBody();

            updateProductQuantity = objectMapper.readValue(responseBody, UpdateProductQuantityDTO.class);

            productServiceConnector.updateProductQuantity(updateProductQuantity);

        } catch (Exception e) {
            log.error("Could not get response from ShoppingCart Service!");
        }
    }

    @Override
    public void addProductToCart(ShoppingCartProductDTO shoppingCartProductDTO) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceCommunicationProperties.getUrl() + "/add-product-to-cart")
                    .queryParam("shoppingCartId", "{shoppingCartId}")
                    .queryParam("productId", "{productId}")
                    .queryParam("name", "{name}")
                    .queryParam("manufacturer", "{manufacturer}")
                    .queryParam("quantity", "{quantity}")
                    .queryParam("productQuantity", "{productQuantity}")
                    .queryParam("price", "{price}")
                    .queryParam("category", "{category}")
                    .queryParam("description", "{description}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("shoppingCartId", shoppingCartProductDTO.getShoppingCartId());
            params.put("productId", shoppingCartProductDTO.getProductId());
            params.put("name", shoppingCartProductDTO.getName());
            params.put("manufacturer", shoppingCartProductDTO.getManufacturer());
            params.put("quantity", String.valueOf(shoppingCartProductDTO.getQuantity()));
            params.put("productQuantity", String.valueOf(shoppingCartProductDTO.getProductQuantity()));
            params.put("price", shoppingCartProductDTO.getPrice().toString());
            params.put("category", shoppingCartProductDTO.getCategory());
            params.put("description", shoppingCartProductDTO.getDescription());

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params).getBody();

        } catch (Exception e) {
            log.error("Could not get response from ShoppingCart Service!");
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
