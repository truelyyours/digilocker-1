package com.example.digilocker_1.DataClasses;

import java.util.List;

public class GetIssuedDocsResponse {
    private List<IssuedDocsMetadata> items;

    public List<IssuedDocsMetadata> getItems() {
        return items;
    }

    public void setItems(List<IssuedDocsMetadata> items) {
        this.items = items;
    }
}
