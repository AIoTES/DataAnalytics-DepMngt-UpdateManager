package eu.hopu.activage.registryClient.dto;

import java.util.List;

public class RegistryRepositories {

    private List<String> repositories;

    public RegistryRepositories() {
    }

    public RegistryRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    public List<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<String> repositories) {
        this.repositories = repositories;
    }

    @Override
    public String toString() {
        return "RegistryRepositories{" +
                "repositories=" + repositories +
                '}';
    }

}
