package ru.job4j.userdetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Site;
import ru.job4j.repository.SiteRepository;

@Service
@AllArgsConstructor
public class SiteDetailsServiceImpl implements UserDetailsService {

    SiteRepository siteRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Site site = siteRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Site Not Found with login: " + login));
        return SiteDetailsImpl.build(site);
    }
}
