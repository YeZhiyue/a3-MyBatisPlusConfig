package com.example.springsecuritydemo.config.security.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author shuang.kou
 */

@Getter
public enum RoleEnum {
    USER("USER", "用户"),
    TEMP_USER("TEMP_USER", "临时用户"),
    MANAGER("MANAGER", "管理者"),
    ADMIN("ADMIN", "Admin");

    @JsonValue
    @EnumValue
    String name;
    String description;

    RoleEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
