package com.example.digilocker_1.XMLClasses;

import org.simpleframework.xml.Element;

public class DocDetails {

    @Element(name = "URI")
    String uri;

    public DocDetails(String uri) {
        this.uri = uri;
    }
}