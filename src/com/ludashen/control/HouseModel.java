package com.ludashen.control;

/**
 * @description: 这是一个模型类，方便循环List集合
 * @author: 陆均琪
 * @Data: 2019-12-03 8:37
 */

import com.ludashen.hothl.House;
import javax.swing.*;
import java.util.List;

public class HouseModel extends  AbstractListModel {
    /**
     * @description:
     * HouseModel() 构造函数，把出入的集合变成全局变量
     * getSize()    获取 集合的总大小
     * getElementAt()   获取集合的对于值
     * @author: 陆均琪
     * @time: 2019-12-04 21:46
     */

    List<House> houses;
    public HouseModel(List<House> houses){
        this.houses=houses;
    }
    @Override
    public int getSize() {
        return houses.size();
    }
    @Override
    public Object getElementAt(int index) {
        return  houses.get(index) ;
    }
}
