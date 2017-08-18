package scjp.enums;


public interface LookupById<E extends Enum<E>> {
	
	public int getId();
	public static <E extends Enum<E> & LookupById<E>> E lookupById2(Class<E> enumClass, Integer id){
			if(id == null) 	return null;

			for(E one: enumClass.getEnumConstants()){
				if(one.getId() == id){
					return one;
				}
			}
			return null;
		 
	 }

}