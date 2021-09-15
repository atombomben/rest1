/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import entity.*;
import static entity.Animal_.type;
import java.util.Random;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Atom
 * 
 */

@Path("animals_db")
public class AnimalFromDB {
        
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AnimalFromDB
     */
    public AnimalFromDB() {
    }
    
    @Path("/animals")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals() {
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
      List<Animal> animals = query.getResultList();
      return new Gson().toJson(animals);
   } finally {
          em.close();
   }
    
}

    @Path("/animalbyid/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public String getAnimalById (@PathParam("id") long id) {
        
        EntityManager em = emf.createEntityManager();
    try {
      Animal animal = em.find(Animal.class, id);
      return new Gson().toJson(animal);
    
   } finally {
          em.close();
   }
        
    }
    
    
    @Path("/animalbytype/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimalsByType(@PathParam("type") String type) {
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.type = :type", Animal.class);
      query.setParameter("type", type);
      List<Animal> animals = query.getResultList();
      return new Gson().toJson(animals);
   } finally {
          em.close();
   }
  }
    
  @Path("/random_animal")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  
  public String getRandomAnimal() {
      
      EntityManager em = emf.createEntityManager();
      try {
          TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
          List<Animal> animals = query.getResultList();
          int count = animals.size();
          Random random = new Random();
          int number = random.nextInt(count);
          return new Gson().toJson(animals.get(number));
          
          
      } finally {
          em.close();
      }
      
  }
    
}
