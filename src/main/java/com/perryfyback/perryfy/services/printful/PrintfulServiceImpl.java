package com.perryfyback.perryfy.services.printful;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.perryfyback.perryfy.models.Printful.CreateMockupResponse;
import com.perryfyback.perryfy.models.Printful.CreateTaskRequest;
import com.perryfyback.perryfy.models.Printful.GetMockupResponse;
import com.perryfyback.perryfy.models.Printful.MockupFile;
import com.perryfyback.perryfy.models.Printful.MockupGenerationRequest;
import com.perryfyback.perryfy.models.Printful.Position;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrintfulServiceImpl implements PrintfulService {

    @Value("${printful.api.key}")
    private String API_KEY;
    private final String BASE_URL = "https://api.printful.com";

    private final RestTemplate restTemplate;

    private static final int AREA_WIDTH = 4200;
    private static final int AREA_HEIGHT = 5400;

    @Override
    public CreateMockupResponse createMockup(MockupGenerationRequest mockupRequest) {
        String url = BASE_URL + "/mockup-generator/create-task/" + mockupRequest.getVariants_id().getFirst();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        List<MockupFile> files = mockupRequest.getFiles().stream()
                .map(fileRequest -> {
                    Position position = new Position();
                    position.setWidth(fileRequest.getPosition().getWidth());
                    position.setHeight(fileRequest.getPosition().getHeight());
                    position.setArea_width(AREA_WIDTH);
                    position.setArea_height(AREA_HEIGHT);
                    position.setTop((AREA_HEIGHT - fileRequest.getPosition().getHeight()) / 2);
                    position.setLeft((AREA_WIDTH - fileRequest.getPosition().getWidth()) / 2);

                    MockupFile mockupFile = new MockupFile();
                    mockupFile.setPlacement(fileRequest.getPlacement());
                    mockupFile.setImage_url(fileRequest.getImage_url());
                    mockupFile.setPosition(position);
                    return mockupFile;
                }).collect(Collectors.toList());

        CreateTaskRequest body = new CreateTaskRequest();
        body.setVariant_ids(Collections.singletonList(mockupRequest.getVariants_id().getLast()));
        body.setFormat("png");
        body.setFiles(files);

        HttpEntity<CreateTaskRequest> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                Map.class);

        Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");
        String taskKey = (String) result.get("task_key");

        return new CreateMockupResponse(taskKey);
    }

    @Override
    public GetMockupResponse getMockups(String taskKey) {
        String url = BASE_URL + "/mockup-generator/task?task_key=" + taskKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_KEY);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            request,
            Map.class);
        
        Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");
        
        if ("pending".equalsIgnoreCase((String) result.get("status"))) {
            // Can return an empty list or throw a custom exception
            return new GetMockupResponse(new ArrayList<>());
        }

        List<Map<String, Object>> mockups = (List<Map<String, Object>>) result.get("mockups");
        List<String> mockupUrls = new ArrayList<>();
        
        for (Map<String, Object> mockup : mockups) {
            mockupUrls.add((String) mockup.get("mockup_url"));
            if (mockup.containsKey("extra")) {
                List<Map<String, Object>> extraMockups = (List<Map<String, Object>>) mockup.get("extra");
                for (Map<String, Object> extra : extraMockups) {
                    mockupUrls.add((String) extra.get("url"));
                }
            }
        }

        return new GetMockupResponse(mockupUrls);
    }
}
