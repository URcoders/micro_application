package com.linxu.microapp.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.linxu.microapp.models.Advice;
import com.linxu.microapp.models.User;
import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linxu
 * @date 2019/4/27
 */
@Data
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.DEFAULT)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapData {
    private User user;
    private Integer id;
    private List<Advice> adviceList;
}
