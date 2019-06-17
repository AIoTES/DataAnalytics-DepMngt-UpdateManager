package eu.hopu.activage.registryClient;

import com.google.gson.Gson;
import eu.hopu.activage.registryClient.dto.RegistryRepositories;
import eu.hopu.activage.registryClient.dto.RegistryTags;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpDockerRegistryClient implements DockerRegistryClient {

    private static final Logger LOG = LogManager.getLogger(OkHttpDockerRegistryClient.class);

    private final String registryUrl;
    private final String user;
    private final String password;
    private final OkHttpClient client;
    private final Gson gson;

    public OkHttpDockerRegistryClient(String registryUrl, String user, String password) {
        this.registryUrl = registryUrl;
        this.user = user;
        this.password = password;
        this.gson = new Gson();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectionPool(new ConnectionPool(10, 30L, TimeUnit.SECONDS));
        this.client = builder.build();
    }

    @Override
    public List<String> getImages() {
        LOG.info("Retrieving images from registry → {}", registryUrl);

        String stringUrl = registryUrl + "/v2/_catalog";
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            LOG.error("Cannot perform operation over a malformed url → {}", stringUrl);
            return new LinkedList<>();
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", Credentials.basic(user, password))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                RegistryRepositories repositories = gson.fromJson(response.body().string(), RegistryRepositories.class);
                LOG.debug("Images retrieved from registry → {}", repositories.getRepositories());
                return repositories.getRepositories();
            } else {
                LOG.warn("Cannot retrieve images from registry → {}", registryUrl);
                return new LinkedList<>();
            }
        } catch (IOException e) {
            LOG.error("{} request throws exception → {}", stringUrl, e.getMessage());
            return new LinkedList<>();
        }
    }

    @Override
    public List<String> getImagTags(String imageId) {
        LOG.info("Retrieving image tags for image id → {}", imageId);

        String stringUrl = registryUrl + "/v2/" + imageId + "/tags/list";
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            LOG.error("Cannot perform operation over a malformed url → {}", stringUrl);
            return new LinkedList<>();
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", Credentials.basic(user, password))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                RegistryTags tags = gson.fromJson(response.body().string(), RegistryTags.class);
                LOG.debug("Tags retrieved from registry → {}", tags.getTags());
                return tags.getTags();
            } else {
                LOG.warn("Cannot retrieve tags for {} from registry → {}", imageId, registryUrl);
                return new LinkedList<>();
            }
        } catch (IOException e) {
            LOG.error("{} request throws exception → {}", stringUrl, e.getMessage());
            return new LinkedList<>();
        }
    }
}
