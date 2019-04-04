package com.linxu.microapp.dtos;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/4/5
 */
@Data
@Service
public class WxAuthMsg {
    private String openid;
    private String session_key;
    private String unionid;
}
