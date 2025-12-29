package com.LocadoraFilmes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;

import com.LocadoraFilmes.Repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
  @Autowired  
 private  UsuarioRepository usuarioRepository;
 
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
}

}
