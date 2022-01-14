package org.launchcode.customhomepage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.launchcode.customhomepage.IntegrationTest;
import org.launchcode.customhomepage.domain.UserWidgets;
import org.launchcode.customhomepage.repository.UserWidgetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserWidgetsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserWidgetsResourceIT {

    private static final String ENTITY_API_URL = "/api/user-widgets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserWidgetsRepository userWidgetsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserWidgetsMockMvc;

    private UserWidgets userWidgets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWidgets createEntity(EntityManager em) {
        UserWidgets userWidgets = new UserWidgets();
        return userWidgets;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWidgets createUpdatedEntity(EntityManager em) {
        UserWidgets userWidgets = new UserWidgets();
        return userWidgets;
    }

    @BeforeEach
    public void initTest() {
        userWidgets = createEntity(em);
    }

    @Test
    @Transactional
    void createUserWidgets() throws Exception {
        int databaseSizeBeforeCreate = userWidgetsRepository.findAll().size();
        // Create the UserWidgets
        restUserWidgetsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isCreated());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeCreate + 1);
        UserWidgets testUserWidgets = userWidgetsList.get(userWidgetsList.size() - 1);
    }

    @Test
    @Transactional
    void createUserWidgetsWithExistingId() throws Exception {
        // Create the UserWidgets with an existing ID
        userWidgets.setId(1L);

        int databaseSizeBeforeCreate = userWidgetsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWidgetsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserWidgets() throws Exception {
        // Initialize the database
        userWidgetsRepository.saveAndFlush(userWidgets);

        // Get all the userWidgetsList
        restUserWidgetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWidgets.getId().intValue())));
    }

    @Test
    @Transactional
    void getUserWidgets() throws Exception {
        // Initialize the database
        userWidgetsRepository.saveAndFlush(userWidgets);

        // Get the userWidgets
        restUserWidgetsMockMvc
            .perform(get(ENTITY_API_URL_ID, userWidgets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userWidgets.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserWidgets() throws Exception {
        // Get the userWidgets
        restUserWidgetsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserWidgets() throws Exception {
        // Initialize the database
        userWidgetsRepository.saveAndFlush(userWidgets);

        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();

        // Update the userWidgets
        UserWidgets updatedUserWidgets = userWidgetsRepository.findById(userWidgets.getId()).get();
        // Disconnect from session so that the updates on updatedUserWidgets are not directly saved in db
        em.detach(updatedUserWidgets);

        restUserWidgetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserWidgets.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserWidgets))
            )
            .andExpect(status().isOk());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
        UserWidgets testUserWidgets = userWidgetsList.get(userWidgetsList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingUserWidgets() throws Exception {
        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();
        userWidgets.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWidgetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userWidgets.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserWidgets() throws Exception {
        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();
        userWidgets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWidgetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserWidgets() throws Exception {
        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();
        userWidgets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWidgetsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserWidgetsWithPatch() throws Exception {
        // Initialize the database
        userWidgetsRepository.saveAndFlush(userWidgets);

        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();

        // Update the userWidgets using partial update
        UserWidgets partialUpdatedUserWidgets = new UserWidgets();
        partialUpdatedUserWidgets.setId(userWidgets.getId());

        restUserWidgetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserWidgets.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserWidgets))
            )
            .andExpect(status().isOk());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
        UserWidgets testUserWidgets = userWidgetsList.get(userWidgetsList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateUserWidgetsWithPatch() throws Exception {
        // Initialize the database
        userWidgetsRepository.saveAndFlush(userWidgets);

        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();

        // Update the userWidgets using partial update
        UserWidgets partialUpdatedUserWidgets = new UserWidgets();
        partialUpdatedUserWidgets.setId(userWidgets.getId());

        restUserWidgetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserWidgets.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserWidgets))
            )
            .andExpect(status().isOk());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
        UserWidgets testUserWidgets = userWidgetsList.get(userWidgetsList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingUserWidgets() throws Exception {
        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();
        userWidgets.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWidgetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userWidgets.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserWidgets() throws Exception {
        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();
        userWidgets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWidgetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserWidgets() throws Exception {
        int databaseSizeBeforeUpdate = userWidgetsRepository.findAll().size();
        userWidgets.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserWidgetsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userWidgets))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserWidgets in the database
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserWidgets() throws Exception {
        // Initialize the database
        userWidgetsRepository.saveAndFlush(userWidgets);

        int databaseSizeBeforeDelete = userWidgetsRepository.findAll().size();

        // Delete the userWidgets
        restUserWidgetsMockMvc
            .perform(delete(ENTITY_API_URL_ID, userWidgets.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserWidgets> userWidgetsList = userWidgetsRepository.findAll();
        assertThat(userWidgetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
