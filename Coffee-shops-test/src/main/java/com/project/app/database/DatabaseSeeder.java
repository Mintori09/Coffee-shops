package com.project.app.database;

import com.project.app.dao.impl.BillDAOImpl;
import com.project.app.dao.impl.BillDetailDAOImpl;
import com.project.app.dao.impl.DrinkCategoryDAOImpl;
import com.project.app.dao.impl.DrinkDAOImpl;
import com.project.app.model.Bill;
import com.project.app.model.BillDetail;
import com.project.app.model.Drink;
import com.project.app.model.DrinkCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DatabaseSeeder {

    private BillDAOImpl billDAO = new BillDAOImpl();
    private BillDetailDAOImpl billDetailDAO = new BillDetailDAOImpl();
    private DrinkCategoryDAOImpl drinkCategoryDAO = new DrinkCategoryDAOImpl();
    private DrinkDAOImpl drinkDAO = new DrinkDAOImpl();

    public static void main(String[] args) {
        DatabaseSeeder seeder = new DatabaseSeeder();
        seeder.seed();
    }

    public void seed() {
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
        BillDetail detail1 = new BillDetail(1, "1", 2, 7.00, "Extra shot", LocalDateTime.now());
        BillDetail detail2 = new BillDetail(1, "2", 1, 2.50, "", LocalDateTime.now());
        BillDetail detail3 = new BillDetail(2, "3", 3, 12.00, "No ice", LocalDateTime.now());

        billDetailDAO.create(detail1);
        billDetailDAO.create(detail2);
        billDetailDAO.create(detail3);
        System.out.println("Bill Details seeded.");
    }
}
