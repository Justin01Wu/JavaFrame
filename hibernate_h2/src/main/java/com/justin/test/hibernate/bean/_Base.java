package com.justin.test.hibernate.bean;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@MappedSuperclass
public abstract class _Base implements java.io.Serializable {	

	private static Logger log = LoggerFactory.getLogger(_Base.class);
	
	private static final long serialVersionUID = -2850861829678496203L;
	
	@Transient
	protected Long pk;
	
	@Transient
	protected Date updatedTime;

	@Transient
	protected Date createdTime;

	@Transient
	protected Long version = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pk")
	public Long getPk() {
		return this.pk;
	}
	public void setPk(Long pk) {
		this.pk = pk;
	}
	public void copyPk(_Base that) {
		if( that != null )
			this.pk = that.pk;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatedTime() {
		return this.updatedTime;
	}
	private void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTime() {
		return this.createdTime;
	}
	private void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@PrePersist
	public void prePersit() {
		if (createdTime == null || updatedTime == null) {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			setCreatedTime(new Timestamp(cal.getTimeInMillis()));
			setUpdatedTime(new Timestamp(cal.getTimeInMillis()));
		}
	}

	@Transient
	public void updated() {
		preUpdate();
	}

	@PreUpdate
	public void preUpdate() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		setUpdatedTime(new Timestamp(cal.getTimeInMillis()));
	}

	@PostPersist
	public void postPersit() {
		if (log.isTraceEnabled())
			log.trace("Created " + this.toString());
	}

	@PostUpdate
	public void postUpdate() {
		if (log.isTraceEnabled())
			log.trace(String.format("Updated %s. Version: %d", this.toString(), version));
	}

	@PostRemove
	public void logRemove() {
		if (log.isTraceEnabled())
			log.trace("Removed " + this.toString());
	}
	
	@Override
	public String toString() {
		// Default implementation. Sub class should provide meaningful overriding
		return this.getClass().getSimpleName() + "[pk=" + pk + "]";
	}

	/**
	 * This is for optimistic locking
	 * 
	 * @return
	 */
	@Version
	public Long getVersion() {
		return version;
	}
	@SuppressWarnings("unused")
	private void setVersion(Long version) {
		this.version = version;
	}
	
	
	@Override
	public boolean equals(Object aThat) {
		// see http://brandon.fuller.name/archives/2009/03/17/16.37.41/
		if (this == aThat) {
			return true;
		}
		if (aThat == null || pk == null) {
			return false;
		}
		
		if (!getClass().isAssignableFrom(aThat.getClass()))	{
			if (!aThat.getClass().isAssignableFrom(getClass()))	{
				return false;
			}			
		}

		_Base that = (_Base) aThat;

		return pk.equals(that.getPk());
	}
	
	@Override
	public int hashCode() {
		if (pk != null)
			return pk.intValue();
		else
			return getClass().getName().hashCode();
	}

}
