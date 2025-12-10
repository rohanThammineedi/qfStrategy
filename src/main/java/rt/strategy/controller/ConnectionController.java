package rt.strategy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rt.strategy.dto.ConnectionDTO;
import rt.strategy.dto.TestConnectionDTO;
import rt.strategy.service.ConnectionManagement;

@RestController
@RequestMapping("/connections")
@Slf4j
public class ConnectionController {
    
    @Autowired
    private ConnectionManagement connectionManagement;


    @PostMapping("/test")
    public String testConnection(@RequestBody TestConnectionDTO testConnectionDTO){
        log.info("Received test connection request for application: {}", testConnectionDTO.getApplication());
        return connectionManagement.testConnection(testConnectionDTO);
    }

    @PostMapping("/create")
    public String createConnection(@RequestBody ConnectionDTO connectionDTO){
        log.info("Received create connection request.");
        return connectionManagement.createConnection(connectionDTO);
    }

}
