package com.example.digilocker_1.XMLClasses;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import androidx.annotation.NonNull;

@Root(name = "PullDocRequest")
@Namespace(reference = "http://tempuri.org/", prefix = "ns2")
public class GetDocRequestXML {

    private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    @Attribute(name = "ver")
    String version;
    @Attribute(name = "ts")
    String timestamp;
    @Attribute(name = "txn")
    String txnId;
    @Attribute(name = "orgId")
    String orgId;
    @Attribute(name = "appId")
    String appId;
    @Attribute(name = "keyhash")
    String keyhash;

    @Element(name = "DocDetails")
    DocDetails docDetails;

    public GetDocRequestXML(String version, String timestamp, String txnId, String orgId, String appId, String keyhash, DocDetails docDetails) {
        this.version = version;
        this.timestamp = timestamp;
        this.txnId = txnId;
        this.orgId = orgId;
        this.appId = appId;
        this.keyhash = keyhash;
        this.docDetails = docDetails;
    }

    @NonNull
    @Override
    public String toString() {
        return xml;

    }
}