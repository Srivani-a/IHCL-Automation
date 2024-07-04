package com.web.automation.dto.testretry;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class Test {
	
	public Test(String name) {
		super();
		this.name = name;
	}

	private String name;
	private int retryCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRetryCount() {
		return retryCount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + retryCount;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)){
			return false;
		}
		return (retryCount == other.retryCount);
	}
	
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	
	public void incremetCount(){
		retryCount++;
	}
	
	@Override
	public String toString() {
		return "Test [name=" + name + ", retryCount=" + retryCount + "]";
	}
}
