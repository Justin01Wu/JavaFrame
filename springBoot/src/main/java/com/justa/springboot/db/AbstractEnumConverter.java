package com.justa.springboot.db;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//from https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa
@Converter
public abstract class AbstractEnumConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {
    private final Class<T> clazz;

    public AbstractEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        T[] enums = clazz.getEnumConstants();

        for (T e : enums) {
            if (e.getValue().equals(dbData)) {
                return e;
            }
        }

        throw new UnsupportedOperationException();
    }
}
