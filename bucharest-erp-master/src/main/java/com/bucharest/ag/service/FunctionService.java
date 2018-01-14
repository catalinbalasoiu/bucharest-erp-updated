package com.bucharest.ag.service;

import com.bucharest.ag.model.Function;

public interface FunctionService {
	public Function getFunctionByType(String functionType);

	public Function getFunctionById(int functionId);
}
