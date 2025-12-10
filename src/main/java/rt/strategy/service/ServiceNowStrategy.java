package rt.strategy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.HttpClientErrorException;
import rt.strategy.dto.TestConnectionDTO;

@Service("SERVICENOW")
@Slf4j
public class ServiceNowStrategy implements ConnectionStrategy {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String testConnection(TestConnectionDTO testConnectionDTO) {
        // 1. Correct URL structure using sysparm_query
        String url = testConnectionDTO.getInstanceUrl() + "/api/now/table/sys_user?sysparm_query=user_name=" + testConnectionDTO.getUserEmail();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        // 2. INTELLIGENT AUTH SWITCH
        // If the "token" looks like a password (short) -> Use Basic Auth
        // If the "token" is a long hash (standard ServiceNow tokens are long) -> Use Bearer Auth
        // Ideally, add a field to DTO like 'authType', but for now:
        
        if (isLikelyToken(testConnectionDTO.getToken())) {
             headers.set("Authorization", "Bearer " + testConnectionDTO.getToken());
        } else {
             headers.setBasicAuth(testConnectionDTO.getUserEmail(), testConnectionDTO.getToken());
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            log.info("Response Status Code: {}", response.getStatusCode());
            // 3. Logic Check: Did we find the user?
            if (response.getBody() != null && !response.getBody().contains(testConnectionDTO.getUserEmail())) {
                 return "Connection Successful, but user not found in ServiceNow.";
            }

            return "ServiceNow connection successful";

        } catch (HttpClientErrorException e) {
            // Catch 401/403 specifically to give better error messages
            return "ServiceNow Auth Failed (" + e.getStatusCode() + "): " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "ServiceNow connection failed: " + e.getMessage();
        }
    }

    private boolean isLikelyToken(String credential) {
        // Simple heuristic: ServiceNow Personal Access Tokens are usually very long (>30 chars)
        // Adjust logic based on your actual inputs
        return credential != null && credential.length() > 30; 
    }
}