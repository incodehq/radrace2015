package domainapp.dom.basket;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.order.Order;
import domainapp.dom.order.OrderStatus;
import domainapp.dom.orderitem.OrderItem;

@ViewModel
public class ShoppingBasket {


    //region > order (property)
    private Order order;

    @Title
    @MemberOrder(sequence = "1")
    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }
    //endregion

    @Action(semantics = SemanticsOf.SAFE)
    public List<MenuItem> chooseMenuItems() {
        return getMenuItems();
    }

    //region > totalToPay (property)
    private BigDecimal totalToPay;

    @MemberOrder(sequence = "1")
    public BigDecimal getTotalToPay() {
        return getOrder().getTotalToPay();
    }

    public String disableChooseMenuItems() {
        return getOrder().getStatus() != OrderStatus.InProgress ? "Order has been completed": null;
    }

    //endregion



    //region > menuItems (collection)

    @Programmatic
    public List<MenuItem> getMenuItems() {
        final List<MenuItem> availableMenuItems = Lists.newArrayList();

        final SortedSet<MenuItem> menuItems = getOrder().getEvent().getMenu().getItems();

        availableMenuItems.addAll(menuItems);

        final SortedSet<OrderItem> items = getOrder().getItems();
        final Iterable<MenuItem> selectedMenuItems = Iterables.transform(items, new Function<OrderItem, MenuItem>() {
            @Nullable @Override public MenuItem apply(final OrderItem orderItem) {
                return orderItem.getMenuItem();
            }
        });

        availableMenuItems.removeAll(Lists.newArrayList(selectedMenuItems));

        return availableMenuItems;
    }
    //endregion

    //region > orderItems (collection)

    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public SortedSet<OrderItem> getSelectedItems() {
        return getOrder().getItems();
    }
    //endregion



}
