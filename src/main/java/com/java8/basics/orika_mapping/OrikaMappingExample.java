package com.java8.basics.orika_mapping;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.Date;

/**
 * @author neeraj on 2019-04-08
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class OrikaMappingExample {

    public static void main(String[] args) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(BasicPerson.class, BasicPersonDto.class)
                .field("name", "fullName")
                .field("age", "currentAge")
                .byDefault()
                .register();

        MapperFacade facade = mapperFactory.getMapperFacade();

        BasicPersonDto basicPersonDto = new BasicPersonDto("Neeraj Jain", 27, new Date());

        BasicPerson basicPerson = facade.map(basicPersonDto, BasicPerson.class);

        System.out.println(basicPerson);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        basicPerson.setBirthDate(new Date());
        basicPersonDto = facade.map(basicPerson, BasicPersonDto.class);

        System.out.println(basicPersonDto);

    }
}
