package com.itxiaoqiang;


import java.util.HashMap;
import java.util.Map;

/**
 * @author hello_liu
 * Created by xiaoqiang on 2019/7/14
 **/
public class FreemarkeView {
    private String ftlPath;
    private Map<String, Object> models = new HashMap<>();

    public FreemarkeView(String ftlPath) {
        this.ftlPath = ftlPath;
    }

    public FreemarkeView(String ftlPath, Map<String, Object> model) {
        this.ftlPath = ftlPath;
        this.models = model;
    }

    public void setModel(String key, Object model) {
        models.put(key, model);
    }

    public void removeModel(String key) {
        models.remove(key);
    }

    public String getFtlPath() {
        return ftlPath;
    }

    public void setFtlPath(String ftlPath) {
        this.ftlPath = ftlPath;
    }

    public Map<String, Object> getModels() {
        return models;
    }

    public void setModels(Map<String, Object> models) {
        this.models = models;
    }
}
