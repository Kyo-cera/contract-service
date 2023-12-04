package com.os.services.contract.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterByIndexData {
    @SerializedName("contractstatus")
    @Expose
    private String contractStatus;
    @SerializedName("processingstatus")
    @Expose
    private String processingStatus;
    @SerializedName("yuvsigstatus")
    @Expose
    private String yuvsigStatus;
    @SerializedName("contracttype")
    @Expose
    private String contractType;
}
