package com.est.erp_api.models;

import com.est.erp_api.configurations.ApplicationContextProvider;
import com.est.erp_api.services.RoleService;
import com.est.erp_api.services.RoleServiceDefault;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_id;

    private String username;
    private String email;
    private String password;



    @Setter
    private RoleServiceDefault roleServiceDefault;

    public User() {
    }

    public User(String id, String username, String email, String password) {
        this.user_id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }


    // Not working, need to change to a workaround for a reactive architecture
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // -> The workaround
    public Flux<GrantedAuthority> getAuth(RoleService roleService) {
        return roleService.getRolesByUserId(this.user_id)
                .map(role -> new SimpleGrantedAuthority(role.getRole_name()));
    }






    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
