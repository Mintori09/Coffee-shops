package com.project.app.database;

import com.project.app.dao.impl.BillDAOImpl;
import com.project.app.dao.impl.AccountDAOImpl;
import com.project.app.dao.impl.BillDAOImpl;
import com.project.app.dao.impl.BillDetailDAOImpl;
import com.project.app.dao.impl.DrinkCategoryDAOImpl;
import com.project.app.dao.impl.DrinkDAOImpl;
import com.project.app.dao.impl.EmployeeDAOImpl;
import com.project.app.model.Account;
import com.project.app.model.Bill;
import com.project.app.model.BillDetail;
import com.project.app.model.Drink;
import com.project.app.model.DrinkCategory;
import com.project.app.model.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseSeeder {

    private AccountDAOImpl accountDAO = new AccountDAOImpl();
    private BillDAOImpl billDAO = new BillDAOImpl();
    private BillDetailDAOImpl billDetailDAO = new BillDetailDAOImpl();
    private DrinkCategoryDAOImpl drinkCategoryDAO = new DrinkCategoryDAOImpl();
    private DrinkDAOImpl drinkDAO = new DrinkDAOImpl();
    private EmployeeDAOImpl employeeDAO = new EmployeeDAOImpl();

    public static void main(String[] args) {
        DatabaseSeeder seeder = new DatabaseSeeder();
        seeder.seed();
    }

    public void seed() {
        System.out.println("Clearing existing data...");
        try {
            billDetailDAO.deleteAll();
            billDAO.deleteAll();
            drinkDAO.deleteAll();
            drinkCategoryDAO.deleteAll();
            employeeDAO.deleteAll();
            accountDAO.deleteAll();
            System.out.println("Existing data cleared.");
        } catch (java.sql.SQLException e) {
            System.err.println("Error clearing existing data: " + e.getMessage());
            e.printStackTrace();
            // Optionally, you might want to exit or handle the error differently
            // System.exit(1);
        }


        seedAccounts();
        seedEmployees();
        seedDrinkCategories();
        seedDrinks();
        seedBills();
        seedBillDetails();
    }

    private void seedDrinkCategories() {
        System.out.println("Seeding Drink Categories...");
        DrinkCategory category1 = new DrinkCategory(0, "Coffee");
        DrinkCategory category2 = new DrinkCategory(0, "Tea");
        DrinkCategory category3 = new DrinkCategory(0, "Juice");

        drinkCategoryDAO.create(category1);
        drinkCategoryDAO.create(category2);
        drinkCategoryDAO.create(category3);
        System.out.println("Drink Categories seeded.");
    }

    private void seedDrinks() {
        System.out.println("Seeding Drinks...");
        // Assuming category IDs are 1, 2, 3 based on insertion order
        Drink drink1 = new Drink(0, "Espresso", "1", 3.50, "Strong coffee", "images/espresso.png", LocalDateTime.now());
        Drink drink2 = new Drink(0, "Green Tea", "2", 2.50, "Healthy green tea", "images/greentea.png", LocalDateTime.now());
        Drink drink3 = new Drink(0, "Orange Juice", "3", 4.00, "Freshly squeezed orange juice", "images/orangejuice.png", LocalDateTime.now());

        drinkDAO.create(drink1);
        drinkDAO.create(drink2);
        drinkDAO.create(drink3);
        System.out.println("Drinks seeded.");
    }

    private void seedBills() {
        System.out.println("Seeding Bills...");
        // Assuming employee ID 1 exists
        Bill bill1 = new Bill(0, LocalDate.now(), 10.00, "First bill", LocalDateTime.now(), 1);
        Bill bill2 = new Bill(0, LocalDate.now(), 15.50, "Second bill", LocalDateTime.now(), 1);

        billDAO.create(bill1);
        billDAO.create(bill2);
        System.out.println("Bills seeded.");
    }

    private void seedBillDetails() {
        System.out.println("Seeding Bill Details...");
        // Assuming bill IDs are 1, 2 and drink IDs are 1, 2, 3 based on insertion order
        // Hardcoding drink names for seeding purposes
        BillDetail detail1 = new BillDetail();
        detail1.setBillId(1);
        detail1.setDrinkId(1);
        detail1.setQuantity(2);
        detail1.setPrice(7.00);
        detail1.setNotes("Extra shot");
        detail1.setCreatedDate(LocalDateTime.now());

        BillDetail detail2 = new BillDetail();
        detail2.setBillId(1);
        detail2.setDrinkId(2);
        detail2.setQuantity(1);
        detail2.setPrice(2.50);
        detail2.setNotes("");
        detail2.setCreatedDate(LocalDateTime.now());

        BillDetail detail3 = new BillDetail();
        detail3.setBillId(2);
        detail3.setDrinkId(3);
        detail3.setQuantity(3);
        detail3.setPrice(12.00);
        detail3.setNotes("No ice");
        detail3.setCreatedDate(LocalDateTime.now());

        billDetailDAO.create(detail1);
        billDetailDAO.create(detail2);
        billDetailDAO.create(detail3);
        System.out.println("Bill Details seeded.");
    }

    private void seedAccounts() {
        System.out.println("Seeding Accounts...");
        Account account1 = new Account(0, "admin", "admin123", "admin");
        Account account2 = new Account(0, "employee1", "emp123", "employee");

        accountDAO.create(account1);
        accountDAO.create(account2);
        System.out.println("Accounts seeded.");
    }

    private void seedEmployees() {
        System.out.println("Seeding Employees...");
        // Assuming account IDs are 1, 2 based on insertion order
        Employee employee1 = new Employee(0, "Nguyen Van A", LocalDate.of(1990, 5, 15), "Male", "0123456789", LocalDateTime.now(), 1);
        Employee employee2 = new Employee(0, "Tran Thi B", LocalDate.of(1992, 8, 20), "Female", "0987654321", LocalDateTime.now(), 2);

        employeeDAO.create(employee1);
        employeeDAO.create(employee2);
        System.out.println("Employees seeded.");
    }
}
