import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        ViewBalance vb=new ViewBalance();
        List<BankAccount> arrBank =new ArrayList<>();
        BankAccount admin = new BankAccount(CustomerType.Admin, "Admin");
        BankAccount user1 = new BankAccount( 12345678, 0, "User", "", "user@gmail.com", "unknown", 707219789);
        arrBank.add(admin);
        arrBank.add(user1);
        System.out.println(admin+"  "+user1);
        int accountCounter = 2;
        BankAccount selectedAccount = null;
        System.out.println("admin \nName: " + admin.getName() + "\nEmail: " + admin.getEmail() + "\nPassword: " + admin.getPassword()+"\nAccount Number: 1");
        System.out.println("----------------------------------");
        System.out.println("user \nName: " + user1.getName() + "\nEmail: " + user1.getEmail() + "\nPassword: " + user1.getPassword());
        selectedAccount = Login(selectedAccount, arrBank);
        chooseOperation(selectedAccount, arrBank, accountCounter,vb);
    }

    private static void invalidInput(BankAccount selectedAccount) throws InvalidAccountException {
        if (selectedAccount == null) {
            try {
                throw new InvalidAccountException("You enter Invalid Password or Name or Email please try again.(This is CustomException)");
            } catch (InvalidAccountException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static BankAccount Login(BankAccount selectedAccount, List<BankAccount> arrBank) {
        System.out.print("""
                                                 Log in
                """);
        Scanner sr = new Scanner(System.in);
        selectedAccount=null;
        while (selectedAccount == null) {
            System.out.print("Email or name:");
            String input1 = sr.nextLine().trim();
            System.out.print("Password:");
            String input2 = sr.nextLine().trim();
            for (BankAccount bankAccount : arrBank) {
                if (bankAccount == null) {
                    break;
                }
                if (input1.contains("@")) {
                    if (input1.equals(bankAccount.getEmail()))
                        if (input2.equals(bankAccount.getPassword())) {
                            selectedAccount = bankAccount;
                            break;
                        }
                } else {
                    if (input1.equals(bankAccount.getName()))
                        if (input2.equals(bankAccount.getPassword())) {
                            selectedAccount = bankAccount;
                            break;
                        }
                }
            }
            invalidInput(selectedAccount);
        }
        return selectedAccount;
    }

    private static void chooseOperation(BankAccount selectedAccount, List<BankAccount> arrBank, int accountCounter, ViewBalance vb) {
        System.out.print("""
                                
                Display(1)
                Deposit(2)
                Withdraw(3)
                Transfer(4)
                Change Account(5)
                Create New Account(6)
                Display balance(7)
                Exit(-1)
                Enter (int):""");
        Scanner sr = new Scanner(System.in);
        switch (sr.nextInt()) {
            case 1 -> {
                System.out.print("""
                        
                        Account(1)
                        History(2)
                        Back(0)
                        Enter(int):""");
                switch (sr.nextInt()){
                    case 1 -> {
                        System.out.println(selectedAccount);
                        chooseOperation(selectedAccount,arrBank,accountCounter,vb);
                    }
                    case 2-> {
                        selectedAccount.displayHistory();
                        chooseOperation(selectedAccount,arrBank,accountCounter,vb);
                    }
                        default ->chooseOperation(selectedAccount,arrBank,accountCounter,vb);
                }
            }
            case 2 -> {
                selectedAccount.deposit();
                chooseOperation(selectedAccount, arrBank, accountCounter,vb);
            }
            case 3 -> {
                selectedAccount.withdraw();
                chooseOperation(selectedAccount, arrBank, accountCounter,vb);
            }
            case 4 -> { selectedAccount.transfer(arrBank);
                chooseOperation(selectedAccount,arrBank,accountCounter,vb);
            }
            case 5 -> {
                selectedAccount=Login(selectedAccount, arrBank);
                chooseOperation(selectedAccount,arrBank,accountCounter,vb);
            }
            case 6 -> {
                    selectedAccount=new BankAccount(arrBank);
                    arrBank.add(selectedAccount);
                    System.out.println(selectedAccount);
                    accountCounter++;
                    chooseOperation(selectedAccount, arrBank, accountCounter,vb);
            }
            case 7 -> {
                vb.viewMethode(selectedAccount);
                chooseOperation(selectedAccount,arrBank, accountCounter, vb);
            }
            case -1 -> {
                System.out.print("Do you want to Exit?\nYes(1),No(2):");
                if (sr.nextInt() == 1) {
                    exit(1);
                } else {
                    chooseOperation(selectedAccount, arrBank, accountCounter,vb);
                }
            }
            default -> {
                System.out.print("Please enter valid number!");
                chooseOperation(selectedAccount, arrBank, accountCounter,vb);
            }
        }
    }


}