package com.justa.springboot.db;

import java.util.Objects;

public class EnumUtil {
    
	public static <E extends Enum<E>> E toEnum(Class<E> enumClass, String dataStr)  {
		
		Objects.requireNonNull(enumClass, "The enumClass must be provided.");
		Objects.requireNonNull(dataStr, "The dataStr must be provided.");
		
		for(E one: enumClass.getEnumConstants()){
			if(one.name().equals(dataStr)){
				return one;
			}
		}
		return null;

	}
	
	public static <E extends Enum<E> & PersistableEnum<E>> E toPersistableEnum(Class<E> enumClass, String dataStr)  {
		
		Objects.requireNonNull(enumClass, "The enumClass must be provided.");
		Objects.requireNonNull(dataStr, "The dataStr must be provided.");
		
		for(E one: enumClass.getEnumConstants()){
			if(one.getValue().equals(dataStr)){
				return one;
			}
		}
		return null;

	}
}
