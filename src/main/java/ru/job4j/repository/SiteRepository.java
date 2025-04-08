package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Site;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    Optional<Site> findByDomain(String domain);

    Optional<Site> findByLogin(String login);

    Boolean existsByDomain(String domain);

    Boolean existsByLogin(String login);

}