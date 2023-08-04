package com.hashem.p1.helpers;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.commands.Command;
import com.hashem.p1.queries.Query;
import com.hashem.p1.responses.Response;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Query.class, name = "query"),
        @JsonSubTypes.Type(value = Command.class, name = "command"),
})
public interface RootObject {
    Response accept(RootObjectVisitor visitor);
}
