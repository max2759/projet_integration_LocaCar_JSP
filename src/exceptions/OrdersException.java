package exceptions;

import entities.AdsEntity;
import entities.OrdersEntity;
import services.AdsService;
import util.JPAutil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class OrdersException {
    //public AdsEntity ads;


    /**
     * Vérification de l'existence de l'entité
     * @param orders
     * @throws Exception
     */
    public void validationEntity(OrdersEntity orders) throws Exception {
        if (orders == null) {
            throw new Exception("La commande est incorrecte");

        }
    }

}