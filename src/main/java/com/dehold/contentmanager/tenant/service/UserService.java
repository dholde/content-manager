package com.dehold.contentmanager.tenant.service;

import com.dehold.contentmanager.tenant.model.User;
import com.dehold.contentmanager.tenant.web.dto.CreateUserRequest;
import com.dehold.contentmanager.tenant.web.dto.UpdateUserRequest;

import java.util.UUID;

public interface UserService {

    User createUser(CreateUserRequest dto);

    User getUser(UUID id);

    User updateUser(UUID id, UpdateUserRequest dto);

    void deleteUser(UUID id);
}
