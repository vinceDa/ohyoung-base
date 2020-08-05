package com.ohyoung.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.security.domain.JwtUser;
import com.ohyoung.system.domain.User;
import com.ohyoung.system.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author vince
 * @date 2019/12/11 11:01
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "用户不存在"));
        }
        return this.createJwtUser(user);
    }

    public Collection<GrantedAuthority> mapToGrantedAuthorities(User user) {
        return user.getRoles().stream()
            .flatMap(role -> role.getMenus().stream().filter(single -> StrUtil.isNotEmpty(single.getPermissionTag())))
            .map(menu -> new SimpleGrantedAuthority(menu.getPermissionTag())).collect(Collectors.toList());
    }

    public UserDetails createJwtUser(User user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone(),
            user.getDepartment(), user.getJob(), this.mapToGrantedAuthorities(user), user.getEnable(),
            user.getGmtCreate(), user.getLastPasswordResetTime());
    }
}
