package com.bycnit.socle.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bycnit.socle.dto.SearchCriteria;
import com.bycnit.socle.dto.UserDto;

/**
 * The user service interface
 *
 * @author S.BENDRIOUE
 */
public interface IUserService {

    /**
     * Gets the users in a paginated fashion
     *
     * @param criterias
     *            list of criterias
     * @param pageRequest
     *            contains page infos
     * @return a page of users
     */
    Page<UserDto> findPaginatedUsersByCriteria(List<SearchCriteria> criterias, Pageable pageRequest);

    /**
     * Returns the user with the id in param
     *
     * @param id
     *            user's id
     * @return user dto
     */
    UserDto findById(Long id);

    /**
     * Saves the user
     *
     * @param user
     *            the user to save
     * @return the Id of the saved uer
     */
    Long save(UserDto user);

    /**
     * Updates the User in params
     *
     * @param user
     *            the user to update
     */
    void update(UserDto user);

    /**
     * Changes the status of the user
     *
     * @param user
     *            the user to update
     */
    void updateUserStatus(UserDto user);

    /**
     * Deletes the user with the id in param
     *
     * @param id
     *            users'id
     */
    void delete(Long id);

}
