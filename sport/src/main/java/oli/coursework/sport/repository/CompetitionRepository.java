/*
 * Copyright by Stepan Oliinyk (c) 2018.
 */

package oli.coursework.sport.repository;

import oli.coursework.sport.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query("select c from Competition c where c.startDate between :firstDate and :secondDate")
    List<Competition> getAll(@Param("firstDate") Date firstDate, @Param("secondDate") Date secondDate);

    List<Competition>findCompetitionsBySportKind_IdAndGyms_GymId(Long sportKindId, Long gymId);

    List<Competition>findCompetitionsBySportKind_IdAndStadiums_StadiumId(Long sportKindId, Long stadiumId);

    List<Competition> findCompetitionsBySportKind_IdAndCourts_CourtId(Long sportKindId, Long courtId);

    List<Competition> findByOrganizer_OrganizerId(Long organizerId);

    List<Competition> findBySportKind_Id(Long sportKindId);
}