package services;

import entities.BrandsEntity;
import entities.CarTypesEntity;
import entities.ModelsEntity;
import util.JPAutil;

import javax.persistence.EntityManager;
import java.util.List;

public class ModelsService {
    EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

    /**
     * Méthode pour lister tous les modèles
     *
     * @return modelsEntities
     */
    public List<ModelsEntity> displayModels() {
        List<ModelsEntity> modelsEntities =
                em.createQuery(
                        "select m from ModelsEntity m order by m.brandsByIdBrands.id").getResultList();

        return modelsEntities;
    }

    /**
     * méthode Consulter d'une entité à  partir de la bd
     *
     * @param em
     * @param id
     * @return
     */
    public ModelsEntity consultModel(EntityManager em, int id) {
        return em.find(ModelsEntity.class, id);
    }

    /**
     * Ajouter un modèle dans la base de donnée
     * @param em
     * @param modelsEntity
     */
    public void addModels(EntityManager em, ModelsEntity modelsEntity){
        em.persist(modelsEntity);
    }

    /**
     * Mise à jour de l'entité modèle
     * @param em
     * @param modelsEntity
     */
    public void updateModels(EntityManager em, ModelsEntity modelsEntity) {
        em.merge(modelsEntity);
    }
}
