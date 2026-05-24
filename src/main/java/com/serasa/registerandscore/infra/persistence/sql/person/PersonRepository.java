package com.serasa.registerandscore.infra.persistence.sql.person;

import com.serasa.registerandscore.infra.persistence.sql.person.model.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    @Query("""
            SELECT p
            FROM PersonEntity p
            WHERE p.active = true
                  AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
                  AND (:zipCode IS NULL OR p.address.zipCode = :zipCode)
                  AND (CAST(:minDate AS date) IS NULL OR p.birthDate >= :minDate)
                  AND (CAST(:maxDate AS date) IS NULL OR p.birthDate <= :maxDate)
            """)
    Page<PersonEntity> findPersonsByFilters(
            @Nullable String name,
            @Nullable String zipCode,
            @Nullable LocalDate minDate,
            @Nullable LocalDate maxDate,
            Pageable pageable
    );
}
