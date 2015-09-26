package domainapp.dom.basket;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.user.ApplicationUser;

import domainapp.dom.event.Event;
import domainapp.dom.menuitem.MenuItem;
import domainapp.dom.order.Order;
import domainapp.dom.order.OrderRepository;
import domainapp.dom.person.Person;
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
