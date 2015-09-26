package domainapp.dom.basket;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.order.Order;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class ShoppingBasketContributionsForOrder {

    //region > showBasket (action)
    @MemberOrder(sequence = "1")
    public ShoppingBasket showBasket(final Order order) {
        final ShoppingBasket shoppingBasket = new ShoppingBasket();
        container.injectServicesInto(shoppingBasket);
        shoppingBasket.setOrder(order);
        return shoppingBasket;
    }
    //endregion


    @Inject
    private DomainObjectContainer container;
}
