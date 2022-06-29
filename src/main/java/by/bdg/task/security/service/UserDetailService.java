package by.bdg.task.security.service;

import by.bdg.task.security.model.User;
import by.bdg.task.security.repository.UserSecurityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("jpaUserDetailsService")
@Repository
@Transactional
public class UserDetailService implements UserDetailsService {

    private final UserSecurityRepository userSecurityRepository;

    public UserDetailService(UserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userSecurityRepository.findByLogin(userName);

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> grantedAuthorityRoles = new HashSet<GrantedAuthority>();
        grantedAuthorityRoles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(grantedAuthorityRoles);
        return grantedAuthorities;
    }
}