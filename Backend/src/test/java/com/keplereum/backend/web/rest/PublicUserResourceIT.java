package com.keplereum.backend.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.keplereum.backend.IntegrationTest;
import com.keplereum.backend.domain.User;
import com.keplereum.backend.repository.UserRepository;
import com.keplereum.backend.repository.search.UserSearchRepository;
import com.keplereum.backend.security.AuthoritiesConstants;
import com.keplereum.backend.service.UserService;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PublicUserResource} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
@IntegrationTest
class PublicUserResourceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * This repository is mocked in the com.keplereum.backend.repository.search test package.
     *
     * @see com.keplereum.backend.repository.search.UserSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserSearchRepository mockUserSearchRepository;

    @Autowired
    private MockMvc restUserMockMvc;

    private User user;
    private Long numberOfUsers;

    @BeforeEach
    void countUsers() {
        numberOfUsers = userRepository.count();
    }

    @BeforeEach
    void initTest() {
        user = UserResourceIT.initTestUser();
    }

    @AfterEach
    void cleanupAndCheck() {
        userService.deleteUser(user.getLogin());
        assertThat(userRepository.count()).isEqualTo(numberOfUsers);
        numberOfUsers = null;
    }

    @Test
    @Transactional
    void getAllPublicUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc
            .perform(get("/api/users?sort=id,desc").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[?(@.id == %d)].login", user.getId()).value(user.getLogin()))
            .andExpect(jsonPath("$.[?(@.id == %d)].keys()", user.getId()).value(Set.of("id", "login")))
            .andExpect(jsonPath("$.[*].email").doesNotHaveJsonPath())
            .andExpect(jsonPath("$.[*].imageUrl").doesNotHaveJsonPath())
            .andExpect(jsonPath("$.[*].langKey").doesNotHaveJsonPath());
    }

    @Test
    @Transactional
    void getAllUsersSortedByParameters() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        restUserMockMvc.perform(get("/api/users?sort=resetKey,desc").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        restUserMockMvc.perform(get("/api/users?sort=password,desc").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
        restUserMockMvc
            .perform(get("/api/users?sort=resetKey,desc&sort=id,desc").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
        restUserMockMvc.perform(get("/api/users?sort=id,desc").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
