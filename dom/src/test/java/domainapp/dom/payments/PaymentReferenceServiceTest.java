package domainapp.dom.payments;

import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.core.unittestsupport.jmocking.JUnitRuleMockery2;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentReferenceServiceTest {

    @Rule
    public JUnitRuleMockery2 context = JUnitRuleMockery2.createFor(JUnitRuleMockery2.Mode.INTERFACES_AND_CLASSES);

    @Mock
    private ClockService mockClockService;

    private PaymentReferenceService paymentReferenceService;

    @Before
    public void setUp() throws Exception {
        paymentReferenceService = new PaymentReferenceService();
        paymentReferenceService.clockService = mockClockService;
    }

    @Test
    public void xxx() throws Exception {

        context.checking(new Expectations() {{
            oneOf(mockClockService).nowAsLocalDateTime();
            will(returnValue(new LocalDateTime(2015, 9, 25, 9, 14, 45)));
        }});

        final String reference = paymentReferenceService.referenceFor(new BigDecimal("154.5"));

        assertThat(reference).isEqualTo("+++154/5250/94511+++");

    }
}