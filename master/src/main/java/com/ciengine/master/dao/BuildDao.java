package com.ciengine.master.dao;

import com.ciengine.master.model.BuildModel;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * Created by emekhanikov on 13.09.2016.
 */
public class BuildDao extends AbstractDao<BuildModel>
{
	public List<BuildModel> getAll()
	{
		CriteriaBuilder builder = openSession().getCriteriaBuilder();

		CriteriaQuery<BuildModel> criteria = builder.createQuery( BuildModel.class );
		Root<BuildModel> root = criteria.from( BuildModel.class );
		criteria.select( root );
		//criteria.where( builder.equal( root.get( BuildModel_.name ), "John Doe" ) );

		List<BuildModel> persons = openSession().createQuery( criteria ).getResultList();
		return persons;
	}

}
