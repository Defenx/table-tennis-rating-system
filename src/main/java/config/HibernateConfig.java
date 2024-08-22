package config;

import org.hibernate.HibernateError;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * The type Hibernate config.
 */
public class HibernateConfig {
    /**
     * Build session factory session factory.
     *
     * @return the session factory
     */
    public SessionFactory buildSessionFactory(){
        try {
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (HibernateError he) {

            throw new HibernateError("Error creating session factory");
        }
    }
}