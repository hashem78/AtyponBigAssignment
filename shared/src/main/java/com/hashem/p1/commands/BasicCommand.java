package com.hashem.p1.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.Command;
import com.hashem.p1.Response;
import com.hashem.p1.helpers.BasicObject;
import com.hashem.p1.helpers.BasicObjectVisitor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddUserCommand.class, name = "add_user"),
        @JsonSubTypes.Type(value = AddUserToClassCommand.class, name = "add_user_to_class"),
        @JsonSubTypes.Type(value = CreateClassCommand.class, name = "create_class"),
        @JsonSubTypes.Type(value = RemoveUserCommand.class, name = "remove_user"),
        @JsonSubTypes.Type(value = RemoveUserFromClassCommand.class, name = "remove_user_from_class"),
})
public abstract class BasicCommand implements Command, BasicObject {
    BasicCommand() {

    }

    @Override
    public Response accept(BasicObjectVisitor visitor) {
        return visitor.visit(this);
    }

    public abstract Response accept(BasicCommandVisitor visitor);
}
