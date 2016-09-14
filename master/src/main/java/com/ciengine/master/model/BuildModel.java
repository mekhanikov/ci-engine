package com.ciengine.master.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Entity(name = "build")
public class BuildModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratorType()
	private int id;

	private String fistName;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFistName()
	{
		return fistName;
	}

	public void setFistName(String fistName)
	{
		this.fistName = fistName;
	}

	@Override public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BuildModel that = (BuildModel) o;

		if (id != that.id)
			return false;
		return fistName != null ? fistName.equals(that.fistName) : that.fistName == null;

	}

	@Override public int hashCode()
	{
		int result = id;
		result = 31 * result + (fistName != null ? fistName.hashCode() : 0);
		return result;
	}

	@Override public String toString()
	{
		return "BuildModel{" +
				"id=" + id +
				", fistName='" + fistName + '\'' +
				'}';
	}
}
