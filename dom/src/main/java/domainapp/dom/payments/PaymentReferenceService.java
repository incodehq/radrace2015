package domainapp.dom.payments;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.inject.Inject;

import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDateTime;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(nature = NatureOfService.DOMAIN)
public class PaymentReferenceService {

    @Programmatic
    public String referenceFor(final BigDecimal amount) {

        final int amountInt = amount.intValue();    // 154
        String formattedAmountInt = String.format("%03d", amountInt);   // "154"

        final BigDecimal by10 = amount.multiply(BigDecimal.TEN);    // 1545

        final int amountBy10 = by10.intValue();
        String formattedAmountBy10 = String.format("%04d", amountBy10); // "1545"

        final int fractional = by10.toBigInteger().mod(BigInteger.TEN).intValue();  // 5

        final LocalDateTime dt = clockService.nowAsLocalDateTime();

        final int dayOfMonth = dt.get(DateTimeFieldType.dayOfMonth());  // 25
        String formattedDayOfMonth = String.format("%02d", dayOfMonth); // "25"

        final int hourOfDay = dt.get(DateTimeFieldType.hourOfDay()); // 9
        String formattedHourOfDay = String.format("%02d", hourOfDay); // "09"

        final int minuteOfHour = dt.get(DateTimeFieldType.minuteOfHour()); // 14
        String formattedMinuteOfHour = String.format("%02d", minuteOfHour); // "14"

        final int secondOfMinute = dt.get(DateTimeFieldType.secondOfMinute()); // 45
        String formattedSecondOfMinute = String.format("%02d", secondOfMinute); // "45"

        final String val =
                String.format(
                        "%s%s%s%s", formattedAmountBy10, formattedDayOfMonth, formattedHourOfDay, formattedSecondOfMinute);

        final BigInteger bigInteger = new BigInteger(val);
        final int modulo = bigInteger.mod(new BigInteger("97")).intValue(); // 11
        String formattedModulo = String.format("%02d", modulo); // "11"

        return String.format("+++%s/%01d%s%s/%s%s%s+++",
                formattedAmountInt, fractional,
                formattedDayOfMonth,
                ""+formattedHourOfDay.charAt(0),
                ""+formattedHourOfDay.charAt(1),
                formattedSecondOfMinute,
                formattedModulo);
    }

    @Inject
    ClockService clockService;
}
