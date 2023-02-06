package Shop;

import DBTables.Item;

import java.util.List;
import java.util.Scanner;

public class Checkout {
    final int customerId;
    final int orderId;

    public Checkout(List<Item> cart, int customerId, int orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
        Scanner sc = new Scanner(System.in);

        if (cart != null) {
            System.out.println();
            System.out.println("Your shopping cart has the following " + cart.size() + " item(s):");
            System.out.println();
            cart.stream()
                    .filter(i -> i.getBrand_id() != null &&
                            i.getColor_id() != null &&
                            i.getSize_id() != null)
                    .forEach(i -> System.out.println(i.minimumToString()));
            System.out.println("Subtotal: " + Shopping.getSubtotal() + " SEK. ");
            System.out.println();
            System.out.println("Submit order? ");
            System.out.println("1. Yes");
            System.out.println("2. Cancel");
            System.out.println("Enter your choice: ");
            int choiceSubmit = sc.nextInt();

            switch (choiceSubmit) {
                case 1 -> {
                    orderId = 0;
                    int finalOrderId;
                    // fler än en vara i kundvagnen
                    if (cart.size() > 1) {
                        finalOrderId = Repository.addToOrder(orderId, customerId, (cart.get(0)).getId());
                        for (int i = 1; i < cart.size(); i++) {
                            Repository.addToOrder(finalOrderId, customerId, (cart.get(i)).getId());
                        }
                        System.out.println();
                        System.out.println("Your order of " + cart.size() + " item(s) has been submitted!");
                        System.out.println("Your order number is: " + finalOrderId);
                        cart.clear(); // tömmer varukorgen när checkout är klar
                    }
                    else if (cart.size() == 1) { // en enda vara i kundvagnen
                        orderId = Repository.addToOrder(orderId, customerId, cart.get(0).getId());
                        System.out.println();
                        System.out.println("Your order of " + cart.size() + " item(s) has been submitted!");
                        System.out.println("Your order number is: " + orderId);
                        if (orderId >= 0) {
                            finalOrderId = orderId;
                            int finalOrderId1 = finalOrderId;

                            cart.stream().skip(1).forEach(i -> {
                                Repository.addToOrder(finalOrderId1, customerId, i.getId());
                                System.out.println();
                                System.out.println("Your order of " + cart.size() + " item(s) has been submitted!");
                                System.out.println("Your order number is: " + finalOrderId1);

                            });
                            cart.clear(); // tömmer varukorgen när checkout är klar
                        } else if (orderId == -1) { // -1 skickas tillbaka av StoredProcedure om något blev fel vid körning i db.
                            System.out.println("Something went wrong and your order has not been submitted.");
                        }
                        else { // betyder att orderId inte har ändrats
                            System.out.println("Your order has not been submitted!");
                        }
                    }
                    else{
                        System.out.println();
                        System.out.println("Your cart is empty!");
                        System.out.println();
                    }
                }
                case 2 -> {
                    System.out.println();
                    System.out.println("Your order has been cancelled!");
                    System.out.println();
                    System.out.println("Thanks for shopping!");
//                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}

