package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ParticipantEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ParticipantEntry entity.
 */
@Repository
public interface ParticipantEntryRepository extends JpaRepository<ParticipantEntry, Long> {

    @Query(value = "select distinct participantEntry from ParticipantEntry participantEntry left join fetch participantEntry.sessionCodes",
        countQuery = "select count(distinct participantEntry) from ParticipantEntry participantEntry")
    Page<ParticipantEntry> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct participantEntry from ParticipantEntry participantEntry left join fetch participantEntry.sessionCodes")
    List<ParticipantEntry> findAllWithEagerRelationships();

    @Query("select participantEntry from ParticipantEntry participantEntry left join fetch participantEntry.sessionCodes where participantEntry.id =:id")
    Optional<ParticipantEntry> findOneWithEagerRelationships(@Param("id") Long id);

}
