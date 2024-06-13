public class Customer {
    private String  name, address, email,password;
    private int phone;
    private final int customerID=1+counter;
    public static int counter=0;
    private CustomerType customerType=CustomerType.NormalUser;

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Customer() {
    counter++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCustomerID() {
        return customerID;
    }

    public Customer(String name, String address, String email, String password, int phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
        counter++;
    }

    @Override
    public String toString() {
        return "Customer{" +
               "name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", phone=" + phone +
               ", customerID=" + customerID +
               ", customerType=" + customerType +
               '}';
    }
}
