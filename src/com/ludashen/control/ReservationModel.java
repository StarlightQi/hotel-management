package com.ludashen.control;

import com.ludashen.hothl.Reservation;

import javax.swing.*;
import java.util.List;

/**
 * @description:订单模型
 * @author: 陆均琪
 * @Data: 2019-12-06 8:51
 */
public class ReservationModel extends AbstractListModel {

    List<Reservation> reservations;
    public ReservationModel(List<Reservation> reservations){
        this.reservations=reservations;
    }
    @Override
    public int getSize() {
        return reservations.size();
    }
    @Override
    public Object getElementAt(int index) {
        return  reservations.get(index) ;
    }
}
