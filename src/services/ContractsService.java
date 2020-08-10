package services;

import entities.AdsEntity;
import entities.ContractsEntity;
import entities.OrdersEntity;
import entities.UsersEntity;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Méthode pour lister toutes les annonces
 */
public class ContractsService {

    /**
     * méthode ajouter d'une entité à  la bd
     *
     * @param em
     * @param contract
     */
    public void add(EntityManager em, ContractsEntity contract) {

        em.persist(contract);

    }

    /**
     * Update de l'entité
     *
     * @param em
     * @param contract
     */
    public void mergeContract(EntityManager em, ContractsEntity contract) {
        em.merge(contract);

    }

    /**
     * Delete de l'entité
     *
     * @param em
     * @param contractsEntity
     */
    public void removeContract(EntityManager em, ContractsEntity contractsEntity) {

        em.remove(em.merge(contractsEntity));

//        em.remove(contractsEntity);
    }

    /**
     * méthode Consulter d'une entité à  partir de la bd
     *
     * @param em
     * @param id
     * @return
     */
    public ContractsEntity consult(EntityManager em, int id) {
        return em.find(ContractsEntity.class, id);
    }

    /**
     * recherche de contract par id order
     *
     * @param em
     * @param id
     * @return
     */
    public ContractsEntity findContractByIdOrder(EntityManager em, int id) {
        try {

            return em.createNamedQuery("Contracts.findContractByIdOrder",
                    ContractsEntity.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }

    }

    /**
     * recherche de contrat par id commande et par id voiture
     * @param em
     * @param idOrder
     * @param idCar
     * @return
     */
    public ContractsEntity findContractByIdOrderAndByIdCar(EntityManager em, int idOrder, int idCar) {
        try {

            return em.createNamedQuery("Contracts.findContractByIdOrderAndByIdCar",
                    ContractsEntity.class)
                    .setParameter("idOrder", idOrder)
                    .setParameter("idCar", idCar)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * Lister tous les contrats d'un utilisateurs
     * @param em
     * @param idUser
     * @return
     */
    public List<ContractsEntity> findAllContractsByIdUser(EntityManager em, int idUser) {

        UsersService usersService = new UsersService();
        UsersEntity usersEntity = usersService.consult(em, idUser);
        List<ContractsEntity> contractsEntities = em.createNamedQuery("Contracts.findAllContractsByIdUser",
                ContractsEntity.class)
                .setParameter("user", usersEntity)
                .getResultList();

        return contractsEntities;

    }



}


