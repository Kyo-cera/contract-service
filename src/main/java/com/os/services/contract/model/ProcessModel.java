package com.os.services.contract.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("creatorId")
    @Expose
    private String creatorId;
    @SerializedName("creationTime")
    @Expose
    private String creationTime;
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("modifierId")
    @Expose
    private String modifierId;
    @SerializedName("modificationTime")
    @Expose
    private String modificationTime;
    @SerializedName("state")
    @Expose
    private String state;
}
