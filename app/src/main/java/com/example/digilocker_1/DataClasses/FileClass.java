package com.example.digilocker_1.DataClasses;

public class FileClass {
    private String mime_type;
    private String size;        // In Bytes I guess. It is not mentioned in the DigiLocker Docs.
    private String hmac;        // Base64 of SHA256 of file content with client_secret as hashing key.
    private String file_content;

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getFile_content() {
        return file_content;
    }

    public void setFile_content(String file_content) {
        this.file_content = file_content;
    }
}
