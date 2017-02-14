package com.psiphiglobal.hyperledger_client;

import com.psiphiglobal.hyperledger_client.api.ChaincodeExecutor;
import com.psiphiglobal.hyperledger_client.rpc.RpcClient;
import com.psiphiglobal.hyperledger_client.rpc.RpcRequest;
import com.psiphiglobal.hyperledger_client.rpc.RpcResponse;

public class ChaincodeExecutorImpl implements ChaincodeExecutor
{
    private final String chaincodeName;
    private final RpcClient rpcClient;

    public ChaincodeExecutorImpl(RpcClient rpcClient, String chaincodeName)
    {
        this.rpcClient = rpcClient;
        this.chaincodeName = chaincodeName;
    }

    @Override
    public String invoke(String functionName, String... args) throws ChaincodeExecutionException
    {
        try
        {
            RpcRequest request = new RpcRequest.Builder(RpcRequest.Type.INVOKE, chaincodeName, functionName)
                    .addParameter(args)
                    .build();
            RpcResponse response = rpcClient.execute(request);
            return response.getBody();
        }
        catch (Exception e)
        {
            throw new ChaincodeExecutionException(e.getMessage());
        }
    }

    @Override
    public String query(String functionName, String... args) throws ChaincodeExecutionException
    {
        try
        {
            RpcRequest request = new RpcRequest.Builder(RpcRequest.Type.QUERY, chaincodeName, functionName)
                    .addParameter(args)
                    .build();
            RpcResponse response = rpcClient.execute(request);
            return response.getBody();
        }
        catch (Exception e)
        {
            throw new ChaincodeExecutionException(e.getMessage());
        }
    }
}
