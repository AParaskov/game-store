package com.tusofia.apigateway.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tusofia.apigateway.config.ProductServiceCommunicationProperties;
import com.tusofia.apigateway.dto.*;
import com.tusofia.apigateway.service.ProductServiceConnector;
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
public class ProductServiceConnectorImpl implements ProductServiceConnector {
    private final ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    private final ProductServiceCommunicationProperties productServiceCommunicationProperties;

    @Override
    public ProductDTO getProductById(String id) {
        ProductDTO product = new ProductDTO();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/get-product-by-id")
                    .queryParam("id", "{id}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("id", id);

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class,
                    params).getBody();

            product = objectMapper.readValue(responseBody, ProductDTO.class);

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }

        return product;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = new ArrayList<>();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/get-all-products").toUriString();

            Object[] responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    Object[].class).getBody();

            if (responseBody != null){
                for (Object product:responseBody) {
                    ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                    products.add(productDTO);
                }
            } else {
                return products;
            }


        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }

        return products;
    }

    @Override
    public void deleteProduct(String id) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/delete-product-by-id")
                    .queryParam("id", "{id}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("id", id);

            restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params);

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }
    }

    @Override
    public void addProduct(ProductAddRequest productAddRequest, String category) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/add-product")
                    .queryParam("category", "{category}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("category", category);

            HttpEntity<ProductAddRequest> requestEntity =
                    new HttpEntity<>(productAddRequest, setHttpHeaders());

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Void.class,
                    params);

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }
    }

    @Override
    public void updateProduct(ProductUpdateRequest productUpdateRequest) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/update-product")
                    .queryParam("name", "{name}")
                    .queryParam("quantity", "{quantity}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("name", productUpdateRequest.getName());
            params.put("quantity", String.valueOf(productUpdateRequest.getQuantity()));

            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params);

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }
    }

    @Override
    public List<String> getAllCategoryNames() {
        List<String> roleNames = new ArrayList<>();

        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/get-all-category-names").toUriString();

            String responseBody = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(setHttpHeaders()),
                    String.class).getBody();

            roleNames = Arrays.stream(responseBody.split(",")).toList();

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }

        return roleNames;
    }

    @Override
    public void updateProductQuantity(UpdateProductQuantityDTO updateProductQuantityDTO) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/update-product-quantity")
                    .queryParam("productIds", "{productIds}")
                    .queryParam("productQuantities", "{productQuantities}")
                    .encode()
                    .toUriString();

            Map<String, List<String>> params = new HashMap<>();
            params.put("productIds", updateProductQuantityDTO.getProductIds());
            params.put("productQuantities", updateProductQuantityDTO.getProductQuantities());

            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params);

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
        }
    }

    @Override
    public void addReview(ReviewAddRequest reviewAddRequest, String username) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(productServiceCommunicationProperties.getUrl() + "/add-review")
                    .queryParam("username", "{username}")
                    .queryParam("text", "{text}")
                    .queryParam("productId", "{productId}")
                    .encode()
                    .toUriString();

            Map<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("text", reviewAddRequest.getText());
            params.put("productId", reviewAddRequest.getProductId());

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(setHttpHeaders()),
                    Void.class,
                    params);

        } catch (Exception e) {
            log.error("Could not get response from Product Service!");
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
