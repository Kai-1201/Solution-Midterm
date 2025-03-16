import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface PaymentMethod {
    boolean processPayment(int amount);
}

class CreditCardPayment implements PaymentMethod {
    private int balance = 1000;
    private int cardNumber;

    public CreditCardPayment(int cardNumber) {
        this.cardNumber = cardNumber;
    }


    @Override
    public boolean processPayment(int amount) {
        if (amount > balance) {
            System.out.println("Error: Insufficient funds on the card. Your balance: " + balance + "тг");
            return false;
        }
        balance -= amount;
        System.out.println("Payment by card from account " + cardNumber + " in the amount of " + amount + "тг. Balance on the card: " + balance + "тг");
        return true;
    }
}

class PayPalPayment implements PaymentMethod {
    private int balance = 1000;
    private int cardNumber;

    public PayPalPayment(int cardNumber) {
        this.cardNumber = cardNumber;
    }


    @Override
    public boolean processPayment(int amount) {
        if (amount > balance) {
            System.out.println("Error: Insufficient funds on the card. Your balance: " + balance + "тг");
            return false;
        }
        balance -= amount;
        System.out.println("Payment via PayPal from account " + cardNumber + " in the amount of " + amount + "тг. Balance on the card: " + balance + "тг");
        return true;
    }

}

class CryptoPayment implements PaymentMethod {
    private int balance = 1000;
    private int cardNumber;

    public CryptoPayment(int cardNumber) {
        this.cardNumber = cardNumber;
    }


    @Override
    public boolean processPayment(int amount) {
        if (amount > balance) {
            System.out.println("Error: Insufficient funds on the card. Your balance: " + balance + "тг");
            return false;
        }
        balance -= amount;
        System.out.println("Payment in cryptocurrency from account " + cardNumber + " in the amount of " + amount + "тг. Balance on the card: " + balance + "тг");
        return true;
    }

}

class QiwiPayment implements PaymentMethod {
    private int balance = 1000;
    private int cardNumber;

    public QiwiPayment(int cardNumber) {
        this.cardNumber = cardNumber;
    }


    @Override
    public boolean processPayment(int amount) {
        if (amount > balance) {
            System.out.println("Error: Insufficient funds on the card. Your balance: " + balance + "тг");
            return false;
        }
        balance -= amount;
        System.out.println("Payment via Qiwi from account " + cardNumber + " in the amount of "+ amount + "тг. Balance on the card: " + balance + "тг");
        return true;
    }

}

class PaymentFactory {
    public static PaymentMethod getPaymentMethod(String type, int num) {
        return switch (type.toLowerCase()) {
            case "creditcard" -> new CreditCardPayment(num);
            case "paypal" -> new PayPalPayment(num);
            case "crypto" -> new CryptoPayment(num);
            case "qiwi" -> new QiwiPayment(num);
            default -> throw new IllegalArgumentException("Error: Unknown payment method");
        };
    }
}

class LegacyBankAPI {
    public void makeTransaction(int amount) {
        System.out.println("Legacy Bank API: Processing a payment for the amount" + amount + "тг");
    }
}

class BankAdapter implements PaymentMethod {
    private final LegacyBankAPI bankAPI;

    public BankAdapter(LegacyBankAPI bankAPI) {
        this.bankAPI = bankAPI;
    }

    @Override
    public boolean processPayment(int amount) {
        bankAPI.makeTransaction(amount);
        return true;
    }
}

class Coffee {
    private String type;
    private List<String> addons;
    private int cost;

    private Coffee(Builder builder) {
        this.type = builder.type;
        this.addons = builder.addons;
        this.cost = builder.cost;
    }

    public String getDescription() {
        if (addons.isEmpty()) {
            return type + " no additives";
        } else {
            String descriptions = type + " with ";
            for (int i = 0; i < addons.size(); i++) {
                descriptions += addons.get(i);
                if (i < addons.size() - 1) {
                    descriptions += ", ";
                }
            }
            return descriptions;
        }
    }

    public int getCost() {
        return cost;
    }

    public static class Builder {
        private String type;
        private List<String> addons = new ArrayList<>();
        private int cost;

        public Builder(String type, int baseCost) {
            this.type = type;
            this.cost = baseCost;
        }

        public Builder addMilk() { addons.add("Milk"); cost += 100; return this; }
        public Builder addVanilla() { addons.add("Vanilla"); cost += 150; return this; }
        public Builder addChocolate() { addons.add("Chocolate"); cost += 150; return this; }
        public Builder addIce() { addons.add("Ice"); cost += 50; return this; }
        public Builder addVeganMilk() { addons.add("Vegan milk"); cost += 120; return this; }
        public Builder addCinnamon() { addons.add("Cinnamon"); cost += 140; return this; }
        public Builder addWhippedCream() { addons.add("Whipped cream"); cost += 130; return this; }

        public Coffee build() { return new Coffee(this); }
    }
}

class CoffeeFactory {
    public static Coffee.Builder createCoffee(String type) {
        return switch (type.toLowerCase()) {
            case "espresso" -> new Coffee.Builder("Espresso", 400);
            case "latte" -> new Coffee.Builder("Latte", 450).addMilk();
            case "cappuccino" -> new Coffee.Builder("Cappuccino", 750).addMilk().addCinnamon();
            case "americano" -> new Coffee.Builder("Americano", 650);
            default -> throw new IllegalArgumentException("Error: Unknown type of coffee");
        };
    }
}
class CoffeeOrderService {
    private final Scanner in;

    public CoffeeOrderService(Scanner in) {
        this.in = in;
    }

    public Coffee createOrder() {
        System.out.println("Choose a coffee type: espresso, latte, cappuccino, americano");
        String coffeeType;
        do {
            coffeeType = in.nextLine().toLowerCase();
        } while (!List.of("espresso", "latte", "cappuccino", "americano").contains(coffeeType));

        Coffee.Builder coffeeBuilder = CoffeeFactory.createCoffee(coffeeType);

        boolean adding = true;
        while (adding) {
            System.out.println("Do you want to add extras? (milk, vanilla, chocolate, ice, veganmilk, cinnamon, whippedcream) or type 'done':");
            String extras = in.nextLine().toLowerCase();
            switch (extras) {
                case "milk" -> coffeeBuilder.addMilk();
                case "vanilla" -> coffeeBuilder.addVanilla();
                case "chocolate" -> coffeeBuilder.addChocolate();
                case "ice" -> coffeeBuilder.addIce();
                case "cinnamon" -> coffeeBuilder.addCinnamon();
                case "done" -> adding = false;
                default -> System.out.println("Invalid input, try again.");
            }
        }

        return coffeeBuilder.build();
    }
}

class PaymentService {
    private final Scanner in;

    public PaymentService(Scanner in) {
        this.in = in;
    }

    public void processPayment(int amount) {
        System.out.println("Choose a payment method: creditcard, paypal, crypto, qiwi");
        String paymentMethod;
        do {
            paymentMethod = in.nextLine().toLowerCase();
        } while (!List.of("creditcard", "paypal", "crypto", "qiwi").contains(paymentMethod));

        System.out.println("Enter payment details (card number or account ID):");
        int cardNumber = in.nextInt();

        PaymentMethod payment = PaymentFactory.getPaymentMethod(paymentMethod, cardNumber);
        boolean success = payment.processPayment(amount);

        if (success) {
            System.out.println("Payment successful!");
        } else {
            System.out.println("Payment failed. Try another method.");
        }
    }
}



public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        CoffeeOrderService coffeeService = new CoffeeOrderService(in);
        Coffee coffee = coffeeService.createOrder();

        System.out.println("Your order: " + coffee.getDescription() + ", Price: " + coffee.getCost() + "тг");

        PaymentService paymentService = new PaymentService(in);
        paymentService.processPayment(coffee.getCost());

    }
}




