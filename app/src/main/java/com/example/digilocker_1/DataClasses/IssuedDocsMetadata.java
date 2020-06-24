package com.example.digilocker_1.DataClasses;

public class IssuedDocsMetadata {
    private String name;
    private String type;
    private String size; // this will  be blank. IDK y.
    private String date;
    private String parent;
//    #TODO: mime can be a list of {mime_types} or simply a string mime_type. Will have to handle it some how.
    private String mime;
    private String uri;
    private String doctype;
    private String description;
    private String issuerid;
    private String issuer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
