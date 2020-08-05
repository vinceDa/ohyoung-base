package com.ohyoung.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.system.domain.Menu;
import com.ohyoung.system.domain.Role;
import com.ohyoung.system.domain.User;
import com.ohyoung.system.domain.dto.UserDTO;
import com.ohyoung.system.domain.query.UserQueryCriteria;
import com.ohyoung.system.domain.vo.MenuVO;
import com.ohyoung.system.domain.vo.RoleVO;
import com.ohyoung.system.domain.vo.UserVO;
import com.ohyoung.system.repository.*;
import com.ohyoung.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author vince
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public List<UserDTO> listAll() {

        List<User> content = userRepository.findAll();
        log.info("listAll, size: {}", content.size());
        return Convert.convert(new TypeReference<List<UserDTO>>() {
        }, content);
    }

    @Override
    public Page<UserVO> listPageable(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return convertUserPage(users);
    }

    @Override
    public Page<UserVO> listPageable(Specification<UserQueryCriteria> specification, Pageable pageable) {
        Page<User> userPage = userRepository.findAll(specification, pageable);
        return convertUserPage(userPage);
    }

    private Page<UserVO> convertUserPage(Page<User> userPage) {
        List<User> users = userPage.getContent();
        List<UserVO> convert = Convert.convert(new TypeReference<List<UserVO>>() {}, users);
        log.info("listPageable, size: {}", users.size());
        return new PageImpl<>(convert, PageRequest.of(userPage.getNumber(), userPage.getSize()), userPage.getTotalElements());
    }

    @Override
    public UserVO getById(Long id) {
        User one = userRepository.getOne(id);
        log.info("getById, id: {}, res: {}", id, one.toString());
        Set<Role> roles = one.getRoles();
        Set<Menu> totalMenus = new HashSet<>();
        for (Role role : roles) {
            totalMenus.addAll(role.getMenus());
        }
        Set<MenuVO> menuVOS = Convert.convert(new TypeReference<Set<MenuVO>>() {
        }, totalMenus);
        UserVO convert = Convert.convert(UserVO.class, one);
        convert.setMenus(menuVOS);
        return convert;
    }

    @Override
    public User getByUsername(String username) {
        User byUsername = userRepository.getByUsername(username);
        log.info("getByUsername, username: {}, res: {}", username, byUsername.toString());
        return byUsername;
    }

    @Override
    public UserDTO insert(UserDTO userDTO) {
        User byUserName = userRepository.getByUsername(userDTO.getUsername());
        if (byUserName != null) {
            log.error("convert insert error: username is exist");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "用户名已存在"));
        }
        User convert = Convert.convert(User.class, userDTO);
        LocalDateTime now = LocalDateTime.now();
        //TODO 加密加盐
        convert.setPassword("123456");
        convert.setLastPasswordResetTime(now);
        convert.setAdmin(false);
        User save = userRepository.save(convert);
        log.info("insert, res: {}", save.toString());
        return Convert.convert(UserDTO.class, save);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            log.error("user delete error: unknown id");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "未知的id"));
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(userDTO.getId());
        if (!byId.isPresent()) {
            log.error("convert update error: unknown id");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "未知的id"));
        }
        if (StrUtil.isNotBlank(userDTO.getUsername())) {
            User byUsername = userRepository.getByUsername(userDTO.getUsername());
            if (byUsername != null && !byUsername.getId().equals(userDTO.getId())) {
                log.error("convert update error: username is exist");
                throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "用户名已存在"));
            }
        }
        User one = byId.get();
        User convert = Convert.convert(User.class, userDTO);
        // 只更新传入的部分字段
        CopyOptions copyOptions = new CopyOptions();
        copyOptions.ignoreNullValue();
        BeanUtil.copyProperties(convert, one, copyOptions);
        User save = userRepository.save(one);
        log.info("update, res: {}", save.toString());
        return Convert.convert(UserDTO.class, save);
    }

}
