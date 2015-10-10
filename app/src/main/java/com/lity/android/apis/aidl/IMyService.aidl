package com.lity.android.apis.aidl;

import java.util.List;

interface IMyService {

	void savePersonInfo(String name);
	List<String> getAllPersons();
}
