package entities;

import enumeration.EnumOrderStatut;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "orders", schema = "projet_bac_info2")
@NamedQueries({
        //Recherche de commande en attente : par id Order
        @NamedQuery(name = "Orders.findOrderById",
                query = "SELECT o from OrdersEntity o where o.id = :id and  o.orderStatut = enumeration.EnumOrderStatut.PENDING"),

        // Recherche de commande validé : par id Order
        @NamedQuery(name = "Orders.findOrderValidatedById",
                query = "SELECT o from OrdersEntity o where o.id = :id and  (o.orderStatut = enumeration.EnumOrderStatut.VALIDATED or o.orderStatut = enumeration.EnumOrderStatut.CANCELED)"),

        // Recherche de commandes par id Order, par id users ou pas un username
        @NamedQuery(name = "Orders.findOrdersValidateByIdOrderAndByIdUserAndByUsername",
                query = "SELECT o from OrdersEntity o where (o.id = :id or o.usersByIdUsers.id = :id or o.usersByIdUsers.username = :username) and (o.orderStatut = enumeration.EnumOrderStatut.VALIDATED or o.orderStatut = enumeration.EnumOrderStatut.CANCELED)"),

        //Recherche de commande en attente : par id user
        @NamedQuery(name = "Orders.findOrderByIdUser",
                query = "SELECT o from OrdersEntity o where o.usersByIdUsers.id = :id and o.orderStatut = enumeration.EnumOrderStatut.PENDING")
})
public class OrdersEntity {
    private int id;
    private Date orderDate;
    private EnumOrderStatut orderStatut;
    private Collection<ContractsEntity> contractsById;
    private Collection<InvoicesEntity> invoicesById;
    private UsersEntity usersByIdUsers;

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
    @Column(name = "Order_Date", nullable = false)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    @Enumerated(EnumType.STRING)
    @Basic
    @Column(name = "Order_Statut", nullable = false)
    public EnumOrderStatut getOrderStatut() {
        return orderStatut;
    }

    public void setOrderStatut(EnumOrderStatut orderStatut) {
        this.orderStatut = orderStatut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersEntity that = (OrdersEntity) o;
        return id == that.id &&
                Objects.equals(orderDate, that.orderDate) &&
                Objects.equals(orderStatut, that.orderStatut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, orderStatut);
    }

    @OneToMany(mappedBy = "ordersByIdOrders")
    public Collection<ContractsEntity> getContractsById() {
        return contractsById;
    }

    public void setContractsById(Collection<ContractsEntity> contractsById) {
        this.contractsById = contractsById;
    }

    @OneToMany(mappedBy = "ordersByIdOrders")
    public Collection<InvoicesEntity> getInvoicesById() {
        return invoicesById;
    }

    public void setInvoicesById(Collection<InvoicesEntity> invoicesById) {
        this.invoicesById = invoicesById;
    }

    @ManyToOne
    @JoinColumn(name = "ID_Users", referencedColumnName = "ID", nullable = false)
    public UsersEntity getUsersByIdUsers() {
        return usersByIdUsers;
    }

    public void setUsersByIdUsers(UsersEntity usersByIdUsers) {
        this.usersByIdUsers = usersByIdUsers;
    }
}
