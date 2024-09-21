package service.validation;

import service.validation.chain.BaseValidator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ValidationRegistry {
    private final Map<String, Map<String, List<BaseValidator>>> routesToValidationMap;

    public ValidationRegistry(Map<String, Map<String, List<BaseValidator>>> routesToValidationMap) {
        this.routesToValidationMap = routesToValidationMap;
    }

    public Map<String, List<BaseValidator>> getValidationsByContextPath(String contextPath) {
        return routesToValidationMap.getOrDefault(contextPath, Collections.emptyMap());
    }
}
