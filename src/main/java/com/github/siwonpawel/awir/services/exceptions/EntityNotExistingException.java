package com.github.siwonpawel.awir.services.exceptions;

public class EntityNotExistingException extends RuntimeException
{
    private final Long id;

    public EntityNotExistingException(Long id)
    {
        this.id = id;
    }

    @Override
    public String getMessage()
    {
        return "User with given id does not exists [ %s ]".formatted(id);
    }
}
