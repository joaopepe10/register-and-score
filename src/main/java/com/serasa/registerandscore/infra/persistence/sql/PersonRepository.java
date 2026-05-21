package com.serasa.registerandscore.infra.persistence.sql;

import com.serasa.registerandscore.infra.persistence.sql.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
}
