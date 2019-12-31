package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ParticipantEntryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipantEntry.class);
        ParticipantEntry participantEntry1 = new ParticipantEntry();
        participantEntry1.setId(1L);
        ParticipantEntry participantEntry2 = new ParticipantEntry();
        participantEntry2.setId(participantEntry1.getId());
        assertThat(participantEntry1).isEqualTo(participantEntry2);
        participantEntry2.setId(2L);
        assertThat(participantEntry1).isNotEqualTo(participantEntry2);
        participantEntry1.setId(null);
        assertThat(participantEntry1).isNotEqualTo(participantEntry2);
    }
}
