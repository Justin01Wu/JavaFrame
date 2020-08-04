package com.justa.springboot.db;

import javax.persistence.AttributeConverter;

// from https://stackoverflow.com/questions/23564506/is-it-possible-to-write-a-generic-enum-converter-for-jpa
public abstract  class GenericEnumUppercaseConverter <E extends Enum<E>> implements AttributeConverter<E, String> {

}
