package com.justa.springboot.db.convert1;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import com.justa.springboot.db.EnumUtil;
import com.justa.springboot.db.PersistableEnum;

@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, PersistableEnum<String>> {

	@Override
	public <T extends PersistableEnum<String>> Converter<String, T> getConverter(Class<T> targetType) {

		return new StringToEnumConverter(targetType);
	}
	
	private static class StringToEnumConverter<T extends Enum<T> & PersistableEnum<T>> implements Converter<String, T> {

      private Class<T> enumType;

      public StringToEnumConverter(Class<T> enumType) {
    	  
          this.enumType = enumType;
      }

      public T convert(String source) {
    	  return EnumUtil.toEnum(enumType, source);
      }
  }

}
