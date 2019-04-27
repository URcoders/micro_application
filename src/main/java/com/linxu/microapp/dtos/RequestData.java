package com.linxu.microapp.dtos;

import com.linxu.microapp.models.Model2Python;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/4/27
 */
@Data
@Service
public class RequestData {
    private int id;
    private Model2Python program;
}
