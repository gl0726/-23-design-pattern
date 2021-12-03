package org.example.design.creative.build.complete;

/**
 *  车库房子建造者
 *
 * Author: GL
 * Date: 2021-12-03
 */
public class GarageHouseBuilder extends HouseBuilder {

    @Override
    public HouseBuilder windows(int windows) {
        super.getHouse().setWindows(windows);
        return this;
    }

    @Override
    public HouseBuilder doors(int doors) {
        super.getHouse().setDoors(doors);
        return this;
    }

    @Override
    public HouseBuilder rooms(int rooms) {
        super.getHouse().setRooms(rooms);
        return this;
    }

    // 此处不可写死为true，假设此处的hasGarage变量不是boolean类型而是车库面积int类型，所以此处仍需要传参
    @Override
    public HouseBuilder hasGarage(boolean hasGarage) {
        super.getHouse().setHasGarage(hasGarage);
        return this;
    }

    // 下面两个函数为空
    @Override
    public HouseBuilder hasSwimPool(boolean hasSwimPool) {
        return this;
    }

    @Override
    public HouseBuilder hasGarden(boolean hasGarden) {
        return this;
    }

}
