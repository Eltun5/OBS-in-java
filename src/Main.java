import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        BankAccount[] arrBank = new BankAccount[10];
        BankAccount admin = new BankAccount(CustomerType.Admin, "Admin");
        BankAccount user1 = new BankAccount( 12345678, 0, "User", "", "user@gmail.com", "unknown", 707219789);
        arrBank[0] = admin;
        arrBank[1] = user1;
        System.out.println(admin+"  "+user1);
        int accountCounter = 2;
        BankAccount selectedAccount = null;
        System.out.println("admin \nName: " + admin.getName() + "\nEmail: " + admin.getEmail() + "\nPassword: " + admin.getPassword()+"\nAccount Number: 1");
        System.out.println("----------------------------------");
        System.out.println("user \nName: " + user1.getName() + "\nEmail: " + user1.getEmail() + "\nPassword: " + user1.getPassword());
        selectedAccount = Login(selectedAccount, arrBank);
        chooseOperation(selectedAccount, arrBank, accountCounter);

        //Hesab balansına və əvvəlcədən müəyyən edilmiş
        // faiz dərəcəsinə əsaslanaraq faizi hesablamaq üçün bir üsul müəyyənləşdirin.
        //Faizləri mütəmadi olaraq hesab balansına tətbiq edin (məsələn, aylıq).
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

    private static BankAccount Login(BankAccount selectedAccount, BankAccount[] arrBank) {
        System.out.print("""
                                                 Log in
                """);
        Scanner sr = new Scanner(System.in);
        selectedAccount=null;
        while (selectedAccount == null) {
            System.out.print("Email or name:");
            String input1 = sr.nextLine();
            System.out.print("Password:");
            String input2 = sr.nextLine();
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

    private static void chooseOperation(BankAccount selectedAccount, BankAccount[] arrBank, int accountCounter) {
        System.out.printf("""
                                
                Display(1)
                Deposit(2)
                Withdraw(3)
                Transfer(4)
                Change Account(5)
                Create New Account (Bank has %s empty location)(6)
                Exit(-1)
                Enter (int):""", (10 - accountCounter));
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
                        chooseOperation(selectedAccount,arrBank,accountCounter);
                    }
                    case 2-> {
                        selectedAccount.displayHistory();
                        chooseOperation(selectedAccount,arrBank,accountCounter);
                    }
                        default ->chooseOperation(selectedAccount,arrBank,accountCounter);
                }
            }
            case 2 -> {
                selectedAccount.deposit();
                chooseOperation(selectedAccount, arrBank, accountCounter);
            }
            case 3 -> {
                selectedAccount.withdraw();
                chooseOperation(selectedAccount, arrBank, accountCounter);
            }
            case 4 -> { selectedAccount.transfer(arrBank);
                chooseOperation(selectedAccount,arrBank,accountCounter);
            }
            case 5 -> {
                selectedAccount=Login(selectedAccount, arrBank);
                chooseOperation(selectedAccount,arrBank,accountCounter);
            }
            case 6 -> {
                if (accountCounter < 10) {
                    arrBank[accountCounter] = new BankAccount(arrBank);
                    selectedAccount=arrBank[accountCounter];
                    System.out.println(arrBank[accountCounter]);
                    accountCounter++;
                    chooseOperation(selectedAccount, arrBank, accountCounter);

                } else {
                    System.out.println("Bank is full please delete some account.");
                }
            }
            case -1 -> {
                System.out.print("Do you want to Exit?\nYes(1),No(2):");
                if (sr.nextInt() == 1) {
                    exit(1);
                } else {
                    chooseOperation(selectedAccount, arrBank, accountCounter);
                }
            }
            default -> {
                System.out.print("Please enter valid number!");
                chooseOperation(selectedAccount, arrBank, accountCounter);
            }
        }
    }


}