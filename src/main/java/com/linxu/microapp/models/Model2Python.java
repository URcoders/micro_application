package com.linxu.microapp.models;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/4/5
 */
@Data
@Service
public class Model2Python {
    private String type;
    private String tab;
    private String ops;
    private String condition;
    private Model2Python children[];

    public static String resolve(Model2Python model2Python) {
        StringBuilder reT = new StringBuilder();
        if (model2Python.getType() != null && !model2Python.getType().equals("")) {
            int tab = Integer.valueOf(model2Python.getTab());
            for (int i = 0; i < tab; i++) {
                reT.append("\t");
            }
            reT.append(model2Python.getType());
            reT.append(" ");
            reT.append(model2Python.getCondition());
            reT.append(" :");
            reT.append("\n");
        }
        if (model2Python.getOps() != null && !model2Python.getOps().equals("")) {
            int tab = Integer.valueOf(model2Python.getTab());
            for (int i = 0; i < tab; i++) {
                reT.append("\t");
            }
            reT.append(model2Python.getOps());
            reT.append("\n");
        }
        if (model2Python.getChildren() != null) {
            for (Model2Python m : model2Python.getChildren()
                    ) {
                reT.append(resolve(m));
            }
        }
        return reT.toString();
    }
}
