package rt.strategy.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConnectionFactory {
    
    @Autowired
    private Map<String, ConnectionStrategy> strategyMap;

    public ConnectionStrategy getStrategy(String name){
        ConnectionStrategy strategy = strategyMap.get(name.toUpperCase());
        if(strategy == null){
            throw new IllegalArgumentException("No strategy found for name: " + name);
        }
        return strategy;
    }
}
