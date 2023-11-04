package jpaProject.shop.service;

import jpaProject.shop.domain.*;
import jpaProject.shop.domain.item.Item;
import jpaProject.shop.repository.ItemRepository;
import jpaProject.shop.repository.MemberRepository;
import jpaProject.shop.repository.OrderRepository;
import jpaProject.shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * order
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // search
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // deliver creation
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // product creation
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // order creation
        Order order = Order.createOrder(member, delivery, orderItem);

        // order save
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * order cancel
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // order search
        Order order = orderRepository.findOne(orderId);
        // order cancel
        order.cancel();
    }

    // search
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
