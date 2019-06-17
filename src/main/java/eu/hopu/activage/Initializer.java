package eu.hopu.activage;

import eu.hopu.activage.registryClient.DockerRegistryClient;
import eu.hopu.activage.registryClient.OkHttpDockerRegistryClient;
import eu.hopu.activage.services.RegistryService;
import eu.hopu.activage.services.RegistryServiceImpl;
import eu.hopu.activage.storage.ImageInfoStore;
import eu.hopu.activage.storage.InMemoryImageInfoStore;
import eu.hopu.activage.storage.PostgresInfoStore;
import eu.hopu.activage.utils.GetEnvOrProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    private static final Logger LOG = LogManager.getLogger(Initializer.class);

    public static RegistryService service;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Initializing Update manager");
        GetEnvOrProperty.getInstance().loadProperties();
        String registryUrl = GetEnvOrProperty.getInstance().get("DOCKER_REGISTRY_SERVER_URL");
        String registryUser = GetEnvOrProperty.getInstance().get("DOCKER_REGISTRY_USER");
        String registryPassword = GetEnvOrProperty.getInstance().get("DOCKER_REGISTRY_PASSWORD");

        DockerRegistryClient client = new OkHttpDockerRegistryClient(registryUrl, registryUser, registryPassword);
        ImageInfoStore store;
        if (GetEnvOrProperty.getInstance().get("STORAGE").equals("POSTGRES"))
            store = new PostgresInfoStore(
                    GetEnvOrProperty.getInstance().get("POSTGRES_HOST"),
                    GetEnvOrProperty.getInstance().get("POSTGRES_PORT"),
                    GetEnvOrProperty.getInstance().get("POSTGRES_DATABASE"),
                    GetEnvOrProperty.getInstance().get("POSTGRES_USER"),
                    GetEnvOrProperty.getInstance().get("POSTGRES_PASSWORD")
            );
        else
            store = new InMemoryImageInfoStore();

        service = new RegistryServiceImpl(client, store);

        LOG.info("Update manager initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Stopping updating manager");
        LOG.info("Updating manager stopped");
    }

}
