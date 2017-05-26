package fr.ebiz.nurdiales.trainingJava.model;

import java.util.List;

public class Page {
    private List<Computer> computers;
    private int quantity;

    public List<Computer> getComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
