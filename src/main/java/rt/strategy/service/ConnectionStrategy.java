package rt.strategy.service;

import rt.strategy.dto.TestConnectionDTO;

/**
 *  Connection service layer interface
 */
public interface ConnectionStrategy {
    
    String testConnection(TestConnectionDTO testConnectionDTO);
}
