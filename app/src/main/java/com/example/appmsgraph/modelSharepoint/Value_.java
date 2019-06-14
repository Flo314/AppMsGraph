
package com.example.appmsgraph.modelSharepoint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value_ {

    @SerializedName("@odata.etag")
    @Expose
    private String odataEtag;
    @SerializedName("createdDateTime")
    @Expose
    private String createdDateTime;
    @SerializedName("eTag")
    @Expose
    private String eTag;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lastModifiedDateTime")
    @Expose
    private String lastModifiedDateTime;
    @SerializedName("webUrl")
    @Expose
    private String webUrl;
    @SerializedName("createdBy")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("lastModifiedBy")
    @Expose
    private LastModifiedBy lastModifiedBy;
    @SerializedName("parentReference")
    @Expose
    private ParentReference parentReference;
    @SerializedName("contentType")
    @Expose
    private ContentType contentType;
    @SerializedName("fields@odata.context")
    @Expose
    private String fieldsOdataContext;
    @SerializedName("fields")
    @Expose
    private Fields fields;

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public LastModifiedBy getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(LastModifiedBy lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ParentReference getParentReference() {
        return parentReference;
    }

    public void setParentReference(ParentReference parentReference) {
        this.parentReference = parentReference;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getFieldsOdataContext() {
        return fieldsOdataContext;
    }

    public void setFieldsOdataContext(String fieldsOdataContext) {
        this.fieldsOdataContext = fieldsOdataContext;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Value_{" +
                "odataEtag='" + odataEtag + '\'' +
                ", createdDateTime='" + createdDateTime + '\'' +
                ", eTag='" + eTag + '\'' +
                ", id='" + id + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", createdBy=" + createdBy +
                ", lastModifiedBy=" + lastModifiedBy +
                ", parentReference=" + parentReference +
                ", contentType=" + contentType +
                ", fieldsOdataContext='" + fieldsOdataContext + '\'' +
                ", fields=" + fields +
                '}';
    }
}
