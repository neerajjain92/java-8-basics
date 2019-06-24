package com.java8.basics.orika_mapping;

import lombok.Data;

import java.util.Date;

/**
 * @author neeraj on 2019-04-08
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
@Data
public class BasicPersonDto {
    private String fullName;
    private int currentAge;
    private Date birthDate;

    public BasicPersonDto(String fullName, int currentAge, Date birthDate) {
        this.fullName = fullName;
        this.currentAge = currentAge;
        this.birthDate = birthDate;
    }
}
