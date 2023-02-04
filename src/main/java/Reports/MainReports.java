package Reports;

import Shop.Repository;

import java.util.Scanner;

public class MainReports {

    public MainReports() {
        Scanner sc = new Scanner(System.in);
        new Repository();
        System.out.println("Welcome to ShoeShop reports");

        while (true) {
            System.out.println();
            System.out.println("Choose from the following reports:");
            System.out.println();
            System.out.println("1. Total sales by brand/color/size listed by customer");
            System.out.println("2. Total orders per customer");
            System.out.println("3. Total sales amount per customer");
            System.out.println("4. Total sales by city");
            System.out.println("5. Total sales by model");
            System.out.println("6. Exit");

            System.out.println();
            System.out.print("Enter your choice: ");

            try
                {
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1 -> {
                            System.out.println();
                            System.out.println("Total sales by brand/color/size listed by customer");
                            System.out.println();
                            ReportMethods.getSalesByBrandColorSize();
                            System.out.println();
                        }
                        case 2 -> {
                            System.out.println();
                            System.out.println("Total orders per customer");
                            System.out.println();
                            ReportMethods.printAllCustomersAndSelectedInfo("orders");
                            System.out.println();
                        }
                        case 3 -> {
                            System.out.println();
                            System.out.println("Total sales amount per customer");
                            System.out.println();
                            ReportMethods.printAllCustomersAndSelectedInfo("total");
                            System.out.println();
                        }
                        case 4 -> {
                            System.out.println();
                            System.out.println("Total sales by city");
                            System.out.println();
    //                        ReportsRepository.getSalesByCity();
                            System.out.println();
                        }
                        case 5 -> {
                            System.out.println();
                            System.out.println("Total sales by model");
                            System.out.println();
    //                        ReportsRepository.getSalesByModel();
                            System.out.println();
                        }
                        case 6 -> {
                            System.out.println();
                            System.out.println("Exit");
                            System.out.println();
                            System.exit(0);
                        }
                        default -> {
                            System.out.println();
                            System.out.println("Please enter a number between 1-6");
                            System.out.println();
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Please enter a number");
                }
        }
    }

        public static void main(String[] args) {
            new MainReports();
            }
}
