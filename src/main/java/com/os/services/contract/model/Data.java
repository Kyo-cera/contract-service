package com.os.services.contract.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {

    @SerializedName("protocolnumber")
    @Expose
    private String protocolNumber;
    @SerializedName("contractcategory")
    @Expose
    private String contractCategory;
    @SerializedName("processingstatus")
    @Expose
    private String processingStatus;
    @SerializedName("contractstatus")
    @Expose
    private String contractStatus;
    @SerializedName("notificationperiod")
    @Expose
    private Integer notificationPeriod;
    @SerializedName("contracttype")
    @Expose
    private String contractType;
    @SerializedName("contractstartdate")
    @Expose
    private String contractStartdate;
    @SerializedName("contractenddate")
    @Expose
    private String contractEnddate;
    @SerializedName("responsibleperson")
    @Expose
    private String responsiblePerson;
    @SerializedName("automaticrenewalday")
    @Expose
    private Integer automaticRenewalday;
    @SerializedName("businessunit")
    @Expose
    private String businessUnit;
    @SerializedName("partnertype")
    @Expose
    private String partnerType;
    @SerializedName("partnername")
    @Expose
    private String partnerName;
    @SerializedName("yuvsigstatus")
    @Expose
    private String yuvsigStatus;

}
