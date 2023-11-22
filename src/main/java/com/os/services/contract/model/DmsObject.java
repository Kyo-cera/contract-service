package com.os.services.contract.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DmsObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("activeversion")
    @Expose
    private Boolean activeversion;
    @SerializedName("finalized")
    @Expose
    private Boolean finalized;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("modified")
    @Expose
    private Modified modified;
    @SerializedName("created")
    @Expose
    private Created created;
    @SerializedName("icon")
    @Expose
    private Icon icon;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("clienturl")
    @Expose
    private String clienturl;
    @SerializedName("contentcount")
    @Expose
    private Integer contentcount;
    @SerializedName("contentlinkedcount")
    @Expose
    private Integer contentlinkedcount;
    @SerializedName("contentid")
    @Expose
    private String contentid;
    @SerializedName("contenttype")
    @Expose
    private String contenttype;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("slide")
    @Expose
    private String slide;
    @SerializedName("folder")
    @Expose
    private Boolean folder;
    @SerializedName("iscontextfolder")
    @Expose
    private Boolean iscontextfolder;
    @SerializedName("lock")
    @Expose
    private String lock;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;
}

