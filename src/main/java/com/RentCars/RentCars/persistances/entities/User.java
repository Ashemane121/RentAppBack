package com.RentCars.RentCars.persistances.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String firstname;
  @Column
  private String lastname;
  @Column
  private String email;
  @Column
  private String password;

  @Column
  private Integer phone;

  @Column
  private String address;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column
  private String profile_picture;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Post> posts;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Request> requests;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Identity> identities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
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
