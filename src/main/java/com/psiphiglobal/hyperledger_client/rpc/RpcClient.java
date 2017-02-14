package com.psiphiglobal.hyperledger_client.rpc;

import com.google.gson.Gson;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RpcClient
{
    private final Logger LOG = LogManager.getLogger();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient httpClient;
    private Gson gson;
    private String url;

    public RpcClient(OkHttpClient httpClient, Gson gson, String url)
    {
        this.httpClient = httpClient;
        this.gson = gson;
        this.url = url;
    }

    public RpcResponse execute(RpcRequest request) throws RpcException
    {
        String jsonRequest = gson.toJson(request);
        LOG.debug("[Hyperledger @ " + url + " ] <<<< " + jsonRequest);

        RequestBody body = RequestBody.create(JSON, jsonRequest);

        Request rpcRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = null;
        try
        {
            response = httpClient.newCall(rpcRequest).execute();
        }
        catch (IOException e)
        {
            LOG.error("[Hyperledger @ " + url + " ] >>>> Failed to execute RPC request", e);
            throw new RpcException("Failed to execute RPC request : " + e.getMessage());
        }

        if (response.code() != 200)
        {
            LOG.error("[Hyperledger @ " + url + " ] >>>> Response code is " + response.code());
            try
            {
                LOG.debug("[Hyperledger @ " + url + " ] >>>> " + response.body().string());
            }
            catch (IOException ignored)
            {
            }
            throw new RpcException("Response code is " + response.code());
        }

        try
        {
            String responseBody = response.body().string();
            RpcResponse rpcResponse = gson.fromJson(responseBody, RpcResponse.class);
            LOG.debug("[Hyperledger @ " + url + " ] >>>> " + responseBody);
            return rpcResponse;
        }
        catch (Exception e)
        {
            LOG.error("[Hyperledger @ " + url + " ] >>>> Unable to process response", e);
            throw new RpcException("Unable to process response : " + e.getMessage());
        }
    }
}
