package com.ciengine.master.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Entity
public class BuildModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratorType()
	private int id;

	private String fistName;
}
