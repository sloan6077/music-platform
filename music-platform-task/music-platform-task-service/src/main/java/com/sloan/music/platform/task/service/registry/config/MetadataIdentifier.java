package com.sloan.music.platform.task.service.registry.config;

import com.alibaba.dubbo.common.URL;

import static com.alibaba.dubbo.common.Constants.SIDE_KEY;
import static com.sloan.music.platform.task.service.util.Constants.*;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/7/31
 **/
public class MetadataIdentifier {


    public static final String SEPARATOR = ":";
    public final static String DEFAULT_PATH_TAG = "metadata";
    public final static String META_DATA_STORE_TAG = ".metaData";

    private String serviceInterface;
    private String version;
    private String group;
    private String side;
    private String application;

    public MetadataIdentifier() {
    }

    public MetadataIdentifier(String serviceInterface, String version, String group, String side, String application) {
        this.serviceInterface = serviceInterface;
        this.version = version;
        this.group = group;
        this.side = side;
        this.application = application;
    }

    public MetadataIdentifier(URL url) {
        this.serviceInterface = url.getServiceInterface();
        this.version = url.getParameter(VERSION_KEY);
        this.group = url.getParameter(GROUP_KEY);
        this.side = url.getParameter(SIDE_KEY);
        setApplication(url.getParameter(APPLICATION_KEY));
    }

    public String getUniqueKey(KeyTypeEnum keyType) {
        if (keyType == KeyTypeEnum.PATH) {
            return getFilePathKey();
        }
        return getIdentifierKey();
    }

    public String getIdentifierKey() {
        return serviceInterface + SEPARATOR + (version == null ? "" : version + SEPARATOR) + (group == null ? "" : group + SEPARATOR) + side + SEPARATOR + application;
    }

    private String getFilePathKey() {
        return getFilePathKey(DEFAULT_PATH_TAG);
    }

    private String getFilePathKey(String pathTag) {
        return pathTag + PATH_SEPARATOR + toServicePath() + PATH_SEPARATOR + (version == null ? "" : (version + PATH_SEPARATOR))
                + (group == null ? "" : (group + PATH_SEPARATOR)) + side + PATH_SEPARATOR + getApplication();
    }

    private String toServicePath() {
        if (ANY_VALUE.equals(serviceInterface)) {
            return "";
        }
        return URL.encode(serviceInterface);
    }


    public String getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public static enum KeyTypeEnum {
        PATH, UNIQUE_KEY
    }
}
