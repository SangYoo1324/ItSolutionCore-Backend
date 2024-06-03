package com.example.ItSolutionCore.common.api;


import com.example.ItSolutionCore.common.dto.GenericResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*

Health check, docker curl for server check

*/

@RestController
@Slf4j
public class SystemApiController {

    @Value("${server.env}")  // blue, green, local
    private String env;

    @Value("${server.port}")
    private String serverPort;  // 8080, 8081

    @Value("${server.serverAddress}")
    private String serverAddress; // ec2 elastic IP address, localhost

    @Value("${serverName}") // local_server, green_server, blue_server
    private String serverName;


    @GetMapping("/hc")
    public ResponseEntity<?> healthCheck(){
        Map<String,String> resp  = new HashMap<>();
        resp.put("serverName", serverName);
        resp.put("serverAddress", serverAddress);
        resp.put("serverPort", serverPort);
        resp.put("env", env);
        return ResponseEntity.status(HttpStatus.OK).body(GenericResponseDto.builder().response("hi").build());
    }

    @GetMapping("/env")
    public ResponseEntity<?> getEnv(){

        return ResponseEntity.status(HttpStatus.OK).body(env);
    }
}
