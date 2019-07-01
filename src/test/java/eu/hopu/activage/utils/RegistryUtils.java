package eu.hopu.activage.utils;

import java.util.Arrays;
import java.util.List;

public class RegistryUtils {

    public static List<String> registryExpectedImages = Arrays.asList("activage-nr-service-composition",
            "aiotes-ide", "clickdigital", "clickdigital-anomaly", "clickdigital-backend", "clickdigital-frontend",
            "code.generator", "component-configuration", "component-configurator", "data-analyser", "data-analytics-server",
            "data-manipulator", "data_analyser.pca", "deployment-manager",
            "device_manager", "dl-data-indexing", "dl-datamodel-workbench",
            "dl-metadata-storage-explorer", "dl-query-component", "feature-selection-filter-method", "feature-selection-wrapper-method",
            "hierarchical-clustering-method", "hypothesis-testing", "independentdatastorage",
            "influxdb", "intermw", "interoperability-demo-app", "ipsm-core", "json-server", "maintenance-panel", "metadata-storage-server",
            "mongo", "myfirstimage", "ont-explorer_dev-semantic_serv-semantics", "siltool", "syntactic-translator", "update-manager");
    public static String registryUrl = "https://docker-activage.satrd.es";
    public static String user = "activage";
    public static String password = "docker.activage";

}
