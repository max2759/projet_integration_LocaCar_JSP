package forms;

import entities.BrandsEntity;
import entities.ModelsEntity;
import services.BrandsService;
import services.ModelsService;
import util.JPAutil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BrandsForm {

    // Appel de l'em
    EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

    // Les entités liés
    BrandsEntity brandsEntity = new BrandsEntity();
    BrandsService brandsService = new BrandsService();

    // Les champs
    private static final String BRANDS_FIELD = "constructeur";
    private static final String BRANDS_FIELD_TO_UPDATE = "brandsLabel";
    private static final String BRANDS_FIELD_ID_TO_UPDATE = "idBrands";



    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * Ajout d'un constructeur dans la DB
     * @param request
     * @return brandsEntity
     */
    public BrandsEntity addBrands(HttpServletRequest request){

        String constructeur = getValeurChamp(request, BRANDS_FIELD);

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            brandsEntity.setLabel(constructeur);
            brandsService.addBrands(em, brandsEntity);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        } finally {
            em.close();
        }

        return brandsEntity;
    }

    public void updateBrands(HttpServletRequest request){
        String label = getValeurChamp(request, BRANDS_FIELD_TO_UPDATE);
        int brandsId = Integer.parseInt(getValeurChamp(request, BRANDS_FIELD_ID_TO_UPDATE));

        BrandsEntity brandsEntity;
        BrandsService brandsService = new BrandsService();

        EntityTransaction tx = null;
        try{
            tx = em.getTransaction();
            tx.begin();
            brandsEntity = brandsService.consultBrands(em, brandsId);
            brandsEntity.setLabel(label);
            brandsService.updateBrands(em, brandsEntity);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null && tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }

    }

    /**
     * Ajout message d'erreur
     *
     * @param field
     * @param message
     */
    private void setErreur(String field, String message) {
        erreurs.put(field, message);
    }

    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}
