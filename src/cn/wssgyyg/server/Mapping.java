package cn.wssgyyg.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruce on 4/8/2017.
 */
public class Mapping {
    private String name;
    private List<String> urlPattern;

    public Mapping() {
        urlPattern = new ArrayList<>();
    }

    public Mapping(String name, List<String> urlPattern) {
        this.name = name;
        this.urlPattern = urlPattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }
}
