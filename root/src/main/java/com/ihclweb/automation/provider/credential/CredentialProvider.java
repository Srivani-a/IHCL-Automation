package com.ihclweb.automation.provider.credential;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.ihclweb.automation.dto.credentials.User;
import com.ihclweb.automation.repo.ExcelRepository;
import com.ihclweb.automation.util.Sheet;
import com.ihclweb.automation.util.exceptions.AccountException;
import com.ihclweb.automation.util.exceptions.AccountNotFoundException;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class CredentialProvider {

	private static ExcelRepository repository = null;
	private static final String SHEET_NAME = Sheet.Credentials.class.getSimpleName();
	
	private CredentialProvider() {}
	
	public static User getUser(String uid){
		User user = null;
		if(StringUtils.isNotBlank(uid)){
			if(Objects.isNull(repository)) {
				repository = ResourceProvider.instance().getRepository();
			}
			user = prepareUser(uid, getUIDRowID(uid));
			}else{
			throw new AccountException("UserID/ID cannot be null/empty to fetch User information.");
		}
		return user;
	}
	
	private static User prepareUser(String uid, int rid){
		User user = new User(uid, getName(rid), getType(rid),getEmail(rid), getMobileno(rid), getPassword(rid));
		user.setLocation(SHEET_NAME +"|"+rid+"|"+Sheet.Credentials.ID+"|"+uid);
		return user;
	}
	
	private static int getUIDRowID(String id){
		try{
			return repository.getRowIDOnUniqueValue(SHEET_NAME, id, Sheet.Credentials.ID);
		}catch(Exception e){
			throw new AccountNotFoundException("Account Not Found with UserID:"+id,e);
		}
	} 
	
	private static String getType(int rid){
		return repository.readStringFrom(SHEET_NAME, Sheet.Credentials.TYPE, rid);
	}
	
	private static String getMobileno(int rid){
		return repository.readStringFrom(SHEET_NAME, Sheet.Credentials.USERNAME, rid);
	}
	
	private static String getEmail(int rid){
		return repository.readStringFrom(SHEET_NAME, Sheet.Credentials.USERNAME, rid);
	}
	
	private static String getName(int rid){
		return repository.readStringFrom(SHEET_NAME, Sheet.Credentials.NAME, rid);
	}
	
	private static String getPassword(int rid){
		return repository.readStringFrom(SHEET_NAME, Sheet.Credentials.PASSWORD, rid);
	}
}
