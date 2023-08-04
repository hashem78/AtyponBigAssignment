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
        @JsonSubTypes.Type(value = CreateUserCommand.class, name = "create_user"),
        @JsonSubTypes.Type(value = CreateRoleCommand.class, name = "create_role"),
        @JsonSubTypes.Type(value = AddUserToClassCommand.class, name = "add_user_to_class"),
        @JsonSubTypes.Type(value = CreateClassCommand.class, name = "create_class"),
        @JsonSubTypes.Type(value = RemoveUserCommand.class, name = "remove_user"),
        @JsonSubTypes.Type(value = RemoveUserFromClassCommand.class, name = "remove_user_from_class"),
})
public interface BasicCommand extends Command, BasicObject {

    @Override
    default Response accept(BasicObjectVisitor visitor) {
        return visitor.visit(this);
    }
    Response accept(BasicCommandVisitor visitor);
}
