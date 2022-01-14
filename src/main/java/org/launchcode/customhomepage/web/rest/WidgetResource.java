package org.launchcode.customhomepage.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.launchcode.customhomepage.domain.Widget;
import org.launchcode.customhomepage.repository.WidgetRepository;
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
 * REST controller for managing {@link org.launchcode.customhomepage.domain.Widget}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WidgetResource {

    private final Logger log = LoggerFactory.getLogger(WidgetResource.class);

    private static final String ENTITY_NAME = "widget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WidgetRepository widgetRepository;

    public WidgetResource(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    /**
     * {@code POST  /widgets} : Create a new widget.
     *
     * @param widget the widget to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new widget, or with status {@code 400 (Bad Request)} if the widget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/widgets")
    public ResponseEntity<Widget> createWidget(@RequestBody Widget widget) throws URISyntaxException {
        log.debug("REST request to save Widget : {}", widget);
        if (widget.getId() != null) {
            throw new BadRequestAlertException("A new widget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Widget result = widgetRepository.save(widget);
        return ResponseEntity
            .created(new URI("/api/widgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /widgets/:id} : Updates an existing widget.
     *
     * @param id the id of the widget to save.
     * @param widget the widget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated widget,
     * or with status {@code 400 (Bad Request)} if the widget is not valid,
     * or with status {@code 500 (Internal Server Error)} if the widget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/widgets/{id}")
    public ResponseEntity<Widget> updateWidget(@PathVariable(value = "id", required = false) final Long id, @RequestBody Widget widget)
        throws URISyntaxException {
        log.debug("REST request to update Widget : {}, {}", id, widget);
        if (widget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, widget.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!widgetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Widget result = widgetRepository.save(widget);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, widget.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /widgets/:id} : Partial updates given fields of an existing widget, field will ignore if it is null
     *
     * @param id the id of the widget to save.
     * @param widget the widget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated widget,
     * or with status {@code 400 (Bad Request)} if the widget is not valid,
     * or with status {@code 404 (Not Found)} if the widget is not found,
     * or with status {@code 500 (Internal Server Error)} if the widget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/widgets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Widget> partialUpdateWidget(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Widget widget
    ) throws URISyntaxException {
        log.debug("REST request to partial update Widget partially : {}, {}", id, widget);
        if (widget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, widget.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!widgetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Widget> result = widgetRepository
            .findById(widget.getId())
            .map(existingWidget -> {
                if (widget.getType() != null) {
                    existingWidget.setType(widget.getType());
                }
                if (widget.getProps() != null) {
                    existingWidget.setProps(widget.getProps());
                }

                return existingWidget;
            })
            .map(widgetRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, widget.getId().toString())
        );
    }

    /**
     * {@code GET  /widgets} : get all the widgets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of widgets in body.
     */
    @GetMapping("/widgets")
    public List<Widget> getAllWidgets() {
        log.debug("REST request to get all Widgets");
        return widgetRepository.findAll();
    }

    /**
     * {@code GET  /widgets/:id} : get the "id" widget.
     *
     * @param id the id of the widget to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the widget, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/widgets/{id}")
    public ResponseEntity<Widget> getWidget(@PathVariable Long id) {
        log.debug("REST request to get Widget : {}", id);
        Optional<Widget> widget = widgetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(widget);
    }

    /**
     * {@code DELETE  /widgets/:id} : delete the "id" widget.
     *
     * @param id the id of the widget to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/widgets/{id}")
    public ResponseEntity<Void> deleteWidget(@PathVariable Long id) {
        log.debug("REST request to delete Widget : {}", id);
        widgetRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
