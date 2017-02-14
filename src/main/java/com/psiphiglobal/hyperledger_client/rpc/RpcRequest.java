package com.psiphiglobal.hyperledger_client.rpc;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RpcRequest
{
    private static final String JSON_RPC_VERSION = "2.0";

    public static class Builder
    {
        private RpcRequest request;

        public Builder(Type type, String chaincodeName, String functionName)
        {
            request = new RpcRequest();

            request.jsonRpcVersion = JSON_RPC_VERSION;
            request.requestId = generateRequestId();
            request.type = type.getValue();

            request.params = new Parameters();
            request.params.type = 1;

            request.params.chaincodeId = new Parameters.ChaincodeId();
            request.params.chaincodeId.name = chaincodeName;

            request.params.payload = new Parameters.Payload();
            request.params.payload.args = new ArrayList<>();
            request.params.payload.args.add(functionName);
        }

        public Builder addParameter(String... parameter)
        {
            request.params.payload.args.addAll(Arrays.asList(parameter));
            return this;
        }

        public RpcRequest build()
        {
            return request;
        }

        private String generateRequestId()
        {
            return UUID.randomUUID().toString();
        }
    }

    public enum Type
    {
        INVOKE("invoke"),
        QUERY("query");

        private String value;

        Type(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    private RpcRequest()
    {
    }

    @SerializedName("id")
    private String requestId;

    @SerializedName("jsonrpc")
    private String jsonRpcVersion;

    @SerializedName("method")
    private String type;

    @SerializedName("params")
    private Parameters params;

    private static class Parameters
    {
        @SerializedName("type")
        private int type;

        @SerializedName("chaincodeID")
        private ChaincodeId chaincodeId;

        @SerializedName("ctorMsg")
        private Payload payload;

        private static class ChaincodeId
        {
            @SerializedName("name")
            private String name;
        }

        private static class Payload
        {
            @SerializedName("args")
            private List<String> args;
        }
    }
}
