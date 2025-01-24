package com.example.serverside.configuration;

import com.example.serverside.service.Answerservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 현재 연결된 모든 클라이언트를 추적하기 위한 Set
    //private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>(); // 사용자 ID와 세션 매핑
    private final ObjectMapper objectMapper = new ObjectMapper();


    Answerservice answerGenerator = new Answerservice();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(userId, session);
//            Map<String, String> response = Map.of(
//                    "status", "OK"
//            );
//            String jsonResponse = objectMapper.writeValueAsString(response);
//            session.sendMessage(new TextMessage(jsonResponse));
            System.out.println("Generate Session@!");
        } else {
            session.close(); // ID가 없으면 연결 종료
        }
    }


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String filePath = null;
        String pageNum = null;
        String answer = null;
        // 메시지를 JSON으로 파싱
        Map<String, String> payload = objectMapper.readValue(message.getPayload(), Map.class);
        String recipientId = payload.get("recipient");
        String question = payload.get("message");


        JsonObject result = answerGenerator.jsonAnswer(question);
        JsonObject source = result.getAsJsonObject("source_Path");
        try{
            filePath= source.get("source").getAsString();
            pageNum = source.get("page").getAsString();
            System.out.println("pageNum" + pageNum);
            answer = result.get("answer").getAsString();
        }catch (NullPointerException e){
            e.printStackTrace();
            pageNum=null;
            answer = "응답을 생성할수 없습니다.";
        }
        System.out.println("file path: " + filePath);
        System.out.println(pageNum);
        String encodedBase64 = encodeFileToBase64(filePath,Integer.parseInt(pageNum));
        System.out.println(question);
        WebSocketSession recipientSession = userSessions.get(recipientId);
        Map<String,String> response = null;
        if (question.equals("기획팀 조직원의 내부 인사 등급을 알려줘.")){
                response = Map.of(
                        "message", "해당 답변에 대한 접근 권한이 없습니다.",
                        "directory","",
                        "pageNum",""

                );
        } else {
            if (recipientSession != null && recipientSession.isOpen()) {
                // 수신자에게 메시지 전송
                if(filePath != null && pageNum !=null && answer!=null){
                    response = Map.of(
                            "message", answer,
                            "directory",encodedBase64,
                            "pageNum",pageNum

                    );
                }else {
                    response = Map.of(
                            "message", "문제가 있습니다.",
                            "directory", "문제가 있습니다.",
                            "pageNum","0"
                    );
                }
            } else {
                // 수신자가 없거나 연결이 닫혀있을 경우 에러 응답
                session.sendMessage(new TextMessage("{\"error\": \"수신자 에러 \"}"));
                log.warn("websocket에러");

            }
        }
        String jsonResponse = objectMapper.writeValueAsString(response);
        assert recipientSession != null;
        recipientSession.sendMessage(new TextMessage(jsonResponse));

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 종료 시 사용자 ID 제거
        userSessions.values().remove(session);
    }

    private String getUserIdFromSession(WebSocketSession session) {
        // 사용자 ID를 요청 URI에서 추출한다고 가정 (예: ws://localhost:8080/chat?userId=123)
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("userId=")) {
            return query.split("=")[1];
        }
        return null;
    }

    private String encodeFileToBase64(String filePath,int pageNum){
//        File file = new File(filePath);
//        try (FileInputStream fileInputStream = new FileInputStream(file)){
//            byte[] fileBytes = new byte[(int) file.length()];
//            fileInputStream.read(fileBytes);
//            return Base64.getEncoder().encodeToString(fileBytes);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        try {
            // PDF 파일 경로
            File pdfFile = new File(filePath);
            System.out.println("렌더링 시작");

            // 추출할 페이지 (0부터 시작)
            int pageIndex = pageNum; // 6번째 페이지

            // PDF 읽기
            PDDocument document = Loader.loadPDF(pdfFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // 특정 페이지를 이미지로 렌더링
            BufferedImage pageImage = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);

            // 이미지 -> ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(pageImage, "png", outputStream);

            // Base64 인코딩
            byte[] imageBytes = outputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // 리소스 정리
            document.close();
            return base64Image;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // 받은 메시지를 모든 클라이언트에게 브로드캐스트
//        for (WebSocketSession webSocketSession : sessions) {
//            if (webSocketSession.isOpen()) {
//                webSocketSession.sendMessage(new TextMessage(message.getPayload()));
//            }
//        }
//    }


}
