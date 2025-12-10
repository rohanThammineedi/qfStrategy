package rt.strategy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestConnectionDTO {
    
    private String application;
    private String userEmail;
    private String token;
    private String instanceUrl;
}
