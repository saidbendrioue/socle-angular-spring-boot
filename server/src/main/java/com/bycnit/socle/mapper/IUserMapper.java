package com.bycnit.socle.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import com.bycnit.socle.domain.User;
import com.bycnit.socle.dto.UserDto;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IUserMapper {

    /**
     * Converts a user entity to a DTO
     *
     * @param user
     *            a user
     * @return a dto
     */
    UserDto asUserDto(User user);

    /**
     * Converts a user DTO to an entity
     *
     * @param user
     *            a user
     * @return an entity
     */
    User asUser(UserDto user);
    /**
     * update user from userDto
     *
     * @param UserDto
     *            target
     * @param User
     * 			source
     */ 	void updateUserFromUserDto(UserDto user, @MappingTarget User entity);
}
