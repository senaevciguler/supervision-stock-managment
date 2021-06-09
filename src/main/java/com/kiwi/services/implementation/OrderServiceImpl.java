package com.kiwi.services.implementation;

import com.kiwi.entities.Order;
import com.kiwi.entities.Product;
import com.kiwi.entities.ProductOrder;
import com.kiwi.entities.Stock;
import com.kiwi.exception.NotFoundException;
import com.kiwi.repositories.OrderRepository;
import com.kiwi.services.BasketService;
import com.kiwi.services.OrderService;
import com.kiwi.services.ProductService;
import com.kiwi.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    @Autowired
    StockService stockService;

    @Autowired
    BasketService basketService;

    @Override
    public Order save(Order order) {
        List<Product> products = productService
                .findByIds(order.getProductOrder().stream().map(ProductOrder::getProductId).collect(Collectors.toList()));

       for(Product product:products) {
                ProductOrder productOrder = order.getProductOrder().stream()
                        .filter(po -> po.getProductId()==product.getId())
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("Product stock not exist"));

           if(product.getStock().getQuantity().compareTo(productOrder.getCount()) == -1) {
               throw new NotFoundException("Product stock not exist ");
           }

                Stock stock = product.getStock();
                stock.setQuantity(stock.getQuantity()- productOrder.getCount());
                stockService.update(stock, stock.getId());
       }

       basketService.delete(order.getBasket().getId());

        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public Optional<Order> update(Order order, long id) {
        return orderRepository.findById(id).map(orderUpdated -> {
            orderUpdated.setDate(order.getDate());

            return orderRepository.save(orderUpdated);
        });
    }

    @Override
    public void delete(long id) {
        orderRepository.deleteById(id);
    }

}
