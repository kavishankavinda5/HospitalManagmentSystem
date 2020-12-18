package sample.model;
//create a Received Postal class

public class ReceivedPostal extends Postal {


//Instance variable
    private String fromAddress;

    //getter & setter

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }


    //toString method
    @Override
    public String toString() {
        return "ReceivedPostal{" +
                "fromAddress='" + fromAddress + '\'' +
                '}'+ super.toString();
    }
}
