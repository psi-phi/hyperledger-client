package com.psiphiglobal.hyperledger_client;

import com.google.gson.Gson;
import com.psiphiglobal.hyperledger_client.api.ChaincodeExecutor;
import com.psiphiglobal.hyperledger_client.api.HyperledgerClient;
import com.psiphiglobal.hyperledger_client.rpc.RpcClient;
import okhttp3.OkHttpClient;

public class HyperledgerClientImpl implements HyperledgerClient
{
    private final String host;
    private final int port;
    private OkHttpClient httpClient;
    private Gson gson;

    public HyperledgerClientImpl(String host, int port, OkHttpClient httpClient, Gson gson)
    {
        this.host = host;
        this.port = port;
        this.httpClient = httpClient;
        this.gson = gson;
    }

    @Override
    public ChaincodeExecutor getChaincodeExecutor(String chaincodeName)
    {
        RpcClient rpcClient = new RpcClient(httpClient, gson, getChaincodeUrl());
        return new ChaincodeExecutorImpl(rpcClient, chaincodeName);
    }

    private String getBaseUrl()
    {
        return "http://" + host + ":" + port + "/";
    }

    private String getChaincodeUrl()
    {
        return getBaseUrl() + "chaincode";
    }
}
