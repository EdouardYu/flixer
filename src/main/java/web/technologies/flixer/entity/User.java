package web.technologies.flixer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user", schema = "public")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private Date birthday;
    @Transient
    private transient int age;
    private Boolean enabled;
    private BigDecimal amount;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "last_update")
    private Instant lastUpdate;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public int getAge() {
        Calendar now = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(this.birthday);

        return birthday.get(Calendar.YEAR) - now.get(Calendar.YEAR);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.getLabel()));
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
    public boolean isAccountNonExpired() { // est-ce que le compte a expiré
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() { // est-ce que le compte est bloqué
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() { // est-ce que les informations d'identification ont expiré
        return this.enabled;
    }

    @Override
    public boolean isEnabled() { // est-ce que le compte est actif
        return this.enabled;
    }
}