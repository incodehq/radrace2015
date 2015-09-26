package domainapp.dom.supplierreport;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.inject.Inject;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import domainapp.dom.event.Event;
import domainapp.dom.ingredient.Ingredient;
import domainapp.dom.orderitem.OrderItem;
import domainapp.dom.orderitem.OrderItemRepository;
import domainapp.dom.supplier.Supplier;

@DomainService(nature = NatureOfService.DOMAIN)
public class SupplierReportCreator {


    public Map<String, Integer> reportItemsFor(Supplier supplier, Event event) {

        Map<String, Integer> reportItems = Maps.newTreeMap();

        final List<OrderItem> orderItems = orderItemRepository.findByEvent(event);
        for (OrderItem orderItem : orderItems) {
            final SortedSet<Ingredient> ingredients = orderItem.getMenuItem().getIngredients();
            for (Ingredient ingredient : ingredients) {
                final Supplier ingredientSupplier = ingredient.getSupplier();
                if(Objects.equal(supplier, ingredientSupplier)) {
                    final int quantity = orderItem.getQuantity();
                    final Integer existingQuantity = reportItems.get(ingredient.getName());
                    final int newQuantity = existingQuantity == null ? quantity : existingQuantity + quantity;
                    reportItems.put(ingredient.getName(), newQuantity);
                }
            }
        }

        return reportItems;
    }

    @Inject
    OrderItemRepository orderItemRepository;

}

