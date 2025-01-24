package com.example.serverside.Service;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class aswerSercive {
    public HashMap getAnswer() {
        String code = "myCode";

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8000")
                        .build();
        // api 요청
        HashMap response =
                webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/answer")
                                        .queryParam("company_name","google")
                                        .queryParam("model_name","gemma-2-9b-it")
                                        .queryParam("question","선정된  핵심  특허와  정량적  정보의  상관성에 대해서 설명해줘")
                                        .build())
                        .retrieve()
                        .bodyToMono(HashMap.class)
                        .block();

        // 결과 확인
        log.info(response.toString());
        return response;
    }

}
