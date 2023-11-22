package com.os.services.contract.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestBodyStartProcess {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;
}
