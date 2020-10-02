package com.example.mybatispluscodegeneral.entity.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TypeEnum {

    ONE("one"),
    TWO("two");

    @EnumValue
    private final String value;
}
