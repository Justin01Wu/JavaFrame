package com.justa.springboot.db;

public interface PersistableEnum<T> {
    public T getValue();
    public Enum<?> getEnum(String shortName);
    
}
