package preCapstone.fuseable.dto.article;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        Long kakaoId,
        String kakaoProfileImg,
        Collection<? extends GrantedAuthority> authorities,
        String kakaoEmail,
        String kakaoNickname,
        String userRole
) implements UserDetails {

    public static BoardPrincipal of(Long kakaoId, String kakaoProfileImg, String kakaoEmail, String kakaoNickname, String userRole) {
        // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅한다.
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                kakaoId,
                kakaoProfileImg,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                kakaoEmail,
                kakaoNickname,
                userRole
        );
    }

    public static BoardPrincipal from(UserDto dto) {
        return BoardPrincipal.of(
                dto.kakaoId(),
                dto.kakaoProfileImg(),
                dto.kakaoEmail(),
                dto.kakaoNickname(),
                dto.userRole()
        );
    }

    public UserDto toDto() {
        return UserDto.of(
                kakaoId,
                kakaoProfileImg,
                kakaoEmail,
                kakaoNickname,
                userRole
        );
    }


    @Override public String getUsername() { return kakaoNickname; }
    @Override public String getPassword() { return "XXX"; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }


    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }

}
