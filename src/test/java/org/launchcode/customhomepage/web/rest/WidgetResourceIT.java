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
import org.launchcode.customhomepage.domain.Widget;
import org.launchcode.customhomepage.repository.WidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WidgetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WidgetResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PROPS = "AAAAAAAAAA";
    private static final String UPDATED_PROPS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/widgets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WidgetRepository widgetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWidgetMockMvc;

    private Widget widget;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Widget createEntity(EntityManager em) {
        Widget widget = new Widget().type(DEFAULT_TYPE).props(DEFAULT_PROPS);
        return widget;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Widget createUpdatedEntity(EntityManager em) {
        Widget widget = new Widget().type(UPDATED_TYPE).props(UPDATED_PROPS);
        return widget;
    }

    @BeforeEach
    public void initTest() {
        widget = createEntity(em);
    }

    @Test
    @Transactional
    void createWidget() throws Exception {
        int databaseSizeBeforeCreate = widgetRepository.findAll().size();
        // Create the Widget
        restWidgetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isCreated());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeCreate + 1);
        Widget testWidget = widgetList.get(widgetList.size() - 1);
        assertThat(testWidget.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWidget.getProps()).isEqualTo(DEFAULT_PROPS);
    }

    @Test
    @Transactional
    void createWidgetWithExistingId() throws Exception {
        // Create the Widget with an existing ID
        widget.setId(1L);

        int databaseSizeBeforeCreate = widgetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWidgetMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWidgets() throws Exception {
        // Initialize the database
        widgetRepository.saveAndFlush(widget);

        // Get all the widgetList
        restWidgetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(widget.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].props").value(hasItem(DEFAULT_PROPS)));
    }

    @Test
    @Transactional
    void getWidget() throws Exception {
        // Initialize the database
        widgetRepository.saveAndFlush(widget);

        // Get the widget
        restWidgetMockMvc
            .perform(get(ENTITY_API_URL_ID, widget.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(widget.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.props").value(DEFAULT_PROPS));
    }

    @Test
    @Transactional
    void getNonExistingWidget() throws Exception {
        // Get the widget
        restWidgetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWidget() throws Exception {
        // Initialize the database
        widgetRepository.saveAndFlush(widget);

        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();

        // Update the widget
        Widget updatedWidget = widgetRepository.findById(widget.getId()).get();
        // Disconnect from session so that the updates on updatedWidget are not directly saved in db
        em.detach(updatedWidget);
        updatedWidget.type(UPDATED_TYPE).props(UPDATED_PROPS);

        restWidgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWidget.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWidget))
            )
            .andExpect(status().isOk());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
        Widget testWidget = widgetList.get(widgetList.size() - 1);
        assertThat(testWidget.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWidget.getProps()).isEqualTo(UPDATED_PROPS);
    }

    @Test
    @Transactional
    void putNonExistingWidget() throws Exception {
        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();
        widget.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWidgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, widget.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWidget() throws Exception {
        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();
        widget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWidgetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWidget() throws Exception {
        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();
        widget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWidgetMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWidgetWithPatch() throws Exception {
        // Initialize the database
        widgetRepository.saveAndFlush(widget);

        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();

        // Update the widget using partial update
        Widget partialUpdatedWidget = new Widget();
        partialUpdatedWidget.setId(widget.getId());

        partialUpdatedWidget.props(UPDATED_PROPS);

        restWidgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWidget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWidget))
            )
            .andExpect(status().isOk());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
        Widget testWidget = widgetList.get(widgetList.size() - 1);
        assertThat(testWidget.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWidget.getProps()).isEqualTo(UPDATED_PROPS);
    }

    @Test
    @Transactional
    void fullUpdateWidgetWithPatch() throws Exception {
        // Initialize the database
        widgetRepository.saveAndFlush(widget);

        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();

        // Update the widget using partial update
        Widget partialUpdatedWidget = new Widget();
        partialUpdatedWidget.setId(widget.getId());

        partialUpdatedWidget.type(UPDATED_TYPE).props(UPDATED_PROPS);

        restWidgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWidget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWidget))
            )
            .andExpect(status().isOk());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
        Widget testWidget = widgetList.get(widgetList.size() - 1);
        assertThat(testWidget.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWidget.getProps()).isEqualTo(UPDATED_PROPS);
    }

    @Test
    @Transactional
    void patchNonExistingWidget() throws Exception {
        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();
        widget.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWidgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, widget.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWidget() throws Exception {
        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();
        widget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWidgetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isBadRequest());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWidget() throws Exception {
        int databaseSizeBeforeUpdate = widgetRepository.findAll().size();
        widget.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWidgetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(widget))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Widget in the database
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWidget() throws Exception {
        // Initialize the database
        widgetRepository.saveAndFlush(widget);

        int databaseSizeBeforeDelete = widgetRepository.findAll().size();

        // Delete the widget
        restWidgetMockMvc
            .perform(delete(ENTITY_API_URL_ID, widget.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Widget> widgetList = widgetRepository.findAll();
        assertThat(widgetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
