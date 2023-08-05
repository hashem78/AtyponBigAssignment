package com.hashem.p1.responses;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.commands.CreateGradeCommand;
import com.hashem.p1.commands.grades.UpdateGradeCommand;
import com.hashem.p1.responses.classes.*;
import com.hashem.p1.responses.grades.DeleteGradeCommandResponse;
import com.hashem.p1.responses.grades.GetGradesForUserQueryResponse;
import com.hashem.p1.responses.grades.UpdateGradeCommandResponse;
import com.hashem.p1.responses.roles.CreateRoleCommandResponse;
import com.hashem.p1.responses.roles.GetRolesQueryResponse;
import com.hashem.p1.responses.users.GetUsersQueryResponse;
import com.hashem.p1.responses.roles.UpdateRoleCommandResponse;
import com.hashem.p1.responses.users.CreateUserCommandResponse;
import com.hashem.p1.responses.roles.DeleteRoleCommandResponse;
import com.hashem.p1.responses.users.DeleteUserCommandResponse;
import com.hashem.p1.responses.users.UpdateUserCommandResponse;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateUserCommandResponse.class, name = "create_user_response"),
        @JsonSubTypes.Type(value = GetUsersQueryResponse.class, name = "get_users_response"),
        @JsonSubTypes.Type(value = UpdateUserCommandResponse.class, name = "update_user_response"),
        @JsonSubTypes.Type(value = DeleteUserCommandResponse.class, name = "delete_user_response"),

        @JsonSubTypes.Type(value = CreateRoleCommandResponse.class, name = "create_role_response"),
        @JsonSubTypes.Type(value = GetRolesQueryResponse.class, name = "get_roles_response"),
        @JsonSubTypes.Type(value = UpdateRoleCommandResponse.class, name = "update_role_response"),
        @JsonSubTypes.Type(value = DeleteRoleCommandResponse.class, name = "delete_role_response"),

        @JsonSubTypes.Type(value = CreateClassCommandResponse.class, name = "create_class_response"),
        @JsonSubTypes.Type(value = GetClassesQueryResponse.class, name = "get_classes_response"),
        @JsonSubTypes.Type(value = UpdateClassCommandResponse.class, name = "update_class_response"),
        @JsonSubTypes.Type(value = DeleteClassCommandResponse.class, name = "delete_class_response"),
        @JsonSubTypes.Type(value = GetClassesForUserQueryResponse.class, name = "get_classes_for_user_response"),

        @JsonSubTypes.Type(value = CreateGradeCommand.class, name = "create_grade_response"),
        @JsonSubTypes.Type(value = GetGradesForUserQueryResponse.class, name = "get_grades_for_user_response"),
        @JsonSubTypes.Type(value = UpdateGradeCommandResponse.class, name = "update_grade_response"),
        @JsonSubTypes.Type(value = DeleteGradeCommandResponse.class, name = "delete_grade_response"),

        @JsonSubTypes.Type(value = ErrorResponse.class, name = "error_response")
})
public interface Response {
}
