package domainapp.dom.basket;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;

import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.order.OrderRepository;
import domainapp.dom.order.OrderStatus;
import domainapp.dom.person.PersonRepository;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class ShoppingBasketContributionsForMenuItem {

    @Action(
            invokeOn = InvokeOn.COLLECTION_ONLY
    )
    public ShoppingBasket select(final MenuItem menuItem) {

        final ShoppingBasket shoppingBasket = shoppingBasketContributionsForEvent.showBasket(menuItem.getMenu().getEvent());
        if(shoppingBasket == null) {
            if(actionInvocationContext.isLast()) {
                container.warnUser("could not locate Person for current user");
            }
            return null;
        }

        shoppingBasket.getOrder().newItem(menuItem, 1);

        return shoppingBasket;
    }

    public String disableSelect(MenuItem menuItem) {
        final ShoppingBasket shoppingBasket = shoppingBasketContributionsForEvent.showBasket(menuItem.getMenu().getEvent());
        if(shoppingBasket == null) {
            return "Could not locate Person for current user";
        }
        if(shoppingBasket.getOrder().getStatus() != OrderStatus.InProgress) {
            return "Order has been completed";
        }
        return null;
    }


    @Inject
    private ShoppingBasketContributionsForEvent shoppingBasketContributionsForEvent;

    @Inject
    private DomainObjectContainer container;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private ActionInvocationContext actionInvocationContext;


}
