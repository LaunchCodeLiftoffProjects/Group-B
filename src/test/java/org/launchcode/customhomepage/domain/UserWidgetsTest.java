package org.launchcode.customhomepage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.launchcode.customhomepage.web.rest.TestUtil;

class UserWidgetsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWidgets.class);
        UserWidgets userWidgets1 = new UserWidgets();
        userWidgets1.setId(1L);
        UserWidgets userWidgets2 = new UserWidgets();
        userWidgets2.setId(userWidgets1.getId());
        assertThat(userWidgets1).isEqualTo(userWidgets2);
        userWidgets2.setId(2L);
        assertThat(userWidgets1).isNotEqualTo(userWidgets2);
        userWidgets1.setId(null);
        assertThat(userWidgets1).isNotEqualTo(userWidgets2);
    }
}
