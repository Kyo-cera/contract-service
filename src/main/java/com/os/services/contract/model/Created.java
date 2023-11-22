package com.os.services.contract.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Created {

    @SerializedName("on")
    @Expose
    private String on;
    @SerializedName("by")
    @Expose
    private By_ by;

}
