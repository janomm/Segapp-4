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
import model.Pessoa;

/**
 *
 * @author mi
 */
public class PessoaDB {

    public PessoaDB() {
    }
    
    public String criar(Pessoa p){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.persist(p);
            manager.getTransaction().commit();

            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String editar(Pessoa p){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
            manager.merge(p);
            manager.getTransaction().commit();
            factory.close();
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public String deletar(Pessoa p){
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");

            EntityManager manager = factory.createEntityManager();
            
            manager.getTransaction().begin();
            manager.remove(manager.merge(p));
            manager.getTransaction().commit();
            
            factory.close();
            
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    public List<Pessoa> retornaListaPessoas(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Integer limite = 10;
        Query query = manager.createQuery("SELECT p FROM Pessoa p");
        //query.setParameter("T", limite);
        List<Pessoa> listaPessoas = query.getResultList();
        factory.close();
        return listaPessoas;
    }
    
    public Pessoa retornaPessoaPorId(Integer id){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT p FROM Pessoa p WHERE p.id = " + id.toString());
        List<Pessoa> listaPessoas = query.getResultList();
        Pessoa pReturn = new Pessoa();
        for(Pessoa p: listaPessoas){
            pReturn = p;
        }
        return pReturn;
    }
    
    public Boolean existePessoaPorTelefone(String tel){
        List<Pessoa> listaPessoas = retornaListaPessoas();
        for (Pessoa p : listaPessoas){
            if(p.getTelefone().equals(tel))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    public Pessoa retornaPessoaPorTelefone(String tel){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT p FROM Pessoa p WHERE p.telefone= " + tel);
        List<Pessoa> listaPessoas = query.getResultList();
        Pessoa pReturn = new Pessoa();
        for(Pessoa p: listaPessoas){
            pReturn = p;
        }
        return pReturn;
    }
    
    public Boolean existeTelefone(String tel){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Segapp-4PU");
        EntityManager manager = factory.createEntityManager();
        Query query = manager.createQuery("SELECT p FROM Pessoa p WHERE p.telefone= " + tel);
        List<Pessoa> listaPessoas = query.getResultList();
        Boolean pReturn = Boolean.FALSE;
        for(Pessoa p: listaPessoas){
            pReturn = Boolean.TRUE;
        }
        return pReturn;
    }
}
