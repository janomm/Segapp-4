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
import model.Mensagem;

/**
 *
 * @author mi
 */
public class MensagemDB {
    
    public String criar(Mensagem msg){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
            
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(msg);
            manager.getTransaction().commit();
            
            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String editar(Mensagem msg){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
            
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(msg);
            manager.getTransaction().commit();
            
            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String deletar(Mensagem msg){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(msg));
            manager.getTransaction().commit();
            
            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public List<Mensagem> retornaListaMensagens(Integer idGrupo){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT m FROM Mensagem m WHERE m.idGrupo = " + idGrupo);
        List<Mensagem> listaMensagens = query.getResultList();
        factory.close();
        return listaMensagens;
    }
    
    
    
}
