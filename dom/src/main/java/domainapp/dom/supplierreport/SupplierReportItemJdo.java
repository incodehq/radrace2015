package domainapp.dom.supplierreport;

import java.math.BigDecimal;

import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

//@javax.jdo.annotations.PersistenceCapable(
//        identityType = IdentityType.NONDURABLE,
//        schema = "simple",
//        table = "SupplierReport",
//        extensions = {
//                @Extension(vendorName = "datanucleus", key = "view-definition",
//                        value = "CREATE VIEW \"simple\".\"SupplierReport\" " +
//                                "( " +
//                                "  {this.supplierName} " +
////                               "  ,{this.ingredientName} " +
////                                "  ,{this.total} " +
//                                ") AS " +
//                                "SELECT " +
//                                "   \"Supplier\".\"name\" " +
//                                "   ,\"Ingredient\".\"name\" " +
////                                "   ,SUM(\"OrderItem\".\"quantity\") as \"total\" " +
//                                " FROM \"simple\".\"Supplier\" "
//                                + " INNER JOIN \"simple\".\"Ingredient\"          ON \"simple\".\"Ingredient\".\"supplier_id\"      = \"simple\".\"Supplier\".\"id\" "
//                                + " INNER JOIN \"simple\".\"MenuItem\"            ON \"simple\".\"MenuItem\".\"id\"                 = \"simple\".\"MenuItemIngredients\".\"menu_item_id\" "
////                                + " INNER JOIN \"simple\".\"MenuItemIngredients\" ON \"simple\".\"MenuItemIngredients\".\"ingredient_id\" = \"simple\".\"Ingredient\".\"id\" "
////                              + " INNER JOIN \"simple\".\"Menu\"       ON \"Menu\".\"id\" = \"MenuItem\".\"menu_item_id\" "
////                              + " INNER JOIN \"simple\".\"OrderItem\"  ON \"OrderItem\".\"menu_item_id\" = \"mi\".\"id\" "
////                              + " WHERE \"Menu\".\"event\" = :event " +
////                              + " group by \"Supplier\".\"name\", \"Ingredient\".\"name\" "
//                )
//        })
//@javax.jdo.annotations.Queries({
//        @javax.jdo.annotations.Query(
//                name = "findBySupplier", language = "JDOQL",
//                value = "SELECT " +
//                        "FROM domainapp.dom.supplierreport.SupplierReport " +
//                        "WHERE supplierName == :supplierName ")
//})
//@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@DomainObject(
        editing = Editing.DISABLED,
        nature = Nature.VIEW_MODEL
)
public class SupplierReportItemJdo {

        //region > supplierName (property)
        private String supplierName;

        @Column(allowsNull = "false")
        @MemberOrder(sequence = "1")
        public String getSupplierName() {
                return supplierName;
        }

        public void setSupplierName(final String supplierName) {
                this.supplierName = supplierName;
        }
        //endregion

        //region > ingredientName (property)
        private String ingredientName;

        @Column(allowsNull = "false")
        @MemberOrder(sequence = "2")
        public String getIngredientName() {
                return ingredientName;
        }

        public void setIngredientName(final String ingredientName) {
                this.ingredientName = ingredientName;
        }
        //endregion

        //region > total (property)
        private BigDecimal total;

        @Column(allowsNull = "false")
        @MemberOrder(sequence = "3")
        public BigDecimal getTotal() {
                return total;
        }

        public void setTotal(final BigDecimal total) {
                this.total = total;
        }
        //endregion


}

