package com.cse5382.assignment.Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cse5382.assignment.Model.UserEntry;

public class UserDetailsAdapter implements UserDetails {

    private UserEntry userEntry;

    public UserDetailsAdapter(UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
        List<String> role_names =  Arrays.asList(userEntry.getRoles().split(" "));
        for(String role : role_names) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return userEntry.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntry.getUsername();
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

    public UserEntry getUserEntry() {
        return userEntry;
    }
}
