package rt.strategy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rt.strategy.dto.ConnectionDTO;
import rt.strategy.dto.TestConnectionDTO;

@Service
public class ConnectionManagement {

    @Autowired
    private ConnectionFactory connectionFactory;

    public String testConnection(TestConnectionDTO testConnectionDTO){
        String strategyName = testConnectionDTO.getApplication();
        ConnectionStrategy strategy = connectionFactory.getStrategy(strategyName);
        return strategy.testConnection(testConnectionDTO);
    }

    public String createConnection(ConnectionDTO connectionDTO){
        StringBuilder result = new StringBuilder();

        TestConnectionDTO source = connectionDTO.getSource();
        String sourceStrategyName = source.getApplication();
        ConnectionStrategy sourceStrategy = connectionFactory.getStrategy(sourceStrategyName);
        String sourceResult = sourceStrategy.testConnection(source);
        result.append("Source Connection: ").append(sourceResult).append("\n");
        TestConnectionDTO target = connectionDTO.getDestination();
        String targetStrategyName = target.getApplication();
        ConnectionStrategy targetStrategy = connectionFactory.getStrategy(targetStrategyName);
        String targetResult = targetStrategy.testConnection(target);
        result.append("Target Connection: ").append(targetResult);
        return result.toString();
    }

    
}
