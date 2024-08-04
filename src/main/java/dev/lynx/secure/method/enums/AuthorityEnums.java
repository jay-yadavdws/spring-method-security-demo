package dev.lynx.secure.method.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.List;

public enum AuthorityEnums {
    READ_AUTHORITY,
    WRITE_AUTHORITY,
    UPDATE_AUTHORITY,
    DELETE_AUTHORITY;

    public static List<GrantedAuthority> m1() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_A, ROLE_B");
    }

}
