package com.iot.model;


public class DataKeeper
{
    private static Model modelInstance;
    private static ServiceData serviceInstance;

    public static Model getInstance()
    {
        if (modelInstance == null)   modelInstance = new Model();
        return modelInstance;
    }
    public static ServiceData getServiceInstance()
    {
        if (serviceInstance == null)    serviceInstance = new ServiceData();
        return serviceInstance;
    }

}
