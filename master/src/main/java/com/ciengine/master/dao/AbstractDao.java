package com.ciengine.master.dao;

import com.ciengine.master.model.BuildModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Transactional
public abstract class AbstractDao<E> {

	@Autowired
	protected SessionFactory sessionFactory;

	public void save(E e) {
		saveEntity(e);
	}

	private void saveEntity(E e) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(e);
		transaction.commit();
	}

	public Session openSession() {
		return sessionFactory.getCurrentSession();
	}

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
