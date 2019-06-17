package eu.hopu.activage;

import eu.hopu.activage.registryClient.DockerRegistryClient;
import eu.hopu.activage.registryClient.OkHttpDockerRegistryClient;
import eu.hopu.activage.utils.HopAsserts;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static eu.hopu.activage.utils.RegistryUtils.*;

public class DockerRegistryClientTest {

    private DockerRegistryClient client;

    @Before
    public void setUp() {
        client = new OkHttpDockerRegistryClient(registryUrl, user, password);
    }

    @Test
    public void check_docker_registry_images_are_correct() {
        List<String> actual = client.getImages();
        List<String> expected = new LinkedList<>(registryExpectedImages);
        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void check_docker_registry_image_tags_are_correct() {
        List<String> actual = client.getImagTags("myfirstimage");
        List<String> expected = new LinkedList<>(Arrays.asList("latest"));
        HopAsserts.assertEqualsJson(expected, actual);
    }

    @Test
    public void check_docker_registry_image_tags_are_empty_with_bad_image() {
        List<String> actual = client.getImagTags("myfirstimago");
        List<String> expected = new LinkedList<>();
        HopAsserts.assertEqualsJson(expected, actual);
    }




}
