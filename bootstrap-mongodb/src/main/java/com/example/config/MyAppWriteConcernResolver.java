package com.example.config;

import com.example.collection.Person;
import com.mongodb.WriteConcern;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.WriteConcernResolver;

public class MyAppWriteConcernResolver implements WriteConcernResolver {
    @Override
    public WriteConcern resolve(MongoAction action) {
//        if (action.getEntityClass().getSimpleName().contains("Audit")) {
//            return WriteConcern.NONE;
//        } else if (action.getEntityClass().getSimpleName().contains("Metadata")) {
//            return WriteConcern.JOURNAL_SAFE;
//        }

        if (action.getEntityType()== Person.class) {
            return WriteConcern.MAJORITY;
        }
        return action.getDefaultWriteConcern();
    }
}
