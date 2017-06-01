package fr.ebiz.nurdiales.trainingjava.core;

public class Parameters {
    public enum SortingElement {
        ID,
        NAME,
        INTRODUCED,
        DISCONTINUED,
        COMPANY
    }

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

    public static class Builder {
        private Parameters params;

        /**
         * default builder for parameters.
         */
        public Builder() {
            params = new Parameters();
        }

        /**
         * set the value of reversed in params.
         * @param reversed value to set.
         * @return the builder.
         */
        public Builder reversed(boolean reversed) {
            params.setReversed(reversed);
            return this;
        }

        /**
         * set the value of idCompany in params.
         * @param idCompany value to set.
         * @return the builder.
         */
        public Builder idCompany(int idCompany) {
            params.setIdCompany(idCompany);
            return this;
        }

        /**
         * set the value of page in params.
         * @param page value to set.
         * @return the builder.
         */
        public Builder page(int page) {
            if (page < 0) {
                params.setPage(0);
            }
            params.setPage(page);
            return this;
        }

        /**
         * set the value of size in params.
         * @param size value to set.
         * @return the builder.
         */
        public Builder size(int size) {
            if (size < 1) {
                params.setSize(10);
            } else {
                params.setSize(size);
            }
            return this;
        }

        /**
         * set the value of name in params.
         * @param name value to set.
         * @return the builder.
         */
        public Builder name(String name) {
            params.setName(name);
            return this;
        }

        /**
         * set the value of nameCompany in params.
         * @param nameCompany value to set.
         * @return the builder.
         */
        public Builder nameCompany(String nameCompany) {
            params.setNameCompany(nameCompany);
            return this;
        }

        /**
         * set the value of nameCompany in params.
         * @param nameCompany value to set.
         * @return the builder.
         */
        public Builder page(String nameCompany) {
            params.setNameCompany(nameCompany);
            return this;
        }

        /**
         * set the value of sortBy in params.
         * @param trierPar value to set.
         * @return the builder.
         */
        public Builder trierPar(SortingElement trierPar) {
            params.setSortBy(trierPar);
            return this;
        }

        /**
         *  build parameters.
         * @return params make by the builder.
         */
        public Parameters build() {
            return params;
        }
    }

    /**
     * Parse le tri et choisi la valeur a attribuer.
     * @param sTri string from request.
     */
    public void parseTri(String sTri) {
        if (sTri == null || sTri.length() < 2) {
            sortBy = SortingElement.ID;
        } else {
            switch (sTri.charAt(0)) {
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
            reversed = (sTri.charAt(1) == 'u') ? true : false;
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

