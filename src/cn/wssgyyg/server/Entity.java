package cn.wssgyyg.server;

/**
 * Created by bruce on 4/8/2017.
 */
public class Entity {
    private String name;
    private String clz;

    public Entity(String name, String clz) {
        this.name = name;
        this.clz = clz;
    }

    public Entity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }
}
