package eu.hopu.activage.registryClient.dto;

import java.util.List;

public class RegistryTags {

    private String name;
    private List<String> tags;

    public RegistryTags() {
    }

    public RegistryTags(String name, List<String> tags) {
        this.name = name;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "RegistryTags{" +
                "name='" + name + '\'' +
                ", tags=" + tags +
                '}';
    }

}
