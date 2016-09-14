package com.ciengine.master.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


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


}
