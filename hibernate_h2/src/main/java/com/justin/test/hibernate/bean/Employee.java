package com.justin.test.hibernate.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table
public class Employee extends _Base {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private Department department;

	public Employee() {
	}

	public Employee(String name, Department department) {
		this.name = name;
		this.department = department;
	}

	public Employee(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	

	private List<String> emails = Collections.emptyList();
	
	@ElementCollection
	@CollectionTable(name = "emp_emails",
    joinColumns = @JoinColumn(name = "emp_id"))
	@Column(name = "email")
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emailList) {
		this.emails = emailList;
	}
	
//	@CollectionOfElements(fetch=FetchType.EAGER) 
//	private List<String> emails;
//	
//	@IndexColumn(name="email_idx")
//	public List<String> getEmails() {
//		return emails;
//	}
//	public void setEmails(List<String> emailList) {
//		this.emails = emailList;
//	}
	
	
	@Override
	public String toString() {
		return "Employee [id=" + pk + ", name=" + name + ", department="
				+ department.getName() + "]";
	}
	

	private Map<Integer, String> certificates = new HashMap<Integer, String>();
	
	  // map from employee pk to certificate
    
    @ElementCollection
    @MapKeyColumn(name="cert_no")
    @Column(name="name")
    @CollectionTable(name="emp_certs", joinColumns=@JoinColumn(name="emp_pk"))
	public Map<Integer, String> getCertificates() {
		return certificates;
	}
	public void setCertificates(Map<Integer, String> certificates) {
		this.certificates = certificates;
	}
}