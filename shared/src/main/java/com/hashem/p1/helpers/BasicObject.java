package com.hashem.p1.helpers;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.Response;
import com.hashem.p1.commands.BasicCommand;
import com.hashem.p1.queries.BasicQuery;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BasicQuery.class, name = "query"),
        @JsonSubTypes.Type(value = BasicCommand.class, name = "command"),
})
public interface BasicObject {
    Response accept(BasicObjectVisitor visitor);
}
