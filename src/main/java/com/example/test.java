package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Collections;
import java.util.List;

public class test {

    public static void main(String[] args) throws JsonProcessingException {
        try {
            String jsonString = "{\"source\": {\"source\": \"example.pdf\", \"page\": 6}, \"answer\": \": 선정된 핵심 특허(P1~P9)는 모두 생성형 인공지능 분야 또는 해당 기술 분류에서 기술적 영향력이 높은 기업이 출원하였습니다. 청구범위의 독립항 수가 3개 이상인 특허가 7건, 나머지 2건 역시 독립항 수가 2개 이상인 것으로 나타났습니다. 또한, 선정된 모든 특허가 다수의 패밀리 문헌을 보유하고 있으며, 특히 P1, P6, P8의 경우 한국, 미국, 유럽, 일본, 중국, 스페인, 포르투갈, 인도, 러시아와 같은 다양한 국가에서 패밀리 문헌을 확보했습니다. \\n\\n\\n\"}";


            // Parse the JSON string
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();

            // Access fields within the JSON
            JsonObject source = jsonObject.getAsJsonObject("source");
            String fileName = source.get("source").getAsString();
            int page = source.get("page").getAsInt();
            String answer = jsonObject.get("answer").getAsString();

            // Print extracted data
            System.out.println("Source File: " + fileName);
            System.out.println("Page Number: " + page);
            System.out.println("Answer: " + answer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
