package com.psiphiglobal.hyperledger_client.rpc;

import com.google.gson.annotations.SerializedName;

public class RpcResponse
{
    @SerializedName("id")
    private String requestId;

    @SerializedName("result")
    private Result result;

    public String getRequestId()
    {
        return requestId;
    }

    public String getStatus()
    {
        if (result != null)
            return result.status;
        else
            return null;
    }

    public String getBody()
    {
        if (result != null)
            return result.message;
        else
            return null;
    }

    private static class Result
    {
        @SerializedName("status")
        private String status;

        @SerializedName("message")
        private String message;
    }
}
