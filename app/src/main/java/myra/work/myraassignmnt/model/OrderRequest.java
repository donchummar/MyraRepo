package myra.work.myraassignmnt.model;

import myra.work.myraassignmnt.net.IDataModel;

/**
 * Created by don on 31/7/17.
 */

public class OrderRequest implements IDataModel {

    private int xCordinate;
    private int yCordinate;
    private String textOrder;


    public void setxCordinate(int xCordinate) {
        this.xCordinate = xCordinate;
    }

    public void setyCordinate(int yCordinate) {
        this.yCordinate = yCordinate;
    }

    public void setTextOrder(String textOrder) {
        this.textOrder = textOrder;
    }

    public int getxCordinate() {

        return xCordinate;
    }

    public int getyCordinate() {
        return yCordinate;
    }

    public String getTextOrder() {
        return textOrder;
    }

    @Override
    public IDataModel parseData(String data) {
        return null;
    }
}
