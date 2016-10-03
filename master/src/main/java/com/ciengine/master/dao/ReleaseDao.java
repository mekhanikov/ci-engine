package com.ciengine.master.dao;

import com.ciengine.master.model.ReleaseModel;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * Created by emekhanikov on 13.09.2016.
 */
@Component
public class ReleaseDao extends AbstractDao<ReleaseModel>
{
	public List<ReleaseModel> getAll()
	{
		CriteriaBuilder builder = openSession().getCriteriaBuilder();

		CriteriaQuery<ReleaseModel> criteria = builder.createQuery( ReleaseModel.class );
		Root<ReleaseModel> root = criteria.from( ReleaseModel.class );
		criteria.select( root );

		List<ReleaseModel> persons = openSession().createQuery( criteria ).getResultList();
		return persons;
	}

	public ReleaseModel getNextToBuild()
	{
		Query query = openSession().createQuery("from build as o where o.Status='QUEUED' order by o.startTimestamp asc");
		query.setFirstResult(0);
		query.setMaxResults(1);
		List result = query.getResultList();
		if (result.size() > 0) {
			return (ReleaseModel) result.get(0);
		}
		return null;
	}
	public List<ReleaseModel> getNextBuildsInProgress()
	{
		Query query = openSession().createQuery("from build as o where o.Status='IN PROGRESS'");
//		query.setFirstResult(0);
//		query.setMaxResults(1);
		List result = query.getResultList();
		return result;
	}
}
