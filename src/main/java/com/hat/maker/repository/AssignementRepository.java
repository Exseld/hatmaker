package com.hat.maker.repository;

import com.hat.maker.model.Assignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignementRepository extends JpaRepository<Assignement, Long>  {
}
