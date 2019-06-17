package eu.hopu.activage.registryClient;

import java.util.List;

public interface DockerRegistryClient {

    List<String> getImages();

    List<String> getImagTags(String imageId);

}
