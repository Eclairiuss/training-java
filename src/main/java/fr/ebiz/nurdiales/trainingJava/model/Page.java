package fr.ebiz.nurdiales.trainingJava.model;

import java.util.List;

public class Page {
    private List<ComputerDTO> computers;
    private int quantity;

    public List<ComputerDTO> getComputers() {
        return computers;
    }

    public void setComputers(List<ComputerDTO> computers) {
        this.computers = computers;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
