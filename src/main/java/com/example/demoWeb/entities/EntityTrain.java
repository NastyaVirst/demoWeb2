package com.example.demoWeb.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Comparator;

@Schema(description = "Состав")
@Entity
@Table(name = "trains")
public class EntityTrain {
    @Schema(description = "Идентификатор записи")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = 0;

    @Schema(description = "Идентификатор пути", hidden = true)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_way")
    private EntityWay way;

    @Schema(description = "Идентификатор вагона")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_wagon")
    private EntityWagon wagon;


    @Schema(description = "Порядковый номер вагона на пути")
    @Column(name = "order_num", nullable = false)
    private long orderNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EntityWay getWay() {
        return way;
    }

    public void setWay(EntityWay way) {
        this.way = way;
    }

    public EntityWagon getWagon() {
        return wagon;
    }

    public void setWagon(EntityWagon wagon) {
        this.wagon = wagon;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }


    public static Comparator<EntityTrain> ByOrd = new Comparator<EntityTrain>() {

        public int compare(EntityTrain s1, EntityTrain s2) {

            int ord1 = (int)s1.getOrderNum();
            int ord2 = (int)s2.getOrderNum();
            return ord1 - ord2;

        }
    };
}
