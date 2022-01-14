package org.launchcode.customhomepage.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.launchcode.customhomepage.domain.UserWidgets;
import org.launchcode.customhomepage.repository.UserWidgetsRepository;
import org.launchcode.customhomepage.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.launchcode.customhomepage.domain.UserWidgets}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserWidgetsResource {

    private final Logger log = LoggerFactory.getLogger(UserWidgetsResource.class);

    private static final String ENTITY_NAME = "userWidgets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserWidgetsRepository userWidgetsRepository;

    public UserWidgetsResource(UserWidgetsRepository userWidgetsRepository) {
        this.userWidgetsRepository = userWidgetsRepository;
    }

    /**
     * {@code POST  /user-widgets} : Create a new userWidgets.
     *
     * @param userWidgets the userWidgets to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userWidgets, or with status {@code 400 (Bad Request)} if the userWidgets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-widgets")
    public ResponseEntity<UserWidgets> createUserWidgets(@RequestBody UserWidgets userWidgets) throws URISyntaxException {
        log.debug("REST request to save UserWidgets : {}", userWidgets);
        if (userWidgets.getId() != null) {
            throw new BadRequestAlertException("A new userWidgets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserWidgets result = userWidgetsRepository.save(userWidgets);
        return ResponseEntity
            .created(new URI("/api/user-widgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-widgets/:id} : Updates an existing userWidgets.
     *
     * @param id the id of the userWidgets to save.
     * @param userWidgets the userWidgets to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWidgets,
     * or with status {@code 400 (Bad Request)} if the userWidgets is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userWidgets couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-widgets/{id}")
    public ResponseEntity<UserWidgets> updateUserWidgets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserWidgets userWidgets
    ) throws URISyntaxException {
        log.debug("REST request to update UserWidgets : {}, {}", id, userWidgets);
        if (userWidgets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userWidgets.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userWidgetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserWidgets result = userWidgetsRepository.save(userWidgets);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userWidgets.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-widgets/:id} : Partial updates given fields of an existing userWidgets, field will ignore if it is null
     *
     * @param id the id of the userWidgets to save.
     * @param userWidgets the userWidgets to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWidgets,
     * or with status {@code 400 (Bad Request)} if the userWidgets is not valid,
     * or with status {@code 404 (Not Found)} if the userWidgets is not found,
     * or with status {@code 500 (Internal Server Error)} if the userWidgets couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-widgets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserWidgets> partialUpdateUserWidgets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserWidgets userWidgets
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserWidgets partially : {}, {}", id, userWidgets);
        if (userWidgets.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userWidgets.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userWidgetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserWidgets> result = userWidgetsRepository
            .findById(userWidgets.getId())
            .map(existingUserWidgets -> {
                return existingUserWidgets;
            })
            .map(userWidgetsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userWidgets.getId().toString())
        );
    }

    /**
     * {@code GET  /user-widgets} : get all the userWidgets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userWidgets in body.
     */
    @GetMapping("/user-widgets")
    public List<UserWidgets> getAllUserWidgets() {
        log.debug("REST request to get all UserWidgets");
        return userWidgetsRepository.findAll();
    }

    /**
     * {@code GET  /user-widgets/:id} : get the "id" userWidgets.
     *
     * @param id the id of the userWidgets to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userWidgets, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-widgets/{id}")
    public ResponseEntity<UserWidgets> getUserWidgets(@PathVariable Long id) {
        log.debug("REST request to get UserWidgets : {}", id);
        Optional<UserWidgets> userWidgets = userWidgetsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userWidgets);
    }

    /**
     * {@code DELETE  /user-widgets/:id} : delete the "id" userWidgets.
     *
     * @param id the id of the userWidgets to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-widgets/{id}")
    public ResponseEntity<Void> deleteUserWidgets(@PathVariable Long id) {
        log.debug("REST request to delete UserWidgets : {}", id);
        userWidgetsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
