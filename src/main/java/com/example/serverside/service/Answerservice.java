package com.example.serverside.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
public class Answerservice {

    public String getAnswer(String question) {
        String code = "myCode";
        String company_name = "google";//모델 및 프롬프트 선택할수 있도록 유지보수 가능
        String model_name = "gemma-2-9b-it";//유지보수 가능
        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8000")
                        .build();
        // api 요청
        String response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/answer")
                                        .queryParam("company_name",company_name)
                                        .queryParam("model_name",model_name)
                                        .queryParam("question",question)
                                        .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        // 결과 확인
        log.info(response);
        return response;
    }

    public JsonObject jsonAnswer(String question) throws JsonProcessingException {
        String answer = getAnswer(question);
        JsonParser parser = new JsonParser();
//        JsonObject jsonObject = parser.parse(answer).getAsJsonObject();
        // 이중 인코딩 해결
        String firstParsed = parser.parse(answer).getAsString(); // 첫 번째 파싱
        JsonObject finalJsonObject = parser.parse(firstParsed).getAsJsonObject(); // 두 번째 파싱

//        // Access fields within the JSON
//        JsonObject source = jsonObject.getAsJsonObject("source_Path");
//        String fileName = source.get("source").getAsString();
//        int page = source.get("page").getAsInt();
//        String reanswer = jsonObject.get("answer").getAsString();
//
//        // Print extracted data
//        System.out.println("Source File: " + fileName);
//        System.out.println("Page Number: " + page);
//        System.out.println("Answer: " + reanswer);
        return finalJsonObject;
    }

}
