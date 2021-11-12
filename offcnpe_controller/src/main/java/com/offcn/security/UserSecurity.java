package com.offcn.security;

import com.offcn.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class UserSecurity implements UserDetails {
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 数据库角色信息
     */
    private List<Role> roleList;

    /**
     * 获取数据库查询出的用户角色信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       List<SimpleGrantedAuthority> authorities=new ArrayList<>();
       for(Role role:roleList){
           SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(role.getKeyword());
           authorities.add(simpleGrantedAuthority);
       }
        return authorities;
    }

    /**
     * 获取数据库密码
     * @return
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * 获取数据库用户名
     * @return
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * 该用户是否未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 该账户是否未上锁
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 认证是否未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 该账户是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
