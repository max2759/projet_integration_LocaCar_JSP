package forms;

import entities.*;
import enumeration.EnumFuel;
import enumeration.EnumTypesAds;
import exceptions.AdsException;
import services.*;
import util.JPAutil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdsForm {
    private static final String FIELD_ID = "idAds";
    private static final String FIELD_LABEL_ADS = "labelAd";
    private static final String FIELD_TYPEADS_ADS = "adType";
    private static final String FIELD_PRICE_ADS = "adPrice";
    private static final String FIELD_COLOR_CAR = "color";
    private static final String FIELD_RELEASEYEAR_CAR = "ReleaseYear";
    private static final String FIELD_KM_CAR = "kilometer";
    private static final String FIELD_HP_CAR = "horsePower";
    private static final String FIELD_CARTYPES_CAR = "carTypes";
    private static final String FIELD_BRANDS_CAR = "brands";
    private static final String FIELD_MODELS_CAR = "models";
    private static final String FIELD_FUEL_CAR = "fuel";
    private static final String FIELD_DELETE_ADS = "adsDelete";




    private String result;
    private Map<String, String> errors = new HashMap<String, String>();

    public String getResult() {

        return result;
    }

    public Map<String, String> getErrors() {

        return errors;
    }

    /**
     * Crée une annonce via le formulaire
     * @param request
     * @throws ParseException
     */
    public void addAds(HttpServletRequest request) throws ParseException {

        EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

        // Les champs du form
        String color = getValeurChamp(request,FIELD_COLOR_CAR );
        String releaseYears = getValeurChamp(request, FIELD_RELEASEYEAR_CAR);
        Date releaseYear = new SimpleDateFormat("yyyy-MM-DD").parse(releaseYears);
        int kilometer = Integer.parseInt(getValeurChamp(request, FIELD_KM_CAR));
        int horsePower = Integer.parseInt(getValeurChamp(request, FIELD_HP_CAR));
        String fuel = getValeurChamp(request, FIELD_FUEL_CAR);
        EnumFuel enumfuel = EnumFuel.valueOf(fuel);
        int models = Integer.parseInt(getValeurChamp(request, FIELD_MODELS_CAR));
        int brands = Integer.parseInt(getValeurChamp(request, FIELD_BRANDS_CAR));
        int carTypes = Integer.parseInt(getValeurChamp(request, FIELD_CARTYPES_CAR));
        String labelAd = getValeurChamp(request, FIELD_LABEL_ADS);
        Double priceAd = Double.parseDouble(getValeurChamp(request, FIELD_PRICE_ADS));
        String typeAds = getValeurChamp(request, FIELD_TYPEADS_ADS);
        EnumTypesAds enumTypesAds = EnumTypesAds.valueOf(typeAds);
        int idCar = 0;
        /*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-DD");
        LocalDateTime now = LocalDateTime.now();*/
        Date todayDate = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(todayDate.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime dateEnd = ldt.plusMonths(1);
        Date endOut = Date.from(dateEnd.atZone(ZoneId.systemDefault()).toInstant());


        // Les entités

        AdsEntity adsEntity = new AdsEntity();
        CarsEntity carsEntity = new CarsEntity();
        CarTypesEntity carTypesEntity = new CarTypesEntity();
        ModelsEntity modelsEntity = new ModelsEntity();
        BrandsEntity brandsEntity = new BrandsEntity();

        // Les services

        AdsService adsService = new AdsService();
        CarsService carsService = new CarsService();
        CarTypesService carTypesService = new CarTypesService();
        ModelsService modelsService = new ModelsService();
        BrandsService brandsService = new BrandsService();

        // Transaction

        EntityTransaction tx = null;

        try{
            tx= em.getTransaction();
            tx.begin();
            carTypesEntity = carTypesService.consult(em, carTypes);
            modelsEntity = modelsService.consultModel(em, models);
            brandsEntity = brandsService.consultBrands(em, brands);
            carsEntity.setActive(true);
            carsEntity.setColor(color);
            carsEntity.setHorsePower(horsePower);
            carsEntity.setReleaseYear(releaseYear);
            carsEntity.setKilometer(kilometer);
            carsEntity.setEnumFuel(enumfuel);
            carsEntity.setCarTypesByIdCarTypes(carTypesEntity);
            carsEntity.setModelsByIdModels(modelsEntity);
            modelsEntity.setBrandsByIdBrands(brandsEntity);
            carsService.addCars(em, carsEntity);
            idCar = carsEntity.getId();

            carsService.consulter(em, idCar);
            adsEntity.setCarsByIdCars(carsEntity);
            adsEntity.setActive(true);
            adsEntity.setLabel(labelAd);
            adsEntity.setPrice(priceAd);
            adsEntity.setTypesAds(enumTypesAds);
            adsEntity.setDateStart(out);
            adsEntity.setDateEnd(endOut);

            adsService.addAds(em, adsEntity);

            tx.commit();
        }catch(Exception ex){
            if (tx != null && tx.isActive()) tx.rollback();
        }

    }

    /**
     * Méthode de vérification de l'existence de l'ads
     *
     * @param request
     * @return
     */
    public AdsEntity checkAds(HttpServletRequest request) {

        AdsEntity ads = null;

        if (request.getParameter(FIELD_ID) != null) {
            // On vérifie que c'est bien un nbr
            try {

                int idAds = Integer.parseInt(request.getParameter(FIELD_ID));

                EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

                // Recherche de l'ads

                AdsService adsService = new AdsService();

                EntityTransaction tx = null;
                try {
                    tx = em.getTransaction();
                    tx.begin();
                    ads = adsService.consulter(em, idAds);
                    tx.commit();
                } catch (Exception ex) {
                    if (tx != null && tx.isActive()) tx.rollback();
                } finally {
                    em.close();
                }

                AdsException adsException = new AdsException();

                try {
                    adsException.validationEntity(ads);
                } catch (Exception e) {
                    setError(FIELD_ID, e.getMessage());
                }
            } catch (NumberFormatException e) {
                setError(FIELD_ID, "Cette valeur n'est pas un chiffre, essaie encore !");

            }
        } else {
            setError(FIELD_ID, "Vide");
        }

        if (errors.isEmpty()) {
            result = "Succès";
        } else {
            result = "Échec";
        }
        return ads;
    }

    public void removeAds(HttpServletRequest request){

        EntityManager em = JPAutil.createEntityManager("projet_bac_info2");

        //Champs
        int idDelCar = Integer.parseInt(getValeurChamp(request, FIELD_DELETE_ADS));
        int idCar = 0;


        //Entité
        AdsEntity adsEntity = new AdsEntity();
        CarsEntity carsEntity =new CarsEntity();

        //Services
        AdsService adsService = new AdsService();
        CarsService carsService = new CarsService();

        // Transaction

        EntityTransaction tx = null;
        try{
            tx= em.getTransaction();
            tx.begin();
            adsEntity = adsService.consulter(em, idDelCar);
            carsEntity = adsEntity.getCarsByIdCars();

            adsEntity.setActive(false);
            carsEntity.setActive(false);

            adsService.updateAds(em, adsEntity);
            carsService.updateCar(em, carsEntity);
            tx.commit();

        }catch(Exception ex){
            if (tx != null && tx.isActive()) tx.rollback();
        }
    }
    /*
     * Ajoute un message correspondant au champ spécifié à la map des errors.
     */
    private void setError(String field, String message) {
        errors.put(field, message);
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