package com.clabuyakchai.endpoint;

import com.clabuyakchai.soap.GetInfoRequest;
import com.clabuyakchai.soap.GetInfoResponse;
import com.clabuyakchai.utility.InfoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class InfoEndpoint {
    private static final String NAMESPACE_URI = "http://com.clabuyakchai/soap";

    @Autowired
    private InfoUtility infoUtility;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInfoRequest")
    @ResponsePayload
    public GetInfoResponse getInfoProject(@RequestPayload GetInfoRequest request){
        GetInfoResponse response = new GetInfoResponse();
        response.setInfoProject(infoUtility.getInfo());
        return response;
    }
}
