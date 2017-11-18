/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.GrupoParticipantes;

/**
 *
 * @author mi
 */
public class GrupoParticipantesDB {

    public GrupoParticipantesDB() {
    }
    
    public String criar(GrupoParticipantes gp){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(gp);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String editar(GrupoParticipantes gp){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(gp);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String deletar(GrupoParticipantes gp){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.remove(manager.merge(gp));
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public List<GrupoParticipantes> retornaGrupoParticipantesPorGrupo(Integer idGrupo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM GrupoParticipantes g WHERE g.idGrupo = " + idGrupo.toString());
        
        List<GrupoParticipantes> listaGrupoParticipantes = query.getResultList();
        return listaGrupoParticipantes;
    }
    
    public List<GrupoParticipantes> retornaGrupoParticipantesPorPessoa(Integer idPessoa){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM GrupoParticipantes g WHERE g.idPessoa = " + idPessoa.toString());
        List<GrupoParticipantes> listaGrupoParticipantes = query.getResultList();
        return listaGrupoParticipantes;
    }
    
    public GrupoParticipantes retornaGrupoParticipantePorGrupoPessoa(Integer idGrupo, Integer idPessoa){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM GrupoParticipantes g WHERE g.idPessoa = " + idPessoa.toString() + " AND g.idGrupo = " + idGrupo.toString());
        List<GrupoParticipantes> listaGrupoParticipantes = query.getResultList();
        GrupoParticipantes gpReturn = new GrupoParticipantes();
        for(GrupoParticipantes gp : listaGrupoParticipantes){
            gpReturn = gp;
        }
        return gpReturn;
    }
    
    public Boolean pessoaParticipaGrupo(Integer idPessoa, Integer idGrupo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM GrupoParticipantes g WHERE g.idPessoa = " + idPessoa.toString() + " AND g.idGrupo = " + idGrupo.toString());
        List<GrupoParticipantes> listaGrupoParticipantes = query.getResultList();
        Boolean bReturn = Boolean.FALSE;
        for(GrupoParticipantes gp : listaGrupoParticipantes){
            bReturn = Boolean.TRUE;
        }
        return bReturn;
    }
}
