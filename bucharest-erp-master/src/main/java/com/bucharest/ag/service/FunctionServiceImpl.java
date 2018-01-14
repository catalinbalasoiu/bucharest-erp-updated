package com.bucharest.ag.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bucharest.ag.context.ApplicationContextHolder;
import com.bucharest.ag.dao.FunctionDao;
import com.bucharest.ag.dao.FunctionDaoImpl;
import com.bucharest.ag.model.Function;

@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {
	private ApplicationContext applicationContext = ApplicationContextHolder
			.getContext();

	@Autowired
	FunctionDao functionDao;

	@Override
	public Function getFunctionByType(String functionType) {
		return functionDao.getFunctionByType(functionType);
	}

	@Override
	public Function getFunctionById(int functionId) {
		SessionFactory sessionFactory = (SessionFactory) applicationContext
				.getBean("sessionFactory");
		FunctionDao functionDao = new FunctionDaoImpl(sessionFactory);

		return functionDao.getFunctionById(functionId);
	}
}
