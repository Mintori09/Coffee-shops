package com.project.app.database.seeder;

import com.project.app.dao.*;
import com.project.app.dao.impl.*;
import com.project.app.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class DatabaseSeeder {

    private static AccountDAO accountDAO = new AccountDAOImpl();
    private static BillDAO billDAO = new BillDAOImpl();
    private static BillDetailDAO billDetailDAO = new BillDetailDAOImpl();
    private static EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private static FoodCategoryDAO drinkCategoryDAO = new FoodCategoryDAOImpl();
    private static FoodDAO drinkDAO = new FoodDAOImpl();

    public static void seed() {
        seedDrinkCategories();
        seedEmployees();
        seedDrinks();
        seedAccounts();
        seedBills();
        seedBillDetails();
    }

    private static void seedDrinkCategories() {
        System.out.println("Seeding Drink Categories...");
        DrinkCategory category1 = new DrinkCategory(1, "Coffee");
        DrinkCategory category2 = new DrinkCategory(2, "Tea");
        DrinkCategory category3 = new DrinkCategory(3, "Juice");

        drinkCategoryDAO.addDrinkCategory(category1);
        drinkCategoryDAO.addDrinkCategory(category2);
        drinkCategoryDAO.addDrinkCategory(category3);
        System.out.println("Drink Categories seeded.");
    }

    private static void seedEmployees() {
        System.out.println("Seeding Employees...");
        Employee employee1 = new Employee(1, 1, "Nguyen Van A", LocalDate.of(1990, 5, 15), "Male", "0123456789", LocalDateTime.now());
        Employee employee2 = new Employee(2, 2, "Tran Thi B", LocalDate.of(1995, 8, 20), "Female", "0987654321", LocalDateTime.now());

        employeeDAO.addEmployee(employee1);
        employeeDAO.addEmployee(employee2);
        System.out.println("Employees seeded.");
    }

    private static void seedDrinks() {
        System.out.println("Seeding Drinks...");
        // Assuming Drink Categories are already seeded, retrieve some category IDs
        int coffeeCategoryId = drinkCategoryDAO.getAllDrinkCategories().get(0).getFoodCategoryId();
        int teaCategoryId = drinkCategoryDAO.getAllDrinkCategories().get(1).getFoodCategoryId();

        Drink drink1 = new Drink(1, "Espresso", coffeeCategoryId, 3.50, "Strong black coffee", "images/espresso.png", LocalDateTime.now());
        Drink drink2 = new Drink(2, "Green Tea", teaCategoryId, 2.50, "Healthy green tea", "images/greentea.png", LocalDateTime.now());

        drinkDAO.addDrink(drink1);
        drinkDAO.addDrink(drink2);
        System.out.println("Drinks seeded.");
    }

    private static void seedAccounts() {
        System.out.println("Seeding Accounts...");
        // Assuming Employees are already seeded, retrieve some employee IDs
        int employeeId1 = employeeDAO.getAllEmployees().get(0).getId();
        int employeeId2 = employeeDAO.getAllEmployees().get(1).getId();

        Account account1 = new Account(1, "admin", "admin123", "manager");
        Account account2 = new Account(2, "staff", "staff123", "staff");

        accountDAO.addAccount(account1);
        accountDAO.addAccount(account2);
        System.out.println("Accounts seeded.");
    }

    private static void seedBills() {
        System.out.println("Seeding Bills...");
        // Assuming Employees are already seeded, retrieve some employee IDs
        int employeeId1 = employeeDAO.getAllEmployees().get(0).getId();

        Bill bill1 = new Bill(1, LocalDate.now(), 10.00, "Table 1", LocalDateTime.now(), employeeId1);
        Bill bill2 = new Bill(2, LocalDate.now(), 15.00, "Table 2", LocalDateTime.now(), employeeId1);

        billDAO.addBill(bill1);
        billDAO.addBill(bill2);
        System.out.println("Bills seeded.");
    }

    private static void seedBillDetails() {
        System.out.println("Seeding Bill Details...");
        // Assuming Bills and Drinks are already seeded, retrieve some IDs
        int billId1 = billDAO.getAllBills().get(0).getBillId();
        int drinkId1 = drinkDAO.getAllDrinks().get(0).getFoodId();
        int drinkId2 = drinkDAO.getAllDrinks().get(1).getFoodId();

        BillDetail billDetail1 = new BillDetail(billId1, drinkId1, 2, 3.50, "No sugar", LocalDateTime.now());
        BillDetail billDetail2 = new BillDetail(billId1, drinkId2, 1, 2.50, "", LocalDateTime.now());

        billDetailDAO.addBillDetail(billDetail1);
        billDetailDAO.addBillDetail(billDetail2);
        System.out.println("Bill Details seeded.");
    }

    public static void main(String[] args) {
        seed();
    }
}
