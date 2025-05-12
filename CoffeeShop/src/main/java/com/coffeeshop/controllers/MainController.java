package com.coffeeshop.controllers;

import com.coffeeshop.views.MainView;
import com.coffeeshop.services.ProductService;
import com.coffeeshop.services.OrderService;

public class MainController {
    private final MainView view;
    private final ProductService productService;
    private final OrderService orderService;

    public MainController(MainView view) {
        this.view = view;
        this.productService = new ProductService();
        this.orderService = new OrderService();
    }

    public void initController() {
        view.setVisible(true);
        // Initialize event listeners here
    }
}
