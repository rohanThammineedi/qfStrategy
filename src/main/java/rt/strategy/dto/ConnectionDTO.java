package rt.strategy.dto;

import lombok.Data;

@Data
public class ConnectionDTO {
    
    private TestConnectionDTO source;
    private TestConnectionDTO destination;
}
