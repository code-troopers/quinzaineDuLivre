package org.fol37.quinzainedulivre.web.rest.dto;


public class File  {

    private String name;

    private String content;

    private String contract;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "File{" +
                super.toString() + "," +
                "name=" + name + "," +
                "contract=" + contract +
                "}";
    }

}
