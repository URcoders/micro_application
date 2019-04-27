package com.linxu.microapp.models;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/4/27
 */
@Data
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.DEFAULT)
public class Behaviors {
    private int id;
    private String behaviors;
}
