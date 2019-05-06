package com.linxu.microapp.models;

import com.linxu.microapp.exceptions.IllegalDataException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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

    public static String resolve(Model2Python model2Python) throws IllegalDataException {
        StringBuilder reT = new StringBuilder();
        try {
            if (model2Python.getType() != null) {
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
            if (model2Python.getOps() != null) {
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
        } catch (IllegalDataException e) {
            throw new IllegalDataException("数据解析失败，模块数据格式错误~~");
        }
        return reT.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"type\":");
        if (type != null) {
            stringBuilder.append("\"" + type + "\"" + ",");
        } else {
            stringBuilder.append(type + ",");
        }
        stringBuilder.append("\"tab\":");
        if (tab != null) {
            stringBuilder.append("\"" + tab + "\"" + ",");
        } else {
            stringBuilder.append(tab + ",");
        }
        stringBuilder.append("\"ops\":");
        if (ops != null) {
            stringBuilder.append("\"" + ops + "\"" + ",");
        } else {
            stringBuilder.append(ops + ",");
        }
        stringBuilder.append("\"condition\":");
        if (condition != null) {
            stringBuilder.append("\"" + condition + "\"" + ",");
        } else {
            stringBuilder.append(condition + ",");
        }
        stringBuilder.append("\"children\":");
        if (children.length > 0) {
            stringBuilder.append(Arrays.toString(children) + "}");
        } else {
            stringBuilder.append(null + "}");
        }
        return stringBuilder.toString();
       /* return "{" +
                "\"type\":\"" + type + '\"' +
                ", \"tab\":\"" + tab + '\"' +
                ", \"ops\":\"" + ops + '\"' +
                ", \"condition\":\"" + condition + '\"' +
                ", \"children\":" + Arrays.toString(children) +
                '}';*/
    }

    public static String buildProgramingAdviceData(List<Advice> adviceList) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        sb.append("{ \" " + "adviceList\":[");
        for (Advice advice : adviceList) {
            sb.append("{\"avgs\":" + "\"" + advice.getAvgs() + "\",");
            //可能查出空字符
            if (advice.getAdvice() != null&&!"".equals(advice.getAdvice()))
                sb.append("\"advice\":" + advice.getAdvice() + "},");
            else
                sb.append("\"advice\":" + "null" + "},");
            flag = true;
        }
        if (flag) {
            int index = sb.lastIndexOf(",");
            sb.deleteCharAt(index);
        }
        sb.append("]}");
        return sb.toString();
    }
}
