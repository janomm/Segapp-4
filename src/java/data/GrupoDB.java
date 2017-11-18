/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Grupo;
import model.GrupoParticipantes;

/**
 *
 * @author mi
 */
public class GrupoDB {

    public GrupoDB() {
    }
    
    public String criar(Grupo g){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(g);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String editar(Grupo g){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
            
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(g);
            manager.getTransaction().commit();
            
            factory.close();
            return "";
        } catch (Exception e){
            return e.getMessage();
        }
    }
    
    public String deletar(Grupo g){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
            
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.remove(manager.merge(g));
            //manager.remove(g);
            manager.getTransaction().commit();
            
            factory.close();
            return "";
        } catch (Exception e){
            return e.getMessage();
        }
    }
    
    public Grupo retornaGrupoPorId(Integer id){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM Grupo g WHERE g.id = " + id.toString());
        List<Grupo> listaGrupos = query.getResultList();
        Grupo gReturn = new Grupo();
        for(Grupo g: listaGrupos){
            gReturn = g;
        }
        return gReturn;
    }
    
    public List<Grupo> retornaListaGruposPorAdm(Integer id){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM Grupo g WHERE g.idAdm = " + id.toString());
        List<Grupo> listaGrupos = query.getResultList();
        return listaGrupos;
    }
    
    public List<Grupo> retornaListaGruposPorPessoa(Integer idPessoa){
        GrupoParticipantesDB gpBD = new GrupoParticipantesDB();
        List<GrupoParticipantes> listaGrupoParticipantes = gpBD.retornaGrupoParticipantesPorPessoa(idPessoa);
        List<Grupo> listaGrupos = new ArrayList<Grupo>();
        Grupo g = new Grupo();
        for(GrupoParticipantes gp : listaGrupoParticipantes){
            g = retornaGrupoPorId(gp.getIdGrupo());
            if(g != null)
                listaGrupos.add(g);
        }
        return listaGrupos;
    }
    
    public Integer retornaQuantidadeParticipantesGrupo(Integer idGrupo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT g FROM GrupoParticipantes g WHERE g.idGrupo = " + idGrupo.toString());
        List<GrupoParticipantes> listaGrupos = query.getResultList();
        Integer quant = 0;
        for(GrupoParticipantes gp : listaGrupos){
            quant++;
        }
        return quant;
    }
}
