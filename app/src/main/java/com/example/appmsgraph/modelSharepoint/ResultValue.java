
package com.example.appmsgraph.modelSharepoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultValue {

    @SerializedName("@odata.context")
    @Expose
    private String odataContext;
    @SerializedName("value")
    @Expose
    private List<Value_> value = null;

    public String getOdataContext() {
        return odataContext;
    }

    public void setOdataContext(String odataContext) {
        this.odataContext = odataContext;
    }

    public List<Value_> getValue() {
        return value;
    }

    public void setValue(List<Value_> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ResultValue{" +
                "odataContext='" + odataContext + '\'' +
                ", value=" + value +
                '}';
    }
}
