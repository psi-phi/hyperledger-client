package com.psiphiglobal.hyperledger_client.api;

public interface HyperledgerClient
{
    ChaincodeExecutor getChaincodeExecutor(String chaincodeName);
}
