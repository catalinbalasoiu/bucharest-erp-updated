package com.bucharest.ag.dao;

import com.bucharest.ag.model.Function;

public interface FunctionDao {
	public Function getFunctionByType(String functionType);
	public Function getFunctionById(int functionId);
}
