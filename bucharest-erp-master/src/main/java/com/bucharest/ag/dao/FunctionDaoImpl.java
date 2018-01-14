package com.bucharest.ag.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.model.Function;

@Repository
@Transactional
public class FunctionDaoImpl implements FunctionDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	/***
	 * When the class is instantiated inside the Spring Context
	 */
	public FunctionDaoImpl() {
	}
	
	/**
	 * When the class us instantiated outside the Spring Context
	 * @param sessionFactory
	 */
	public FunctionDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Function getFunctionByType(String functionType) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Function WHERE type = :type");
		query.setParameter("type", functionType);
		//List<FunctionId> list = (List<FunctionId>) query.list();	
		Function function = (Function) query.uniqueResult();
		session.close();
		return function;
	}


	@Override
	public Function getFunctionById(int functionId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Function WHERE functionId = :function");
		query.setParameter("function", functionId);
		Function function = (Function) query.uniqueResult();
		return function;
	}
}
