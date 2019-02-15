package br.com.fr.commons;

public class BaseParameters {

    private static BaseParameters instance;

    private String appImgPath = "";
    private String libImgPath = "/images/";

    public static BaseParameters getInstance() {
        if (instance == null) {
            instance = new BaseParameters();
        }

        return instance;
    }

    private BaseParameters() {
    }

    public String getAppImgPath() {
        return appImgPath;
    }

    public void setAppImgPath(String appImgPath) {
        this.appImgPath = appImgPath;
    }

    public String getLibImgPath() {
        return libImgPath;
    }

    public void setLibImgPath(String libImgPath) {
        this.libImgPath = libImgPath;
    }

}
