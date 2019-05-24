package com.linxu.microapp.models;

import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/5/24
 */
@Data
@Service
public class Counter {
    private int id;
    private int ahead;
    private int behind;
    private int left;
    private int right;
    private int take_photo;
    private int show_photo;
    private int open_arm;
    private int close_arm;
    private int move_high_up;
    private int move_high_down;
    private int move_low_up;
    private int move_low_down;
    private int move_arm_left;
    private int move_arm_right;
    private int check_thing;
    //to fix up;
    public void aheadIncre(){
        this.ahead++;
    }
    public void behindIncre(){
        this.behind++;
    }
    public void leftIncre(){
        this.left++;
    }
    public void rightIncre(){
        this.right++;
    }
    public void take_photoIncre(){
        this.take_photo++;
    }
    public void show_photoIncre(){
        this.show_photo++;
    }
    public void open_armIncre(){
        this.open_arm++;
    }
    public void close_armIncre(){
        this.close_arm++;
    }
    public void move_high_upIncre(){
        this.move_high_up++;
    }
    public void move_high_downIncre(){
        this.move_high_down++;
    }
    public void move_low_upIncre(){
        this.move_low_up++;
    }
    public void move_low_downIncre(){
        this.move_low_down++;
    }
    public void move_arm_leftIncre(){
        this.move_arm_left++;
    }
    public void move_arm_rightIncre(){
        this.move_arm_right++;
    }
    public void checkIncre(){
        this.check_thing++;
    }

}
