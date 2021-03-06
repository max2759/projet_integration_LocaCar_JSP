package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "contracts", schema = "projet_bac_info2")
@NamedQueries({
        // Recherche tous les contracts par idOrder
        @NamedQuery(name = "Contracts.findAllContractsByIdOrder",
                query = "SELECT c from ContractsEntity c " +
                        "where c.ordersByIdOrders.id = :id"),

        // Recherche de contract par Id order et par id Car
        @NamedQuery(name = "Contracts.findContractByIdOrderAndByIdCar",
                query = "SELECT c from ContractsEntity c where c.ordersByIdOrders.id = :idOrder and c.carsByIdCars.id = :idCar"),


        // Vérification des dates de réservation d'un véhicule
        @NamedQuery(name = "Contracts.findContractsByIdCarAndReservationDate",
                query = "SELECT c from ContractsEntity c " +
                        "where c.carsByIdCars.id = :idCar and  " +
                        "((c.dateStart between :dateStart and :dateEnd) or (c.dateEnd between :dateStart and :dateEnd))"),

        // Recherche de contracts par id User
        @NamedQuery(name = "Contracts.findAllContractsByIdUser",
                query = "SELECT c from ContractsEntity c " +
                        "where c.ordersByIdOrders.usersByIdUsers = :user and c.ordersByIdOrders.orderStatut = enumeration.EnumOrderStatut.PENDING")
})
public class ContractsEntity {
    private int id;
    private Date dateStart;
    private Date dateEnd;
    private double finalPrice;
    private OrdersEntity ordersByIdOrders;
    private CarsEntity carsByIdCars;
    private ContractTypesEntity contractTypesByIdContractTypes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Date_Start", nullable = false)
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "Date_End", nullable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Basic
    @Column(name = "Final_Price", nullable = false, precision = 0)
    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractsEntity that = (ContractsEntity) o;
        return id == that.id &&
                Double.compare(that.finalPrice, finalPrice) == 0 &&
                Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateStart, dateEnd, finalPrice);
    }

    @ManyToOne
    @JoinColumn(name = "ID_Orders", referencedColumnName = "ID", nullable = false)
    public OrdersEntity getOrdersByIdOrders() {
        return ordersByIdOrders;
    }

    public void setOrdersByIdOrders(OrdersEntity ordersByIdOrders) {
        this.ordersByIdOrders = ordersByIdOrders;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Cars", referencedColumnName = "ID", nullable = false)
    public CarsEntity getCarsByIdCars() {
        return carsByIdCars;
    }

    public void setCarsByIdCars(CarsEntity carsByIdCars) {
        this.carsByIdCars = carsByIdCars;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Contract_Types", referencedColumnName = "ID", nullable = false)
    public ContractTypesEntity getContractTypesByIdContractTypes() {
        return contractTypesByIdContractTypes;
    }

    public void setContractTypesByIdContractTypes(ContractTypesEntity contractTypesByIdContractTypes) {
        this.contractTypesByIdContractTypes = contractTypesByIdContractTypes;
    }
}
