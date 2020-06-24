package com.example.digilocker_1.DataClasses;

import java.util.List;

public class GetSelfUploadedDocsResponse {
    private String directory;
    private List<SelfUploadedDocsMetadata> items;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public List<SelfUploadedDocsMetadata> getItems() {
        return items;
    }

    public void setItems(List<SelfUploadedDocsMetadata> items) {
        this.items = items;
    }
}
