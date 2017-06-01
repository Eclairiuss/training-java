package fr.ebiz.nurdiales.trainingjava.core;

public class Parameters {
    /**
     * constructor of paramaters.
     */
    private Parameters() {
    }

    private boolean reversed;
    private int idCompany;
    private int page;
    private int size;
    private String name = "";
    private String nameCompany = "";
    private SortingElement sortBy;


    /**
     * default builder for parameters.
     * @return new parameters.
     */
    public static Parameters builder() {
        return new Parameters();
    }

    /**
     * set the value of reversed in params.
     * @param reversed value to set.
     * @return the builder.
     */
    public Parameters reversed(boolean reversed) {
        this.setReversed(reversed);
        return this;
    }

    /**
     * set the value of idCompany in params.
     * @param idCompany value to set.
     * @return the builder.
     */
    public Parameters idCompany(int idCompany) {
        this.setIdCompany(idCompany);
        return this;
    }

    /**
     * set the value of page in params.
     * @param page value to set.
     * @return the builder.
     */
    public Parameters page(int page) {
        this.setPage(page);
        if (page < 0) {
            this.setPage(0);
        }
        return this;
    }

    /**
     * set the value of size in params.
     * @param size value to set.
     * @return the builder.
     */
    public Parameters size(int size) {
        if (size < 1) {
            this.setSize(10);
        } else {
            this.setSize(size);
        }
        return this;
    }

    /**
     * set the value of name in params.
     * @param name value to set.
     * @return the builder.
     */
    public Parameters name(String name) {
        this.setName(name);
        return this;
    }

    /**
     * set the value of nameCompany in params.
     * @param nameCompany value to set.
     * @return the builder.
     */
    public Parameters nameCompany(String nameCompany) {
        this.setNameCompany(nameCompany);
        return this;
    }

    /**
     * set the value of nameCompany in params.
     * @param nameCompany value to set.
     * @return the builder.
     */
    public Parameters page(String nameCompany) {
        this.setNameCompany(nameCompany);
        return this;
    }

    /**
     * set the value of sortBy in params.
     * @param trierPar value to set.
     * @return the builder.
     */
    public Parameters trierPar(SortingElement trierPar) {
        this.setSortBy(trierPar);
        return this;
    }

    /**
     * Parse le tri et choisi la valeur a attribuer.
     * @param sortingElement string from request.
     */
    public void parseSortingElement(String sortingElement) {
        if (sortingElement == null || sortingElement.length() < 2) {
            sortBy = SortingElement.ID;
        } else {
            switch (sortingElement.charAt(0)) {
                case 'n':
                    sortBy = SortingElement.NAME;
                    break;
                case 'c':
                    sortBy = SortingElement.COMPANY;
                    break;
                case 'i':
                    sortBy = SortingElement.INTRODUCED;
                    break;
                case 'd':
                    sortBy = SortingElement.DISCONTINUED;
                    break;
                default:
                    sortBy = SortingElement.ID;
                    break;
            }
            reversed = (sortingElement.charAt(1) == 'u') ? true : false;
        }
    }


    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = (page < 0) ? 0 : page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = (size < 1) ? 10 : size;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = (name == null) ? "" : name;
    }

    public String getNameCompany() {
        return nameCompany == null ? "" : nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany == null ? "" : nameCompany;
    }

    public SortingElement getSortBy() {
        return sortBy == null ? SortingElement.ID : sortBy;
    }

    public void setSortBy(SortingElement sortBy) {
        this.sortBy = sortBy;
    }
}

