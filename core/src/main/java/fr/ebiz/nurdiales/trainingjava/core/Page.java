package fr.ebiz.nurdiales.trainingjava.core;

import java.util.List;

public class Page {
    private List<Computer> computers;
    private long quantity;

    public List<Computer> getComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
