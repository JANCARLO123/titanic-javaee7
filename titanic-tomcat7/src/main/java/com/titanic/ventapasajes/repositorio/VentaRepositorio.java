package com.titanic.ventapasajes.repositorio;

import com.titanic.ventapasajes.modelo.Bus;
import com.titanic.ventapasajes.modelo.Recorrido;
import com.titanic.ventapasajes.modelo.Venta;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by josediaz on 7/28/14.
 */
public class VentaRepositorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    public Venta obtenerVenta(Long id){
        return entityManager.find(Venta.class, id);
    }


    public Venta adicionarVenta(Venta venta) {

        return entityManager.merge(venta);
    }

    public Venta obtenerVenta(Date fechaVenta, Recorrido ruta, String horaSalida, Bus bus) {

        try{

            Venta venta = entityManager.createQuery("from Venta where " +
                    "fechaVenta = :fechaVenta and " +
                    "ruta.ideRecorrido = :ideRecorrido and " +
                    "horaSalida = :horaSalida and " +
                    "bus.ideBus = :ideBus", Venta.class)
                    .setParameter("fechaVenta", fechaVenta)
                    .setParameter("ideRecorrido", ruta.getIdeRecorrido())
                    .setParameter("horaSalida", horaSalida)
                    .setParameter("ideBus", bus.getIdeBus())
                    .getSingleResult();

            Hibernate.initialize(venta.getBoletos());
            return venta;
        }catch(NoResultException e){
            return null;
        }


    }
}
