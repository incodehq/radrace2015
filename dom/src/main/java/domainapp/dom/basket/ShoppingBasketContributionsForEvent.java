package domainapp.dom.basket;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.user.ApplicationUser;

import domainapp.dom.event.Event;
import domainapp.dom.order.Order;
import domainapp.dom.order.OrderRepository;
import domainapp.dom.person.Person;
import domainapp.dom.person.PersonRepository;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class ShoppingBasketContributionsForEvent {

    //region > showBasket (action)
    @MemberOrder(sequence = "1")
    public ShoppingBasket showBasket(final Event event) {

        final ApplicationUser me = meService.me();
        Person person = personRepository.findByUsername(me.getUsername());
        if(person == null) {
            return null;
        }

        final Order order = orderRepository.findOrCreate(person, event);

        final ShoppingBasket shoppingBasket = new ShoppingBasket();
        container.injectServicesInto(shoppingBasket);
        shoppingBasket.setOrder(order);

        return shoppingBasket;

    }
    //endregion

    @Inject
    private MeService meService;

    @Inject
    private DomainObjectContainer container;

    @Inject
    private PersonRepository personRepository;

    @Inject
    private OrderRepository orderRepository;

}
