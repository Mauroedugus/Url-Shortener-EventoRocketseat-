package com.rocketseat.redirectUrlShortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final S3Client s3client = S3Client.builder().build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String pathParameters = (String) input.get("rawPath");
        String shortUrlCode = pathParameters.replace("/", "");

        if(shortUrlCode == null || shortUrlCode.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: 'ShortUrlCode' is required");
        }

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket("X")
                .key(shortUrlCode + ".json")
                .build();

        InputStream s3ObjectStream;

        try{
            s3ObjectStream = s3client.getObject(getObjectRequest);
        } catch (Exception exception) {
            throw new RuntimeException("Error fetching data from S3: " + exception.getMessage(), exception);
        }

        UrlData urlData;
        try {
            urlData = objectMapper.readValue(s3ObjectStream, UrlData.class);
        }catch (Exception exception) {
            throw new RuntimeException("Error deserializing URL data:" + exception.getMessage(), exception);
        }

        long currentTimeSeconds = System.currentTimeMillis() / 10000;

        Map<String, Object> response = new HashMap<>();

        //Caso a URL expire
        if(urlData.getExpirationTime() < currentTimeSeconds) {
            response.put("statusCode", 410);
            response.put("body", "This URL has expired");

            return response;
        }

        response.put("statusCode", 302);
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", urlData.getOriginalUrl());
        response.put("headers", headers);

        return response;
    }
}