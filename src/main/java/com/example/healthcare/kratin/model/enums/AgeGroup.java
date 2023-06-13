package com.example.healthcare.kratin.model.enums;

public enum AgeGroup {

	UNDER_18(0, 17),
    FROM_18_TO_50(18, 50),
    OVER_50(51, Integer.MAX_VALUE);
	
	private final int minAge;
    private final int maxAge;

    AgeGroup(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
    
    public static AgeGroup getAgeGroup(int age) {
    	if(age>=0 && age <= 17) {
    		return UNDER_18;
    	}
    	else if(age >= 18 && age <= 50) {
    		return FROM_18_TO_50;
    	}
		return OVER_50;
    	
    }
}
