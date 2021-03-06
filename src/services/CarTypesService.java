package services;

import entities.CarTypesEntity;
import util.JPAutil;

import javax.persistence.EntityManager;
import java.util.List;

public class CarTypesService {

    EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

    /**
     * Méthode pour ajouter une catégorie
     *
     * @param em
     * @param carTypesEntity
     */
    public void addCategory(EntityManager em, CarTypesEntity carTypesEntity) {
        em.persist(carTypesEntity);
    }

    /**
     * Méthode pour lister toutes les annonces
     *
     * @return carTypesEntities
     */
    public List<CarTypesEntity> displayCategory() {
        List<CarTypesEntity> carTypesEntities =
                em.createNamedQuery("car_types.listCT", CarTypesEntity.class).getResultList();
        return carTypesEntities;
    }

    /**
     * Suppression de l'entité carTypes
     *
     * @param em
     * @param carTypesEntity
     */
    public void deleteCarTypes(EntityManager em, CarTypesEntity carTypesEntity) {
        em.remove(em.merge(carTypesEntity));
    }

    /**
     * Modification de la table catégorie en fonction de l'id passé
     *
     * @param carTypesEntity
     * @param em
     * @return
     */
    public void updateCarTypes(EntityManager em, CarTypesEntity carTypesEntity) {
        em.merge(carTypesEntity);
    }

    /**
     * méthode Consulter d'une entité à  partir de la bd
     *
     * @param em
     * @param id
     * @return
     */
    public CarTypesEntity consult(EntityManager em, int id) {
        return em.find(CarTypesEntity.class, id);
    }

   public CarTypesEntity checkCarTypesEntity(EntityManager em, String label){
        try{
            return em.createNamedQuery("car_types.checkLabel", CarTypesEntity.class).setParameter("label", label).getSingleResult();
        }catch (Exception ex) {
            return null;
        }
   }




}
