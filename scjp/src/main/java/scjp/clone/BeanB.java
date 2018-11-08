package scjp.clone;

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
	

}
