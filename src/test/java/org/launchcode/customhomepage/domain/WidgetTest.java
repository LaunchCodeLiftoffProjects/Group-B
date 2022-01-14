package org.launchcode.customhomepage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.launchcode.customhomepage.web.rest.TestUtil;

class WidgetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Widget.class);
        Widget widget1 = new Widget();
        widget1.setId(1L);
        Widget widget2 = new Widget();
        widget2.setId(widget1.getId());
        assertThat(widget1).isEqualTo(widget2);
        widget2.setId(2L);
        assertThat(widget1).isNotEqualTo(widget2);
        widget1.setId(null);
        assertThat(widget1).isNotEqualTo(widget2);
    }
}
