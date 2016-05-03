package com.izhbg.typz.sso.auth;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityUserAuth extends UserAuthDTO implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;


    public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities)
	{
		this.authorities = authorities;
	}

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    // ~ ==================================================
    public void setPermissions(List<Map<String, Object>> permissions) {
        super.setPermissions(permissions);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(getId());
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        setId((String) in.readObject());
    }

}
