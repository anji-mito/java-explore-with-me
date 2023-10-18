package ru.practicum.ewm.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.compilation.model.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query("SELECT c FROM Compilation AS c WHERE (COALESCE(:pinned, c.pinned) = c.pinned)")
    Page<Compilation> findAllFilterBy(@Param("pinned") Boolean pinned, Pageable pageable);
}