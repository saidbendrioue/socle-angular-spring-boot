package com.bycnit.socle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bycnit.socle.domain.User;
import com.bycnit.socle.dto.SearchCriteria;
import com.bycnit.socle.dto.UserDto;
import com.bycnit.socle.mapper.IUserMapper;
import com.bycnit.socle.repository.UserRepository;
import com.bycnit.socle.repository.specifications.UserSpecification;
import com.bycnit.socle.service.IUserService;
import com.bycnit.socle.service.exception.ElementNotFoundException;

/**
 * Service for user mangement
 *
 * @author S.BENDRIOUE
 */
@Service
@Transactional
public class UserService extends AbstractService<User> implements IUserService {

    /** User mapper */
    @Autowired
    private IUserMapper mapper;

    /** User repository */
    @Autowired
    private UserRepository repository;

    /**
     * @see com.bycnit.socle.service.IUserService#findPaginatedUsersByCriteria(java.util.List, org.springframework.data.domain.Pageable)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> findPaginatedUsersByCriteria(final List<SearchCriteria> criterias, final Pageable pageRequest) {

        final Page<User> page = repository.findAll(new UserSpecification(toSpecifications(criterias)), pageRequest);

        return page.map(mapper::asUserDto);
    }

    /**
     * @see com.bycnit.socle.service.IUserService#findById(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto findById(final Long id) {

        final Optional<User> user = repository.findById(id);

        if (user.isPresent()) {
            return mapper.asUserDto(user.get());
        } else {
            throw new ElementNotFoundException(User.class, id);
        }
    }

    /**
     * @see com.bycnit.socle.service.IUserService#save(com.bycnit.socle.dto.UserDto)
     */
    @Override
    public Long save(final UserDto user) {

        final User entity = mapper.asUser(user);
        
        entity.setActive(true);
        entity.setCreatedBy("S.BENDRIOUE");
        
        repository.save(entity);

        return entity.getId();
    }

    /**
     * @see com.bycnit.socle.service.IUserService#update(com.bycnit.socle.dto.UserDto)
     */
    @Override
    public void update(final UserDto user) {

        final Optional<User> saved = repository.findById(user.getId());

        if (saved.isPresent()) {
            final User entity = saved.get();

            mapper.updateUserFromUserDto(user, entity);

            repository.save(entity);
        } else {
            throw new ElementNotFoundException(User.class, user.getId());
        }
    }

    /**
     * @see com.bycnit.socle.service.IUserService#updateUserStatus(UserDto)
     */
    @Override
    public void updateUserStatus(final UserDto user) {

        final Optional<User> savedUser = repository.findById(user.getId());

        if (savedUser.isPresent()) {
            final User content = savedUser.get();

            content.setActive(user.isActive());
        } else {
            throw new ElementNotFoundException(User.class, user.getId());
        }
    }

    /**
     * @see com.bycnit.socle.service.IUserService#delete(java.lang.Long)
     */
    @Override
    public void delete(final Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ElementNotFoundException(User.class, id);
        }
    }

}
