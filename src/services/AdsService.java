package services;

import javax.persistence.EntityManager;
import java.util.List;

import entities.AdsEntity;
import servlet.Ads;
import util.JPAutil;

/**
 * Méthode pour lister toutes les annonces
 */
public class AdsService {

    EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

    /**
     * Méthode pour lister toutes les annonces
     *
     * @return
     */
    public List<AdsEntity> listerTous() {
        List<AdsEntity> ads =
                em.createQuery(
                        "select a from AdsEntity a order by a.dateStart desc ").getResultList();
        return ads;
    }

    /**
     * méthode qui retourne une annonce en fonction de l'id passé
     * @param em
     * @param id
     * @return
     */
    public AdsEntity consulter(EntityManager em, int id) {
        return em.find(AdsEntity.class, id);
    }

    /**
     * Ajouter une annonce dans la DB
     *
     * @param em
     * @param adsEntity
     */
    public void addAds(EntityManager em, AdsEntity adsEntity) {
        em.persist(adsEntity);
    }

    /**
     * Mets à jour l'entité dans la db
     * @param em
     * @param adsEntity
     */
    public void updateAds(EntityManager em, AdsEntity adsEntity) {
        em.merge(adsEntity);
    }

}