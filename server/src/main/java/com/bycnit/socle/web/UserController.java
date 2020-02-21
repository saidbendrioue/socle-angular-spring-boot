package com.bycnit.socle.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bycnit.socle.constants.Authorization;
import com.bycnit.socle.constants.WebConstants;
import com.bycnit.socle.dto.SearchCriteria;
import com.bycnit.socle.dto.UserDto;
import com.bycnit.socle.service.IUserService;
import com.bycnit.socle.util.SearchCriteriaUtils;

/**
 * Controller for user management
 *
 * @author S.BENDRIOUE
 */
@RestController
@RequestMapping(WebConstants.USERS)
@PreAuthorize(Authorization.RH)
public class UserController {

    @Autowired
    private IUserService service;

    /**
     * Gets the users in a paginated fashion
     *
     * @param searchToken
     *            the search query
     * @param pageRequest
     *            contains infos about pagination and sorting
     * @return a page of users
     */
    @GetMapping
    public Page<UserDto> list(@RequestParam(value = "search", required = false) final String searchToken, final Pageable pageRequest) {

        // Constructing criterias from the search query
        final List<SearchCriteria> criterias = SearchCriteriaUtils.build(searchToken);

        return service.findPaginatedUsersByCriteria(criterias, pageRequest);
    }

    @PostMapping("/status")
    public void updateStatus(@RequestBody final UserDto user) {
        service.updateUserStatus(user);
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable("id") final Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Long store(@Valid @RequestBody final UserDto user) {

        if (user.getId() != null) {
            service.update(user);
            return user.getId();
        }

        return service.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") final Long id) {
        service.delete(id);
    }
}
