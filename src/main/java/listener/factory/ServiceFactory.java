package listener.factory;

import service.Service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {
    private final Map<String, Service> services = new HashMap<>();

    public void registerService(String name, Service service) {
        services.put(name, service);
    }

    public Service getService(String name) {
        Service service = services.get(name);
        if (service == null) {
            throw new IllegalArgumentException("Service not found: " + name);
        }
        return service;
    }

}