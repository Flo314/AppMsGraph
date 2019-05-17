
package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {

    @SerializedName("@odata.etag")
    @Expose
    private String odataEtag;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("visite")
    @Expose
    private String visite;
    @SerializedName("prenom")
    @Expose
    private String prenom;
    @SerializedName("BusinessManager")
    @Expose
    private String businessManager;
    @SerializedName("Historiquevisite")
    @Expose
    private String historiquevisite;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ContentType")
    @Expose
    private String contentType;
    @SerializedName("Modified")
    @Expose
    private String modified;
    @SerializedName("Created")
    @Expose
    private String created;
    @SerializedName("AuthorLookupId")
    @Expose
    private String authorLookupId;
    @SerializedName("EditorLookupId")
    @Expose
    private String editorLookupId;
    @SerializedName("_UIVersionString")
    @Expose
    private String uIVersionString;
    @SerializedName("Attachments")
    @Expose
    private Boolean attachments;
    @SerializedName("Edit")
    @Expose
    private String edit;
    @SerializedName("LinkTitleNoMenu")
    @Expose
    private String linkTitleNoMenu;
    @SerializedName("LinkTitle")
    @Expose
    private String linkTitle;
    @SerializedName("ItemChildCount")
    @Expose
    private String itemChildCount;
    @SerializedName("FolderChildCount")
    @Expose
    private String folderChildCount;
    @SerializedName("_ComplianceFlags")
    @Expose
    private String complianceFlags;
    @SerializedName("_ComplianceTag")
    @Expose
    private String complianceTag;
    @SerializedName("_ComplianceTagWrittenTime")
    @Expose
    private String complianceTagWrittenTime;
    @SerializedName("_ComplianceTagUserId")
    @Expose
    private String complianceTagUserId;

    public String getOdataEtag() {
        return odataEtag;
    }

    public void setOdataEtag(String odataEtag) {
        this.odataEtag = odataEtag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVisite() {
        return visite;
    }

    public void setVisite(String visite) {
        this.visite = visite;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getBusinessManager() {
        return businessManager;
    }

    public void setBusinessManager(String businessManager) {
        this.businessManager = businessManager;
    }

    public String getHistoriquevisite() {
        return historiquevisite;
    }

    public void setHistoriquevisite(String historiquevisite) {
        this.historiquevisite = historiquevisite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAuthorLookupId() {
        return authorLookupId;
    }

    public void setAuthorLookupId(String authorLookupId) {
        this.authorLookupId = authorLookupId;
    }

    public String getEditorLookupId() {
        return editorLookupId;
    }

    public void setEditorLookupId(String editorLookupId) {
        this.editorLookupId = editorLookupId;
    }

    public String getUIVersionString() {
        return uIVersionString;
    }

    public void setUIVersionString(String uIVersionString) {
        this.uIVersionString = uIVersionString;
    }

    public Boolean getAttachments() {
        return attachments;
    }

    public void setAttachments(Boolean attachments) {
        this.attachments = attachments;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getLinkTitleNoMenu() {
        return linkTitleNoMenu;
    }

    public void setLinkTitleNoMenu(String linkTitleNoMenu) {
        this.linkTitleNoMenu = linkTitleNoMenu;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public String getItemChildCount() {
        return itemChildCount;
    }

    public void setItemChildCount(String itemChildCount) {
        this.itemChildCount = itemChildCount;
    }

    public String getFolderChildCount() {
        return folderChildCount;
    }

    public void setFolderChildCount(String folderChildCount) {
        this.folderChildCount = folderChildCount;
    }

    public String getComplianceFlags() {
        return complianceFlags;
    }

    public void setComplianceFlags(String complianceFlags) {
        this.complianceFlags = complianceFlags;
    }

    public String getComplianceTag() {
        return complianceTag;
    }

    public void setComplianceTag(String complianceTag) {
        this.complianceTag = complianceTag;
    }

    public String getComplianceTagWrittenTime() {
        return complianceTagWrittenTime;
    }

    public void setComplianceTagWrittenTime(String complianceTagWrittenTime) {
        this.complianceTagWrittenTime = complianceTagWrittenTime;
    }

    public String getComplianceTagUserId() {
        return complianceTagUserId;
    }

    public void setComplianceTagUserId(String complianceTagUserId) {
        this.complianceTagUserId = complianceTagUserId;
    }

}
