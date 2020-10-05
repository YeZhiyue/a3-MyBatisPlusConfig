package com.example.springsecuritydemo.utils.mybatisplus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 叶之越
 * Description
 * Date 2020/10/1
 * Time 8:51
 * Mail 739153436@qq.com
 */
@Data
@AllArgsConstructor
public class MyCodeConfig {
    private String key;
    private String value;
    private String serviceName;
    private String mapperName;
}
