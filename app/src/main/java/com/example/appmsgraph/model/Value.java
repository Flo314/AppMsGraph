
package com.example.appmsgraph.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Value {
    public Value(String odataEtag, String createdDateTime, String eTag, String id, String lastModifiedDateTime, String webUrl, CreatedBy createdBy, LastModifiedBy lastModifiedBy, ParentReference parentReference, ContentType contentType, String fieldsOdataContext, Fields fields) {
        this.odataEtag = odataEtag;
        this.createdDateTime = createdDateTime;
        this.eTag = eTag;
        this.id = id;
        this.lastModifiedDateTime = lastModifiedDateTime;
        this.webUrl = webUrl;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.parentReference = parentReference;
        this.contentType = contentType;
        this.fieldsOdataContext = fieldsOdataContext;
        this.fields = fields;
    }

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

}
