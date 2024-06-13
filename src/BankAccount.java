import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class BankAccount extends Customer {
    private final AccountType accountType=AccountType.CHECKING;
    private final int accountNumber;
    private int balance;
    private TransactionStatus transactionStatus=TransactionStatus.WAIT;
    private final String[] date=new String[1000];

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void deposit() {
        if (getCustomerType() == CustomerType.Admin) {
            System.out.println("You must be less than " + (Integer.MAX_VALUE - this.balance + 1) + " if you enter grater than this time You will owe us about " + Integer.MIN_VALUE);
        }
        Scanner sr = new Scanner(System.in);
        System.out.print("Please enter deposit number:");
        int input = sr.nextInt();
        InsufficientFundsException(input, 0, "Your Input must be grater than or equals 0.");
        this.balance += input;
        for (int i = 0; i < 1000; i++) {
            if (date[i]==null){
                date[i]= "Deposit " + input + " Balance " + balance +
                         " Date " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                break;
            }
            if (i==999&&date[i]!=null){
                System.out.println("Memory is full that is why we cannot save in history.");
            }
        }
        System.out.println("\nYou deposit " + input + "$ and your current balance is " + this.balance + "$.");
    }

    private void InsufficientFundsException(int big, int small, String massage) throws InsufficientFundsException {
        if (small > big) {
            try {
                throw new InsufficientFundsException(massage + "(This is CustomException).");
            } catch (InsufficientFundsException e) {
                System.out.println(e.getMessage());
                if (small == 0) {
                    deposit();
                } else {
                    withdraw();
                }
            }
        }
    }

    public void withdraw() {
        if (this.balance < 0) {
            System.out.println("Please pay off your debt first.");
        } else if (this.balance == 0) {
            System.out.println("You don't have money.");
        } else {
            System.out.println("You have " + this.balance + "$.");
            Scanner sr = new Scanner(System.in);
            System.out.print("Please enter withdraw number:");
            int input = sr.nextInt();
            InsufficientFundsException(this.balance, input, "Your Input must be less than or equals " + this.balance + "$.");
            this.balance-=input;
            for (int i = 0; i < 1000; i++) {
                if (date[i]==null){
                    date[i]= "Withdraw " + input + " Balance " + balance +
                             " Date " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    break;
                }
                if (i==999&&date[i]!=null){
                    System.out.println("Memory is full that is why we cannot save in history.");
                }
            }
            System.out.println("\nYou withdraw " + input + "$ and your current balance is " + this.balance + "$.");
        }
    }

    public void transfer( BankAccount[] arrBank) {
        BankAccount transferAccount = null;
        Scanner sr = new Scanner(System.in);
        if (this.balance < 0) {
            System.out.println("Please pay off your debt first.");
        } else if (this.balance == 0) {
            System.out.println("You don't have money.");
        } else {
            while (transferAccount == null) {
                System.out.print("AccountNumber:");
                int input1 = sr.nextInt();
                for (BankAccount bankAccount : arrBank) {
                    if (bankAccount != this) {
                        if (bankAccount == null) {
                            break;
                        }
                        if (input1==bankAccount.getAccountNumber()) {
                            transferAccount=bankAccount;
                        }
                    }
                }
                if (transferAccount == null) {
                    try {
                        throw new InvalidAccountException("You enter Invalid Password or Name or Email please try again.(This is CustomException)");
                    } catch (InvalidAccountException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            System.out.println("You have " + this.balance + "$.");
            Scanner sr1 = new Scanner(System.in);
            System.out.print("Please enter transfer number:");
            int input = sr1.nextInt();
            InsufficientFundsException(this.balance, input, "Your Input must be less than or equals " + this.balance + "$.");
            this.balance-=input;
            transferAccount.setBalance(transferAccount.getBalance()+input);
            this.transactionStatus=TransactionStatus.SUCCESS;
            for (int i = 0; i < 1000; i++) {
                if (date[i]==null){
                    date[i]= "Transfer " + input + " Balance " + balance +" transferAccount "+transferAccount.getAccountNumber()+
                             " Date " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    break;
                }
                if (i==999&&date[i]!=null){
                    System.out.println("Memory is full that is why we cannot save in history.");
                }
            }
            for (int i = 0; i < 1000; i++) {
                if (transferAccount.date[i]==null){
                    transferAccount.date[i]= "Transfer " + input + " Balance " + transferAccount.balance +" transferAccount "+accountNumber+
                             " Date " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    break;
                }
            }
            System.out.println("\nYou transfer " + input + "$ and your current balance is " + this.balance + "$(transactionStatus="+this.transactionStatus+").");
            this.transactionStatus=TransactionStatus.WAIT;
        }
    }

    public void displayHistory() {
        System.out.println("\n");
        for (int i = 0; i < 1000; i++) {
            if (date[i]==null&&i==0){
                System.out.println("History is empty ");
                break;
            }
            if (date[i]==null)
                break;
            System.out.println(date[i]);
        }
    }

    public BankAccount(CustomerType customerType, String name) {
        setName(name);
        setCustomerType(customerType);
        setEmail("admin@gmail.com");
        this.accountNumber = 1;
        setPassword("0");
        this.balance = Integer.MAX_VALUE - 10000;
    }

    public BankAccount( int accountNumber, int balance, String name, String address, String email, String password, int phone) {
        super(name, address, email, password, phone);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public BankAccount(BankAccount[] arr) {
        Scanner sr = new Scanner(System.in);
        Scanner sr1 = new Scanner(System.in);
        System.out.print("Name(cannot contain @):");
        setName(sr.nextLine());
        while (getName().isEmpty() || getName().length() > 20 || getName().contains("@")) {
            System.out.print("Please enter valid Name:");
            setName(sr.nextLine());
        }
        System.out.print("Email:");
        setEmail(sr.nextLine());
        while (!getEmail().contains("@")) {
            System.out.print("Please enter valid Email:");
            setEmail(sr.nextLine());
        }
        System.out.print("Address:");
        setAddress(sr.nextLine());
        System.out.print("Phone:");
        setPhone(sr1.nextInt());
        System.out.print("Password:");
        setPassword(sr.nextLine());
        while (getPassword().length() < 8) {
            System.out.print("""
                    Password can not be less than 8 character.
                    Please enter valid Password:""");
            setPassword(sr.nextLine());
        }
        System.out.print("Password again:");
        while (!sr.nextLine().equals(getPassword()))
            System.out.print("Please enter same Password:");
        this.accountNumber = (int) (Math.random() * 899999) + 10000000;
        for (BankAccount bankAccount:arr) {
            if (this.accountNumber!=bankAccount.getAccountNumber()){
                break;
            }
        }
        System.out.println("Your account number is " + this.accountNumber);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
               "name='" + getName() + '\'' +
               ", address='" + getAddress() + '\'' +
               ", email='" + getEmail() + '\'' +
               ", password='" + getPassword() + '\'' +
               ", phone=" + getPhone() +
               ", customerID=" + getCustomerID() +
               ", customerType=" + getCustomerType() +
               ", accountType=" + accountType +
               ", accountNumber=" + accountNumber +
               ", balance=" + balance +
               ", transactionStatus=" + transactionStatus +
               '}';
    }
}
