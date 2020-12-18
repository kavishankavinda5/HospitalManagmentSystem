package sample.model;

//create a Dispatch Postal class

public class DispatchPostal extends Postal{

    //Instance variable
    private String address;

    //getter & setter

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //toString method
    @Override
    public String toString() {
        return "DispatchPostal{" +
                "address='" + address + '\'' +
                '}'+ super.toString();
    }
}
