package com.example.serverside.controller;


import com.example.serverside.configuration.GetIP;

import com.example.serverside.service.Answerservice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class controller {


    @Autowired
    Answerservice as;


    @GetMapping("/report")//client단 변경시 서버에서 경로 수집
    public int getDirectoryStatus(@RequestParam String filePath){
        System.out.println("selected filePath: " + filePath);
        return 1;
    }
    @GetMapping("/")
    public String hello(){
        return "Success";
    }

    @PostMapping("/set-directory")
    @ResponseStatus(HttpStatus.OK)
    public void setDirect(@RequestBody HashMap<String, Object> map){
        System.out.println(map);

    }

    @PostMapping("/directory-event")
    @ResponseStatus(HttpStatus.OK)
    public void eventWatcher(@RequestBody HashMap<String, Object> map){
        System.out.println(map.size());

    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> LoginTest(String PID, String password){
        System.out.println(PID);
        System.out.println(password);
        if(PID.equals("20193290")&& password.equals("dufrns12")){
            return  new ResponseEntity<Integer>(HttpStatus.OK);
        }else{
            return  new ResponseEntity<Integer>(HttpStatus.UNAUTHORIZED);//UNAUTHORIZED는 401코드
        }
    }

    @GetMapping("/test")
    public void testHander(String question){
        String result = as.getAnswer(question);
        System.out.println(result);
    }

    @GetMapping("/IPTest")//접속자 IP조회
    public String testRequest(HttpServletRequest request){
        String IP = GetIP.getRemoteIP(request);
        return IP;
    }
//    @GetMapping("/getUser")
//    public humanResourceDto  getUser(){
//        humanResourceDto result = service.getUserInfo("20193288");//전달받은 dto객체 반환.
//        System.out.println(result);
//        return result;
//    }
//
//    @GetMapping("/getAllUser")
//    public List<humanResourceDto> getAllUser(){
//        List<humanResourceDto> result = service.getAllUserService();//전달받은 dto객체 반환.
//        System.out.println(result);
//        return result;
//    }
//
//    @GetMapping("/department")
//    public Optional<department> searchUser(){
//        Optional<department> departInfo = service.findDepartmentByHID("20193290");
//        System.out.println(departInfo.getClass());
//        return departInfo;
//    }

}
