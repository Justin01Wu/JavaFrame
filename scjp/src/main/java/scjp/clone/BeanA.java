package scjp.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class BeanA implements Cloneable , Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private Integer code;
	private String name;
	private Date birthDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Override
	public BeanA clone() throws CloneNotSupportedException {
		BeanA that =  (BeanA)super.clone();
		that.name = this.name+ "-cloned";
		return that;
	}

	public BeanA deepClone() throws IOException, ClassNotFoundException  {
		
		// comes from https://stackoverflow.com/questions/9737182/bug-in-using-object-clone
		// used Serializable to do deep copy
		
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        BeanA that = (BeanA) ois.readObject();
        return that;
        
        
	}

}
