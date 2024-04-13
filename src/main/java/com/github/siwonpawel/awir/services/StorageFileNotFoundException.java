package com.github.siwonpawel.awir.services;

public class StorageFileNotFoundException extends RuntimeException
{
    public StorageFileNotFoundException(Exception e)
    {
        super(e);
    }
}
