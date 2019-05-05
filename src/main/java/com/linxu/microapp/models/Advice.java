package com.linxu.microapp.models;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/5/5
 */
@Data
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.DEFAULT)
public class Advice {
    //推荐程度
    private int avgs;
    //具体模块数据
    private String advice;
}
