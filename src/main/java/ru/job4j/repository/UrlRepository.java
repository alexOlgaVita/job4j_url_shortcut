package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Site;
import ru.job4j.model.Url;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByUrl(String url);

    Optional<Url> findByCode(String code);

    Boolean existsByUrl(String url);

    Boolean existsByCode(String code);

    List<Url> findAllUrlBySite(Site site);
}