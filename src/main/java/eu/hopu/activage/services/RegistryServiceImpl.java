package eu.hopu.activage.services;

import eu.hopu.activage.registryClient.DockerRegistryClient;
import eu.hopu.activage.services.dto.ImageInfo;
import eu.hopu.activage.storage.ImageInfoStore;

import java.util.List;

public class RegistryServiceImpl implements RegistryService {

    private DockerRegistryClient client;
    private ImageInfoStore store;

    public RegistryServiceImpl(DockerRegistryClient client, ImageInfoStore store) {
        this.client = client;
        this.store = store;
    }

    @Override
    public List<String> getImages() {
        return client.getImages();
    }

    @Override
    public List<String> getImagTags(String imageId) {
        return client.getImagTags(imageId);
    }

    @Override
    public ImageInfo getImageInfo(String imageId) {
        return store.getImageInfo(imageId);
    }

    @Override
    public ImageInfo addImageInfo(String imageId, String imageInfo) {
        return store.addImageInfo(imageId, imageInfo);
    }

    @Override
    public ImageInfo updateImageInfo(String imageId, String imageInfo) {
        return store.updateImageInfo(imageId, imageInfo);
    }

    @Override
    public boolean deleteImageInfo(String imageId) {
        return store.deleteImageInfo(imageId);
    }

}
