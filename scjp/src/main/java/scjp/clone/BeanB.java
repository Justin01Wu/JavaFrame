package scjp.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BeanB  extends BeanA {
	
	private Integer tId;

	public Integer getTId() {
		return tId;
	}

	public void setTId(Integer tId) {
		this.tId = tId;
	}
	
	@Override
	public BeanB clone() throws CloneNotSupportedException {
	    return (BeanB)super.clone();
	}
	
	public BeanB deepClone() throws IOException, ClassNotFoundException  {
		
		// comes from https://stackoverflow.com/questions/9737182/bug-in-using-object-clone
		// used Serializable to do deep copy
		
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        BeanB that = (BeanB) ois.readObject();
        return that;
        
        
	}
	

}
