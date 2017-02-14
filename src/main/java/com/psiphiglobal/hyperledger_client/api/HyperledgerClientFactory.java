package com.psiphiglobal.hyperledger_client.api;

import com.google.gson.Gson;
import com.psiphiglobal.hyperledger_client.HyperledgerClientImpl;
import okhttp3.OkHttpClient;

public class HyperledgerClientFactory
{
    public static HyperledgerClient getDefaultClient(String host, int port)
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new Gson();
        return new HyperledgerClientImpl(host, port, okHttpClient, gson);
    }
}
