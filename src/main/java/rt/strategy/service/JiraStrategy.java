package rt.strategy.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import rt.strategy.constants.JiraCloudApiEndpoints;
import rt.strategy.dto.TestConnectionDTO;
import rt.strategy.utils.ConnectionUtils;

@Service("JIRA")
@Slf4j
public class JiraStrategy implements ConnectionStrategy {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String testConnection(TestConnectionDTO testConnectionDTO) {
        String instanceUrl = testConnectionDTO.getInstanceUrl();
        String userEmail = testConnectionDTO.getUserEmail();
        String apiToken = testConnectionDTO.getToken();

        boolean hasAdminPermissions = checkAdminPermissions(instanceUrl, userEmail, apiToken);
        if (hasAdminPermissions) {
            return "Connection successful and user has admin permissions.";
        } else {
            return "User does not have admin permissions.";
        }
    }

    private boolean checkAdminPermissions(String instanceUrl, String userEmail, String apiToken) {
        HttpHeaders headers = ConnectionUtils.createAuthHeaders(userEmail, apiToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String finalEndpoint = ConnectionUtils.buildURI(instanceUrl, JiraCloudApiEndpoints.JIRA_ADMIN_PERMISSION);
        try {
            ResponseEntity<String> response = restTemplate.exchange(finalEndpoint, HttpMethod.GET, entity, String.class);
            log.info("Response Body: {}", response.getBody());

            JSONObject permissions = new JSONObject(response.getBody()).getJSONObject("permissions");
            JSONObject adminPermission = permissions.getJSONObject("ADMINISTER");
            log.info("Admin Permission: {}", adminPermission);
            if (adminPermission.has("havePermission")) {
                boolean hasPermission = adminPermission.getBoolean("havePermission");
                log.info("Parsed hasPermission: {}", hasPermission);
                return hasPermission;
            } else {
                throw new RuntimeException("havePermission field not found in the response");
            }
        } catch (Exception e) {
            log.error("Error while checking admin permissions: {}", e.getMessage());
            throw new RuntimeException("Failed to verify admin permissions: " + e.getMessage());
        }
    }

}
