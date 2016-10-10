package com.ciengine.master.dao;

import com.ciengine.master.model.BuildModel;
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

	public BuildModel getNextToBuild()
	{
		Query query = openSession().createQuery("from build as o where o.Status='QUEUED' order by o.startTimestamp asc");
		query.setFirstResult(0);
		query.setMaxResults(1);
		List result = query.getResultList();
		if (result.size() > 0) {
			return (BuildModel) result.get(0);
		}
		return null;
	}
	public List<BuildModel> getNextBuildsInProgress()
	{
		Query query = openSession().createQuery("from build as o where o.Status='IN PROGRESS'");
//		query.setFirstResult(0);
//		query.setMaxResults(1);
		List result = query.getResultList();
		return result;
	}

	public List<BuildModel> find(String moduleName, String branchName, String executionList, String dockerImageId, String inputParams) {
		Query query = openSession().createQuery("from build as o" +
				" where o.moduleName=:moduleName" +
				" AND o.branchName=:branchName" +
				" AND o.executionList=:executionList" +
				" AND o.dockerImageId=:dockerImageId" +
//				" AND o.inputParams=:inputParams" +
				" ORDER BY o.startTimestamp DESC")
				.setParameter( "moduleName", moduleName )
				.setParameter( "branchName", branchName )
				.setParameter( "executionList", executionList )
				.setParameter( "dockerImageId", dockerImageId );
				//.setParameter( "inputParams", inputParams ); // TODO use hash?
//		query.setFirstResult(0);
//		query.setMaxResults(1);
		List result = query.getResultList();
		return result;
	}

	public BuildModel findByExternalId(String externalId) {
		Query query = openSession().createQuery("from build as o where o.externalId=:externalId")
				.setParameter( "externalId", externalId );
//		query.setFirstResult(0);
//		query.setMaxResults(1);
		return (BuildModel) query.getSingleResult();
	}
}
