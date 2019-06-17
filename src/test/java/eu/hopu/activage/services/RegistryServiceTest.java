package eu.hopu.activage.services;

import eu.hopu.activage.registryClient.DockerRegistryClient;
import eu.hopu.activage.registryClient.OkHttpDockerRegistryClient;
import eu.hopu.activage.services.dto.ImageInfo;
import eu.hopu.activage.storage.ImageInfoStore;
import eu.hopu.activage.storage.InMemoryImageInfoStore;
import eu.hopu.activage.utils.HopAsserts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static eu.hopu.activage.utils.RegistryUtils.*;


public class RegistryServiceTest {

    private RegistryService service;

    @Before
    public void setUp() throws Exception {
        DockerRegistryClient client = new OkHttpDockerRegistryClient(registryUrl, user, password);
        ImageInfoStore store = new InMemoryImageInfoStore();
        service = new RegistryServiceImpl(client, store);
    }

    @Test
    public void check_get_images_return_images() {
        List<String> actual = service.getImages();
        List<String> expected = new LinkedList<>(registryExpectedImages);
        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void check_docker_registry_image_tags_are_correct() {
        List<String> actual = service.getImagTags("myfirstimage");
        List<String> expected = new LinkedList<>(Arrays.asList("latest"));
        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void check_docker_registry_image_tags_are_empty_with_bad_image() {
        List<String> actual = service.getImagTags("myfirstimago");
        List<String> expected = new LinkedList<>();
        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void check_image_info_operations() {
        String imageId = "imageId";
        String imageInfo = "imageInfo";
        ImageInfo actual = service.addImageInfo(imageId, imageInfo);
        ImageInfo expected = new ImageInfo(imageId, imageInfo);
        HopAsserts.assertEqualsJson(expected, actual);

        actual = service.getImageInfo(imageId);
        HopAsserts.assertEqualsJson(expected, actual);

        String imageInfoModified = "imageInfoModified";
        actual = service.addImageInfo(imageId, imageInfoModified);
        expected = new ImageInfo(imageId, imageInfoModified);
        HopAsserts.assertEqualsJson(expected, actual);

        Assert.assertTrue(service.deleteImageInfo(imageId));

        Assert.assertNull(service.getImageInfo(imageId));

    }
}
