package cc.myosotis.leisure.repository;

import cc.myosotis.leisure.model.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Integer> {

    List<Bill> findBillByBillingNumber(String billingNumber);

    Bill findBillByBillingNumberAndPayer(String billingNumber, String payer);

    Bill findBillByBillingNumberAndBuyer(String billingNumber, String buyer);
}
