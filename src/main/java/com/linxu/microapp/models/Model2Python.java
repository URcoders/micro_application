package com.linxu.microapp.models;

import com.linxu.microapp.exceptions.IllegalDataException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public static String resolve(Model2Python model2Python, Counter counter) throws IllegalDataException {
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
                if ("move_ahead".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.aheadIncre();
                } else if ("move_behind".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.behindIncre();
                } else if ("move_left".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.leftIncre();
                } else if ("move_right".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.rightIncre();
                } else if ("take_photo".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.take_photoIncre();
                } else if ("show_photo".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.show_photoIncre();
                } else if ("open_arm".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.open_armIncre();
                } else if ("close_arm".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.close_armIncre();
                } else if ("move_arm_high_up".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.move_high_upIncre();
                } else if ("move_arm_high_down".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.move_high_downIncre();
                } else if ("move_arm_low_up".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.move_low_upIncre();
                } else if ("move_arm_low_down".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.move_low_downIncre();
                } else if ("move_arm_left".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.move_arm_leftIncre();
                } else if ("move_arm_right".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.move_arm_rightIncre();
                } else if ("check_thing".equals(model2Python.getOps().substring(0, model2Python.getOps().indexOf("(")))) {
                    counter.checkIncre();
                }
                reT.append(model2Python.getOps());
                reT.append("\n");
            }
            if (model2Python.getChildren() != null) {
                for (Model2Python m : model2Python.getChildren()
                        ) {
                    reT.append(resolve(m, counter));
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
            stringBuilder.append("[]" + "}");
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

    public static String buildProgramingAdviceData(List<Behaviors> adviceList) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        sb.append("{ \" " + "adviceList\":[");
        for (Behaviors advice : adviceList) {
            sb.append("{\"avgs\":" + "\"" + advice.getWeight() + "\",");
            //可能查出空字符
            if (advice.getBehaviors() != null && !"".equals(advice.getBehaviors()))
                sb.append("\"advice\":" + advice.getBehaviors() + "},");
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
