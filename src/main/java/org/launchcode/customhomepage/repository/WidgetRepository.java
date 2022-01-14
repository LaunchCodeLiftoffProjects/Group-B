package org.launchcode.customhomepage.repository;

import org.launchcode.customhomepage.domain.Widget;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Widget entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WidgetRepository extends JpaRepository<Widget, Long> {}
