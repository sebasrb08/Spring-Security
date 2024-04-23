package com.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.controller.dto.AuthCreateUserRequest;
import com.app.controller.dto.AuthLoginRequest;
import com.app.controller.dto.AuthResponse;
import com.app.persistence.entity.RolesEntity;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.RoleRepository;
import com.app.persistence.repository.UserRepository;
import com.app.util.JwtUtils;

@Service
public class UserDetailServiceImpl implements UserDetailsService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
        .orElseThrow(()-> new UsernameNotFoundException("El usuario "+username+" no existe"));

        List <SimpleGrantedAuthority> authorityList =new ArrayList<>();
        
        userEntity.getRole()
        .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name() ))));

        userEntity.getRole().stream()
        .flatMap(role -> role.getPermissions().stream())
        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getNombre())));

        return new User(userEntity.getUsername(),
        userEntity.getPassword(),
        userEntity.isEnabled(),
        userEntity.isAccountNoExpired(),
        userEntity.isCredentialNoExpired(),
        userEntity.isAccountNoLocked(),
        authorityList
        );

    }
    

    // se genera el token de acceso
    public AuthResponse loginUser(AuthLoginRequest authLoginRequest){

        String username=authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication =this.authenticated(username,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accesToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "user loged successfully", accesToken, true);

        return authResponse;
    }

    public Authentication authenticated( String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username o password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword()) ) {
            throw new BadCredentialsException("Invalid password");

        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),userDetails.getAuthorities());

    }


    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest){
        
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();

        List<String> roleRequest = authCreateUserRequest.authCreateRoleRequest().roleName();
        
        Set<RolesEntity> roleEntitySet = roleRepository.findRoleEntityByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The role specified not exist");
        }

        UserEntity userEntity = UserEntity.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .role(roleEntitySet)
        .isEnabled(true)
        .accountNoLocked(true)
        .accountNoExpired(true)
        .credentialNoExpired(true)
        .build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userSaved.getRole().stream().flatMap(role -> role.getPermissions().stream()).forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getNombre())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(username, "User created successfully", accessToken, true);
        return authResponse;

    }

}
